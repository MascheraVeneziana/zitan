package org.mascheraveneziana.zitan.config;

import org.mascheraveneziana.zitan.service.provider.ProviderResourceService;
import org.mascheraveneziana.zitan.service.provider.ProviderUserService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleResourceService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderConfig {

    @Bean
    public ProviderUserService providerUserService() {
      return new GoogleUserService();
    }
    
    @Bean
    public ProviderResourceService providerResourceService() {
      return new GoogleResourceService();
    }

}
