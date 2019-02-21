package controller.TestEndpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * .资源服务器
 *
 * @author john
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId("Web").stateless(true);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 忽略/oauth/** ,主要是忽略登录接口
        .antMatchers("/oauth/**").permitAll()
        .antMatchers("/mobile/isUpdate").permitAll()
        .antMatchers("/mobile/configs").permitAll()
        .antMatchers("/mobile/apk/download").permitAll()
        .antMatchers("/mobile/online").permitAll()
        .antMatchers("/libs/**").permitAll()
        .antMatchers("/mobile/orgs").permitAll()
        .antMatchers("/mobile/users/register").permitAll()
        .antMatchers("/mobile/userPointSetting").permitAll()//积分配置
        .antMatchers("/wxPay/notify").permitAll()
        .antMatchers("/mobile/users/forget-password").permitAll()
        .anyRequest().authenticated();
  }
}
