package org.poem.config.ralm;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.poem.Constant;
import org.poem.config.filter.token.OAuthToken;
import org.poem.jwt.JwtHelper;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SignatureException;
import java.util.HashSet;
import java.util.Map;

/**
 * @author poem
 */
public class ShiroConfigRealm extends AuthorizingRealm {


  /** */
  private static final Logger logger = LoggerFactory.getLogger(ShiroConfigRealm.class);


  /**
   * 是否支持
   *
   * @param token
   * @return
   */
  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof OAuthToken;
  }

  /**
   * 认证.登录
   *
   * @param principals
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    logger.info("权限配置-->ShiroConfigRealm.doGetAuthorizationInfo()");
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setStringPermissions(new HashSet<>());
    return info;
  }

  /**
   * 授权
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    String accessToken = (String) token.getPrincipal();

    UserInfoVO userInfoVO;

    logger.info("ShiroConfigRealm.doGetAuthenticationInfo");
    try {
      Map<String, Object> map = JwtHelper.extractInfo(accessToken);
      if (map == null){
        throw new AuthenticationException();
      }
      String s = (String) map.get(Constant.JWT_CLAIM_KEY);
      userInfoVO = JSONObject.parseObject(s, UserInfoVO.class);
    } catch (Exception e) {
      e.printStackTrace();
      if (e instanceof ExpiredJwtException){
        throw new LockedAccountException("the key is expire");
      }else if( e instanceof SignatureException){
        throw new LockedAccountException("the key is incorrect");
      }else{
        throw new LockedAccountException("the key is error");
      }
    }
    logger.info("----->>userInfoVO=" + userInfoVO);
    //会抛出账号不存在的异常
    if (userInfoVO == null) {
      return null;
    }
    //锁住了
    if (userInfoVO.getLocked() != null && userInfoVO.getLocked()) {
      throw new LockedAccountException("locked this account");
    }
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userInfoVO, accessToken, getName());
    // 放入shiro.调用CredentialsMatcher检验密码
    //会抛出密码错误
    return info;
  }
}
