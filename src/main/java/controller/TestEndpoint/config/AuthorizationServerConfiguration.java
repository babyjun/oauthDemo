package controller.TestEndpoint.config;

import controller.TestEndpoint.handler.JwtTokenEnhancer;
import controller.TestEndpoint.handler.MyRedisTokenStore;
import controller.TestEndpoint.service.MyUserDetailsService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


/**
 * .授权服务
 *
 * @author john
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private TokenEnhancer jwtTokenEnhancer;


  /**
   * . Basic 密码认真模式 Web-Client: key Web, password Web Mobile-Client: key Mobile, password Mobile
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory().withClient("Web")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("read", "write")
        .authorities("ROLE_WEB")
        .secret(passwordEncoder.encode("Web")).resourceIds("Web")
        .and()
        .withClient("Mobile")
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("read", "write")
        .authorities("ROLE_MOBILE")
        .secret("Mobile").resourceIds("Web");
  }

  /**
   * . 配置授权访问端点和令牌服务
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    // redis 保存token
    endpoints
        .tokenStore(tokenStore)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);

    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> enhancerList = new ArrayList<>();
    enhancerList.add(jwtTokenEnhancer);
    enhancerList.add(jwtAccessTokenConverter);
    enhancerChain.setTokenEnhancers(enhancerList);

    endpoints.tokenEnhancer(enhancerChain)
        .accessTokenConverter(jwtAccessTokenConverter);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.checkTokenAccess("isAuthenticated()");
  }

  /**
   * .注入redisTokenStore，使用redis存储token
   */
  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setTokenStore(tokenStore);
    return tokenServices;
  }

  /**
   * .redis保存
   */
/*  @Bean
  public TokenStore redisTokenStore() {
    return new MyRedisTokenStore(redisConnectionFactory);
  }*/
}
