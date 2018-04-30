package org.mascheraveneziana.zitan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/*", "/asetts/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .oauth2Login()
                .and()
            .logout().logoutUrl("/api/v1/logout");
    }

}
