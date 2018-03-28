package org.mascheraveneziana.zitan.service.google;

import java.util.List;
import java.util.stream.Collectors;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Users;

@Service
public class GoogleUserService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public User getGoogleUser(String id) throws Exception {
        Directory directoryService = getDirectoryService(id);

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
        com.google.api.services.admin.directory.model.User googleUser =
                directoryService.users().get(id)
                .setViewType("domain_public")
                .execute();

        User user = new User();
        user.setId(id);
        user.setName(googleUser.getName().getFullName());
        user.setEmail(googleUser.getPrimaryEmail());
        return user;
    }

    public List<User> getGoogleUserList(String id) throws Exception {
        Directory directoryService = getDirectoryService(id);

        // https://developers.google.com/admin-sdk/directory/v1/reference/users/list
        Users googleUsers = directoryService.users().list()
                .setDomain("unirita.co.jp")
                .setMaxResults(500)
                .setOrderBy("email")
                .setProjection("full")
                .setViewType("domain_public").execute();

        List<User> userList = googleUsers.getUsers()
                .stream().map(googleUser -> {
                    User user = new User();
                    user.setId(googleUser.getId());
                    user.setName(googleUser.getName().getFullName());
                    user.setEmail(googleUser.getPrimaryEmail());
                    return user;
                }).collect(Collectors.toList());

        // TODO: use logger
        System.out.println(userList.size());
        return userList;
    }

    private Directory getDirectoryService(String id) {
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
