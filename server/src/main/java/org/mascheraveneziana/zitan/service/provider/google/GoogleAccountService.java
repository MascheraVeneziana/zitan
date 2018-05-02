package org.mascheraveneziana.zitan.service.provider.google;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.domain.provider.google.GoogleAccount;
import org.mascheraveneziana.zitan.service.SystemService;
import org.mascheraveneziana.zitan.service.provider.ProviderAccountService;
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
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.CalendarResources;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;

@Service
public class GoogleAccountService implements ProviderAccountService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private SystemService systemService;

    @Override
    public ProviderAccount getUserById(OAuth2AuthenticationToken authentication, String id) {
        try {
            Directory directoryService = getDirectoryService(authentication);

            // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
            User googleUser = directoryService.users().get(id)
                    .setViewType("domain_public")
                    .execute();

            GoogleAccount account = new GoogleAccount();
            account.setId(googleUser.getId());
            account.setName(googleUser.getName().getFullName());
            account.setEmail(googleUser.getPrimaryEmail());

            return account;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProviderAccount> getUsers(OAuth2AuthenticationToken authentication) {
        try {
            Directory directoryService = getDirectoryService(authentication);

            // https://developers.google.com/admin-sdk/directory/v1/reference/users/list
            Users googleUsers = directoryService.users().list()
                    .setDomain("unirita.co.jp")
                    .setMaxResults(500)
                    .setOrderBy("email")
                    .setProjection("full")
                    .setViewType("domain_public")
                    .execute();

            List<ProviderAccount> accountList = googleUsers.getUsers().stream().map(googleUser -> {
                ProviderAccount account = new GoogleAccount();
                account.setId(googleUser.getId());
                account.setName(googleUser.getName().getFullName());
                account.setEmail(googleUser.getPrimaryEmail());
                return account;
            }).collect(Collectors.toList());

            return accountList;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProviderAccount> getResources(OAuth2AuthenticationToken authentication) {
        try {
            Directory directoryService = getDirectoryService(authentication);

            // https://developers.google.com/admin-sdk/directory/v1/reference/resources/calendars/list
            CalendarResources calendarResources = directoryService.resources().calendars().list("my_customer").execute();

            List<ProviderAccount> accountList = calendarResources.getItems().stream().map(item -> {
                ProviderAccount account = new GoogleAccount();
                account.setId(item.getResourceEmail());
                account.setName(item.getResourceName());
                return account;
            }).collect(Collectors.toList());

            return accountList;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Directory getDirectoryService(OAuth2AuthenticationToken authentication) {
        String id = authentication.getPrincipal().getName();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", id);
        Credential credential = new GoogleCredential()
                .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

        Directory directoryService = new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                 .setApplicationName(systemService.system().getApplicationName())
                .build();
        return directoryService;
    }

}
