package org.mascheraveneziana.zitan.service;

import org.mascheraveneziana.zitan.domain.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    @Autowired
    private System system;

    public System system() {
        return system;
    }

}
