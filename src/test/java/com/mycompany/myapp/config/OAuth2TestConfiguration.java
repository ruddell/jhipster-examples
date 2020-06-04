package com.mycompany.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2TestConfiguration implements ResourceServerConfigurer {
    // We set stateless to false to prevent the SecurityContext to be cleared when the respective filter is invoked
    // Otherwise it is impossible to use @WithMockUser in combination with oauth2 in tests
    @Override
    public void configure(ResourceServerSecurityConfigurer security) {
        security.stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) {
    }
}
