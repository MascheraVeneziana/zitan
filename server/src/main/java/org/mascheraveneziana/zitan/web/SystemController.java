package org.mascheraveneziana.zitan.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {

    @RequestMapping(method = RequestMethod.GET)
    public String system() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"1.0.0\" }";
        return str;
    }

}
