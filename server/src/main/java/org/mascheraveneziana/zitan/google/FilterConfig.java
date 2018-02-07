package org.mascheraveneziana.zitan.google;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AllFilter());
        bean.setName("allFilter");
        bean.addUrlPatterns("/login");
        return bean;
    }

}
