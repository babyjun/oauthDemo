package com.john.oauthDemo.config;

import com.john.oauthDemo.handler.JwtTokenEnhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author create by John on 2019/2/21
 * @version 1.0.0
 * @description
 */
@Configuration
public class JwtTokenConfig {

  @Bean
  public TokenStore jwtTokenSatore(){
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  /**
   * token生成处理：指定签名
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter(){
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey("internet_plus");
    return accessTokenConverter;
  }

  @Bean
  public TokenEnhancer jwtTokenEnhancer(){
    return new JwtTokenEnhancer();
  }
}
