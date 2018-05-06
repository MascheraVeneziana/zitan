package org.mascheraveneziana.zitan.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class System {

    @Value("${application.version}")
    private String version;

    @Value("${application.name}")
    private String applicationName;

}
