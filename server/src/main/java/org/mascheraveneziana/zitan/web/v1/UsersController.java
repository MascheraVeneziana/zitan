package org.mascheraveneziana.zitan.web.v1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mascheraveneziana.zitan.ZitanException;
import org.mascheraveneziana.zitan.domain.User;
import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.mascheraveneziana.zitan.service.UserService;
import org.mascheraveneziana.zitan.service.provider.ProviderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

            if (user == null) {
                ProviderUser providerUser = providerUserService.getMe(authentication);
                user = new User();
                user.setId(providerUser.getId());
                user.setName(providerUser.getName());
                user.setEmail(providerUser.getEmail());
                userService.saveUser(user);
                // TODO: log "created ner user"
            }

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
    public User create(OAuth2AuthenticationToken authentication, @RequestBody User user)
            throws ZitanException, Exception {

        // check at application
        if (userService.getUser(user.getId()) != null) {
            throw new ZitanException("registered user", HttpStatus.BAD_REQUEST);
        }

        // check at provider
        ProviderUser providerUser = providerUserService.getUserById(authentication, user.getId());
        if (providerUser == null) {
            throw new ZitanException("not google user", HttpStatus.BAD_REQUEST);
        }

        User before = new User();
        before.setId(providerUser.getId());
        before.setName(providerUser.getName());
        before.setEmail(providerUser.getEmail());

        User after = userService.saveUser(before);
        return after;
    }

    @ExceptionHandler(ZitanException.class)
    public ResponseEntity<String> handleZitanException(HttpServletRequest request, ZitanException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Throwable e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
