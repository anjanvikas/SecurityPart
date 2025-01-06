package com.SecurityPart.SecurityPart.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfig {

    @Autowired
    private OAuth2Props oauth2Props;
    
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(oauth2Props.getClientId())
                .clientSecret(oauth2Props.getClientSecret())
                .scope(oauth2Props.getScope())
                .authorizationUri(oauth2Props.getAuthorizationUri())
                .tokenUri(oauth2Props.getTokenUri())
                .userInfoUri(oauth2Props.getUserInfoUri())
                .redirectUri(oauth2Props.getRedirectUri())
                .clientName("Google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .jwkSetUri(oauth2Props.getJwtSetUri())
                .build();
    }
}