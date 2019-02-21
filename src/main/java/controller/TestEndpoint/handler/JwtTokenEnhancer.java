package controller.TestEndpoint.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author create by John on 2019/2/21
 * @version 1.0.0
 * @description
 */
public class JwtTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
      OAuth2Authentication oAuth2Authentication) {
    Map<String,Object> info = new HashMap<>();
    info.put("provider","Fant.J");
    //设置附加信息
    ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
    return oAuth2AccessToken;
  }
}
