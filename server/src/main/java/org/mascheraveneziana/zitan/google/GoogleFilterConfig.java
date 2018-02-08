package org.mascheraveneziana.zitan.google;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleFilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new GoogleFilter());
        bean.setName("allFilter");
        bean.addUrlPatterns("/login");
        return bean;
    }

}
