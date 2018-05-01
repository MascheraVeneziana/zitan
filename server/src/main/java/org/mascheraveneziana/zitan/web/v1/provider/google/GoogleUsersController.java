package org.mascheraveneziana.zitan.web.v1.provider.google;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.mascheraveneziana.zitan.service.provider.google.GoogleUserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/provider/google/users")
public class GoogleUsersController {

    private GoogleUserService userService = new GoogleUserService();

    @GetMapping()
    public List<ProviderUser> users(OAuth2AuthenticationToken authentication) throws Exception {
      List<ProviderUser> users = userService.getUsers(authentication);
        return users;
    }

    @GetMapping("/me")
    public ProviderUser me(OAuth2AuthenticationToken authentication) throws Exception {
      ProviderUser user = userService.getMe(authentication);
        return user;
    }
    
    @GetMapping("/id/{id}")
    public ProviderUser userById(OAuth2AuthenticationToken authentication,
        @PathVariable String id) throws Exception {
      ProviderUser user = userService.getUserById(authentication, id);
      return user;
    }
    
    @GetMapping("/email/{mail}")
    public ProviderUser userByEmail(OAuth2AuthenticationToken authentication,
        @PathVariable String email) throws Exception {
      ProviderUser user = userService.getUserByEmail(authentication, email);
      return user;
    }

}
