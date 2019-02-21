package controller.TestEndpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author create by John on 2019/2/21
 * @version 1.0.0
 * @description
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    return new User(userName,passwordEncoder.encode("123456"),AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
  }
}
