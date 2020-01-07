package com.wang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.ArrayList;

/**
 * Created by wangxiangyun on 2019/4/3.
 */
@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig {
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri("http://localhost:9000/oauth/token");
        clientCredentialsResourceDetails.setClientId("book_client");
        clientCredentialsResourceDetails.setClientSecret("book_client");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("read");
        clientCredentialsResourceDetails.setScope(arrayList);
        clientCredentialsResourceDetails.setGrantType("client_credentials");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails, oAuth2ClientContext);
        return oAuth2RestTemplate;
    }
    @Bean
    public OAuth2RestTemplate codeRestTemplate() {
        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setAccessTokenUri("http://localhost:9000/oauth/token");
        authorizationCodeResourceDetails.setClientId("book_client");
        authorizationCodeResourceDetails.setUserAuthorizationUri("http://localhost:9000/oauth/authorize");
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(authorizationCodeResourceDetails, oAuth2ClientContext);
        return oAuth2RestTemplate;
    }


}