package org.mascheraveneziana.zitan.web.v1.provider.google;

import java.util.List;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.provider.ProviderAccount;
import org.mascheraveneziana.zitan.service.provider.ProviderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/provider/google/users")
public class GoogleUsersController {

    @Autowired
    private ProviderAccountService accountService;

    @GetMapping
    public List<ProviderAccount> users(OAuth2AuthenticationToken authentication) {
        List<ProviderAccount> users = accountService.getUsers(authentication);
        return users;
    }

    @GetMapping("/me")
    public ProviderAccount me(OAuth2AuthenticationToken authentication) throws Exception {
        String id = authentication.getPrincipal().getName();
        ProviderAccount user = accountService.getUserById(authentication, id);
        return user;
    }

    @GetMapping("/id/{id}")
    public ProviderAccount userById(OAuth2AuthenticationToken authentication,
            @PathVariable String id) throws Exception {
        ProviderAccount user = accountService.getUserById(authentication, id);
        return user;
    }

    @ExceptionHandler(ZitanException.class)
    public ResponseEntity<String> handleZitanException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
