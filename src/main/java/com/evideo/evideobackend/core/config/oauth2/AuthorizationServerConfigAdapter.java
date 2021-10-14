package com.evideo.evideobackend.core.config.oauth2;

import com.evideo.evideobackend.unsecur.rest.VideoUnSecurRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfigAdapter extends AuthorizationServerConfigurerAdapter {
    static org.apache.log4j.Logger log = Logger.getLogger(VideoUnSecurRest.class.getName());

    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Inject
    private AuthenticationManager authenticationManager;
    @Inject
    private UserDetailsService usrSvc;
    @Inject
    private PasswordEncoder oauthClientPasswordEncoder;


    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(oauthClientPasswordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore());
//        endpoints.userApprovalHandler(this.userApprovalHandler());
        endpoints.authenticationManager(authenticationManager);
         endpoints.userDetailsService(usrSvc);
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(this.tokenStore());
        return store;
    }

//    @Bean
//    public UserApprovalHandler userApprovalHandler() {
//        ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
//        handler.setApprovalStore(this.approvalStore());
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory((ClientDetailsService) this.usrSvc));
//        handler.setClientDetailsService((ClientDetailsService) this.usrSvc);
//        return handler;
//    }
}
