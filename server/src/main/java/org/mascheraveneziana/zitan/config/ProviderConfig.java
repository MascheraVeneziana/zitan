package org.mascheraveneziana.zitan.config;

import org.mascheraveneziana.zitan.service.provider.ProviderAccountService;
import org.mascheraveneziana.zitan.service.provider.ProviderStatusService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleAccountService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleStatusService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ProviderConfig {

    @Bean
    @Primary
    public ProviderAccountService providerAccountService() {
      return new GoogleAccountService();
    }

//    @Bean
    public ProviderStatusService providerStatusService() {
        return new GoogleStatusService();
    }

}
