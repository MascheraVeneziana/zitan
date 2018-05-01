package org.mascheraveneziana.zitan.service.provider.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleAccount;
import org.mascheraveneziana.zitan.service.provider.ProviderStatusService;
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
public class GoogleStatusService implements ProviderStatusService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public List<ProviderAccount> getStatus(OAuth2AuthenticationToken authentication,
            Date timeMin, Date timeMax, List<String> mailList) {
        try {
            Calendar calendarService = getCalendarService(authentication);

            List<FreeBusyRequestItem> items = mailList.stream().map(mail -> {
                return new FreeBusyRequestItem().setId(mail);
                }).collect(Collectors.toList());

            FreeBusyRequest request = new FreeBusyRequest()
                    .setTimeMin(new DateTime(timeMin))
                    .setTimeMax(new DateTime(timeMax))
                    .setItems(items);

            FreeBusyResponse response = calendarService.freebusy().query(request).execute();
            Map<String, FreeBusyCalendar> calendars = response.getCalendars();

            List<ProviderAccount> statusList = new ArrayList<>();
            for (String mail : calendars.keySet()) {
                FreeBusyCalendar freeBusy = calendars.get(mail);

                GoogleAccount account = new GoogleAccount();
                account.setId(mail);
                account.setFree(freeBusy.size() == 0);
                statusList.add(account);
            }

            return statusList;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Calendar getCalendarService(OAuth2AuthenticationToken authentication) {
        String token = authentication.getPrincipal().getName();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", token);
        Credential credential = new GoogleCredential().setAccessToken(authorizedClient.getAccessToken().getTokenValue());

        Calendar calendarService = new Calendar.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                // TODO: read from properties file
                // .setApplicationName("")
                .build();
        return calendarService;
    }

}
