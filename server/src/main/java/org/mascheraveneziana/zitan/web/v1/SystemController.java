package org.mascheraveneziana.zitan.web.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    @GetMapping()
    public String system() {
        String str = "{ \"name\": \"Zitan\", \"version\": \"v1.0.0\" }";
        return str;
    }

}
