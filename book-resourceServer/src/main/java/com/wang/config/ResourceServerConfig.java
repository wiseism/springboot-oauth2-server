package com.wang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ResourceServerConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    protected ResourceServerConfiguration BookResources() {

        ResourceServerConfiguration resource =  new ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Arrays.<ResourceServerConfigurer>asList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                TokenStore tokenStore = new JdbcTokenStore(dataSource);
                OAuth2AuthenticationManager auth2AuthenticationManager = new OAuth2AuthenticationManager();
                JdbcAccessPermissionManager jdbcAccessPermissionManager=    new JdbcAccessPermissionManager(dataSource);
                jdbcAccessPermissionManager.setClientDetailsService(new JdbcClientDetailsService(dataSource));
                auth2AuthenticationManager.setAccessPermissionManager(jdbcAccessPermissionManager);
                resources.authenticationManager(auth2AuthenticationManager);
                resources.resourceId("book-service").tokenStore(tokenStore).stateless(true);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests().
                        anyRequest().authenticated();
                http.cors().and().csrf().disable();
            }

        }));

        return resource;

    }


}