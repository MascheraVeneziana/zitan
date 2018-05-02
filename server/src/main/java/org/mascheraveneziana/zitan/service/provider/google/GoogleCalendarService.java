package org.mascheraveneziana.zitan.service.provider.google;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.domain.provider.ProviderMeetingGroup;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleAccount;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleMeetingGroup;
import org.mascheraveneziana.zitan.service.SystemService;
import org.mascheraveneziana.zitan.service.provider.ProviderCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.FreeBusyCalendar;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;

@Service
public class GoogleCalendarService implements ProviderCalendarService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private SystemService systemService;

    @Override
    public ProviderMeetingGroup getStatus(OAuth2AuthenticationToken authentication, ProviderMeetingGroup group) {
        try {
            Calendar calendarService = getCalendarService(authentication);

            List<FreeBusyRequestItem> items = group.getAccounts().stream().map(account -> {
                return new FreeBusyRequestItem().setId(account.getEmail());
            }).collect(Collectors.toList());

            FreeBusyRequest request = new FreeBusyRequest()
                    .setTimeMin(new DateTime(group.getTimeMin()))
                    .setTimeMax(new DateTime(group.getTimeMax()))
                    .setItems(items);

            FreeBusyResponse response = calendarService.freebusy().query(request).execute();

            Map<String, FreeBusyCalendar> calendars = response.getCalendars();

            List<ProviderAccount> resultAccounts = group.getAccounts().stream().map(account -> {
                FreeBusyCalendar calendar = calendars.get(account.getEmail());
                GoogleAccount googleAccount = new GoogleAccount();
                googleAccount.setEmail(account.getEmail());
                googleAccount.setFree(calendar.getBusy().size() == 0);
                return googleAccount;
            }).collect(Collectors.toList());

            ProviderMeetingGroup result = new GoogleMeetingGroup();
            result.setAccounts(resultAccounts);
            result.setOpenable(getFreedoms(resultAccounts).size() == resultAccounts.size());

            return result;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Calendar getCalendarService(OAuth2AuthenticationToken authentication) {
        String token = authentication.getPrincipal().getName();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", token);
        Credential credential = new GoogleCredential().setAccessToken(authorizedClient.getAccessToken().getTokenValue());

        Calendar calendarService = new Calendar.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                 .setApplicationName(systemService.system().getApplicationName())
                .build();
        return calendarService;
    }

    private List<ProviderAccount> getFreedoms(List<ProviderAccount> accounts) {
        List<ProviderAccount> freedoms = accounts.stream().filter(account -> {
            return ((GoogleAccount) account).isFree();
        }).collect(Collectors.toList());
        return freedoms;
    }

}
