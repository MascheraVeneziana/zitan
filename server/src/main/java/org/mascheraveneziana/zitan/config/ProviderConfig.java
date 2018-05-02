package org.mascheraveneziana.zitan.config;

import org.mascheraveneziana.zitan.service.provider.ProviderAccountService;
import org.mascheraveneziana.zitan.service.provider.ProviderCalendarService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleAccountService;
import org.mascheraveneziana.zitan.service.provider.google.GoogleCalendarService;
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

    @Bean
    @Primary
    public ProviderCalendarService providerCalendarService() {
        return new GoogleCalendarService();
    }

}
