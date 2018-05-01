package org.mascheraveneziana.zitan.service.provider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.GoogleResource;
import org.mascheraveneziana.zitan.domain.provider.ProviderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.CalendarResource;
import com.google.api.services.admin.directory.model.CalendarResources;

public class GoogleResourceService implements ProviderResourceService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public List<ProviderResource> getResources(OAuth2AuthenticationToken authentication) {
        try {
            Directory directoryService = getDirectoryService(authentication);

            // https://developers.google.com/admin-sdk/directory/v1/reference/resources/calendars/list
            CalendarResources calendarResources = directoryService
                    .resources().calendars().list("my_customer").execute();

            List<ProviderResource> resourceList = calendarResources.getItems().stream().map(item -> {
                GoogleResource resource = new GoogleResource();
                resource.setId(item.getResourceEmail());
                resource.setName(item.getResourceName());
                return resource;
            }).collect(Collectors.toList());
            return resourceList;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProviderResource getResourceById(OAuth2AuthenticationToken authentication, String id) {
        try {
            Directory directoryService = getDirectoryService(authentication);

            // https://developers.google.com/admin-sdk/directory/v1/reference/resources/calendars/get
            CalendarResource calendarResource = directoryService
                    .resources().calendars().get("my_customer", id).execute();
            ProviderResource resource = new GoogleResource();
            resource.setId(calendarResource.getResourceEmail());
            resource.setName(calendarResource.getResourceName());
            return resource;
        } catch (IOException e) {
            throw new ZitanException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO 同じコードがあと1箇所ある
    private Directory getDirectoryService(OAuth2AuthenticationToken authentication) {
      String id = authentication.getPrincipal().getName();
      OAuth2AuthorizedClient authorizedClient =
              authorizedClientService.loadAuthorizedClient("google", id);
      Credential credential = new GoogleCredential()
              .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

      Directory directoryService = new Directory.Builder(
              new NetHttpTransport(), new JacksonFactory(), credential)
              // TODO: read from properties file
              // .setApplicationName("")
              .build();
      return directoryService;
  }

}
