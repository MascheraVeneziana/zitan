package org.mascheraveneziana.zitan.web.v1;

import java.util.List;

import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.service.UserService;
import org.mascheraveneziana.zitan.service.google.GoogleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleUserService googleUserService;

    @GetMapping(path = "/users")
    public List<User> users(OAuth2AuthenticationToken authentication) throws Exception {
        List<User> userList = userService.getUsers();
        return userList;
    }

    @GetMapping(path = "/users/me")
    public User me(OAuth2AuthenticationToken authentication) throws Exception {
        try {
            String googleId = authentication.getPrincipal().getName();
            User user = userService.getUser(googleId);
            if (user == null) {
              return this.create(authentication);              
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping(path = "/users/me/google")
    public User googleMe(OAuth2AuthenticationToken authentication) throws Exception {
        String googleId = authentication.getPrincipal().getName();
        User user = googleUserService.getGoogleUser(googleId);
        return user;
    }

    @GetMapping(path = "/users/google")
    public List<User> google(OAuth2AuthenticationToken authentication) throws Exception {
        String googleId = authentication.getPrincipal().getName();
        List<User> userList = googleUserService.getGoogleUserList(googleId);
        return userList;
    }

    @PostMapping(path = "/users/new")
    public User create(OAuth2AuthenticationToken authentication) throws Exception {
        String googleId = authentication.getPrincipal().getName();
        if (userService.getUser(googleId) != null) {
            throw new Exception("registered user");
        }

        User googleUser = googleUserService.getGoogleUser(googleId);
        if (googleUser == null) {
            throw new Exception("not google user");
        }
        User newUser = userService.saveUser(googleUser);
        return newUser;
    }

}
