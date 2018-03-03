package org.mascheraveneziana.zitan.web;

import java.io.IOException;
import java.util.Map;

import org.mascheraveneziana.zitan.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;

@RestController
public class HomeController {

    @Autowired
    OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping(path = "/users/me")
    public User home(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(authorizedClient.getAccessToken().getTokenValue());

        // TODO: 動きません。"People API"にプロジェクトを登録する必要があるというエラーが発生する。
        PeopleService peopleService = new PeopleService.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
        System.out.println(peopleService.getServicePath());
        try {
            Person person = peopleService.people().get("people/me").setPersonFields("names,emailAddresses").execute();
            System.out.println(person.getNames());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = authentication.getPrincipal().getAttributes();
        User user = new User();
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));
        return user;
    }

}
