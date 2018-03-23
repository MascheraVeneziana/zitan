package org.mascheraveneziana.zitan.web;

import java.util.ArrayList;
import java.util.List;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Users;

@RestController
public class UsersController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping(path = "/users")
    public List<User> users(OAuth2AuthenticationToken authentication) throws Exception {
        try {
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            Credential credential = new GoogleCredential().setAccessToken(authorizedClient.getAccessToken().getTokenValue());
            Directory directoryService = new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
//                    .setApplicationName("")
                    .build();
            Users googleUsers = directoryService.users().list()
                    .setDomain("unirita.co.jp")
                    .setMaxResults(500)
                    .setOrderBy("email")
                    .setProjection("full")
                    .setViewType("domain_public")
                    .execute();
            List<User> userList = new ArrayList<>();
            for (com.google.api.services.admin.directory.model.User user : googleUsers.getUsers()) {
                User zitanUser = new User();
//                zitanUser.setId(Long.parseLong(user.getId()));
                zitanUser.setName(user.getName().getFullName());
                zitanUser.setEmail(user.getName().getFullName());
                userList.add(zitanUser);
            }
            System.out.println(userList.size());
            return userList;
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
            throw e;
        }
    }

}
