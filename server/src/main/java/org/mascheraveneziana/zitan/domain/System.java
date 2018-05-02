package org.mascheraveneziana.zitan.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class System {

    // TODO: 適切な取得方法は？
    private String version = "V1.0.0";

    @Value("${application.name}")
    private String applicationName;

}
