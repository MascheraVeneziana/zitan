package org.mascheraveneziana.zitan.web.v1;

import java.util.List;

import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.mascheraveneziana.zitan.service.UserService;
import org.mascheraveneziana.zitan.service.provider.ProviderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ProviderUserService providerUserService;

    /**
     * アプリケーションにおける全ユーザーの情報を返す
     * @param authentication
     * @return
     * @throws Exception
     */
    @GetMapping()
    public List<User> users(OAuth2AuthenticationToken authentication) throws Exception {
        List<User> userList = userService.getUsers();
        return userList;
    }

    /**
     * アプリケーションにおけるエンドユーザーの情報を返す
     * @param authentication
     * @return
     * @throws Exception
     */
    @GetMapping("/me")
    public User me(OAuth2AuthenticationToken authentication) throws Exception {
            String id = authentication.getPrincipal().getName();
            User user = userService.getUser(id);
            return user;
    }

    /**
     * ユーザーをアプリケーションに新しく登録する
     * @param authentication
     * @param user
     * @return
     * @throws Exception 既にアプリケーションに存在するか、プロバイダーに登録が無い場合はエラー
     */
    @PostMapping("/new")
    public User create(OAuth2AuthenticationToken authentication, @RequestBody User user) throws Exception {
        // check at application
        if (userService.getUser(user.getId()) != null) {
            throw new Exception("registered user");
        }

        // check at provider
        ProviderUser providerUser = providerUserService.getUserById(authentication, user.getId());
        if (providerUser == null) {
            throw new Exception("not google user");
        }
        
        User before = new User();
        before.setId(providerUser.getId());
        before.setName(providerUser.getName());
        before.setEmail(providerUser.getEmail());

        User after = userService.saveUser(before);
        return after;
    }

}
