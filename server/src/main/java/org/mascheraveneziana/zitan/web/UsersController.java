package org.mascheraveneziana.zitan.web;

import java.util.List;
import java.util.stream.Collectors;

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
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            Credential credential = new GoogleCredential()
                    .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

            Directory directoryService = new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                    // .setApplicationName("")
                    .build();

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
//                        user.setId(googleUser.getId());
                        user.setName(googleUser.getName().getFullName());
                        user.setEmail(googleUser.getPrimaryEmail());
                        return user;
                    }).collect(Collectors.toList());

            System.out.println(userList.size());
            return userList;
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping(path = "/users/me")
    public User me(OAuth2AuthenticationToken authentication) throws Exception {
        try {
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            Credential credential = new GoogleCredential()
                    .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

            Directory directoryService = new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                    // .setApplicationName("")
                    .build();

            String googleId = authentication.getPrincipal().getName();

            // https://developers.google.com/admin-sdk/directory/v1/reference/users/get
            com.google.api.services.admin.directory.model.User googleUser =
                    directoryService.users().get(googleId).setViewType("domain_public").execute();

            User user = new User();
            // TODO: GoogleのIDはlongの最大値を超過するので、String型にするか？
//            user.setId(googleId);
            user.setName(googleUser.getName().getFullName());
            user.setEmail(googleUser.getPrimaryEmail());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
