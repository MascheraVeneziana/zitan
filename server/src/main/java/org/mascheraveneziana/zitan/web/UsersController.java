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
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

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
            String googleId = authentication.getPrincipal().getName();

            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
            Credential credential = new GoogleCredential()
                    .setAccessToken(authorizedClient.getAccessToken().getTokenValue());

            PeopleService peopleService = new PeopleService.Builder(
                    new NetHttpTransport(), new JacksonFactory(), credential).build();

            // https://developers.google.com/people/api/rest/v1/people/get
            Person person = peopleService.people().get("people/" + googleId)
                    .setPersonFields("names,emailAddresses")
                    .execute();

            Name googleName = person.getNames()
                    .stream().filter(name -> name.getMetadata().getPrimary()).findFirst().get();
            EmailAddress googleEmailAddress = person.getEmailAddresses()
                    .stream().filter(emailAddress -> emailAddress.getMetadata().getPrimary()).findFirst().get();

            User user = new User();
            // TODO: GoogleのIDはlongの最大値を超過するので、String型にするか？
//            user.setId(googleId);
            user.setName(googleName.getDisplayName());
            user.setEmail(googleEmailAddress.getValue());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
