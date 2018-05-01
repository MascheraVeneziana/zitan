package org.mascheraveneziana.zitan.web.v1;

import java.util.List;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.service.UserService;
import org.mascheraveneziana.zitan.service.provider.ProviderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderAccountService providerAccountService;

    @GetMapping()
    public List<User> users(OAuth2AuthenticationToken authentication) {
        List<User> userList = userService.getUsers();
        return userList;
    }

    @GetMapping("/me")
    public User me(OAuth2AuthenticationToken authentication) {
        String id = authentication.getPrincipal().getName();

        User user = userService.getUser(id);

        if (user == null) {
            ProviderAccount providerAccount = providerAccountService.getUserById(authentication, id);
            user = new User();
            user.setId(providerAccount.getId());
            user.setName(providerAccount.getName());
            user.setEmail(providerAccount.getEmail());
            userService.saveUser(user);
            // TODO: log "created ner user"
        }

        return user;
    }

    @GetMapping("/id/{id}")
    public User userById(OAuth2AuthenticationToken authentication, @PathVariable("id") String id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ZitanException("not found", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @PostMapping("/new")
    public User create(OAuth2AuthenticationToken authentication) {
        // check at application
        String id = authentication.getPrincipal().getName();
        if (userService.getUser(id) != null) {
            throw new ZitanException("registered user", HttpStatus.BAD_REQUEST);
        }

        // check at provider
        ProviderAccount providerAccount = providerAccountService.getUserById(authentication, id);
        if (providerAccount == null) {
            throw new ZitanException("not google user", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setId(providerAccount.getId());
        newUser.setName(providerAccount.getName());
        newUser.setEmail(providerAccount.getEmail());

        User user = userService.saveUser(newUser);
        return user;
    }

    @ExceptionHandler(ZitanException.class)
    public ResponseEntity<String> handleZitanException(ZitanException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
