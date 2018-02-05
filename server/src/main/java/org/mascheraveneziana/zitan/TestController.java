package org.mascheraveneziana.zitan;

import org.mascheraveneziana.zitan.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public User test() {
        User user = new User(1, "Akira");
        return user;
    }

}
