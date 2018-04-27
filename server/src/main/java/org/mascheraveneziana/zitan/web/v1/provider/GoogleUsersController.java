package org.mascheraveneziana.zitan.web.v1.provider;

import java.util.List;

import org.mascheraveneziana.zitan.domain.provider.ProviderUser;
import org.mascheraveneziana.zitan.service.provider.GoogleUserService;
import org.mascheraveneziana.zitan.service.provider.ProviderUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/provider/google/users")
public class GoogleUsersController {

    private ProviderUserService userService = new GoogleUserService();

    @GetMapping("/users")
    public List<ProviderUser> users() throws Exception {
        return null;
    }

    @GetMapping("/users/me")
    public ProviderUser me() throws Exception {
        return null;
    }

    // TODO 未実装

}
