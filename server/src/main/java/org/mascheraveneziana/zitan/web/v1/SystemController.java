package org.mascheraveneziana.zitan.web.v1;

import org.mascheraveneziana.zitan.domain.System;
import org.mascheraveneziana.zitan.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping()
    public System system() {
        System system = systemService.system();
        return system;
    }

}
