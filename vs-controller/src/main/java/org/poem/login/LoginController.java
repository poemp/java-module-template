package org.poem.login;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.poem.Constant;
import org.poem.IpUtils;
import org.poem.SpringUtils;
import org.poem.config.annotation.ShiroOauthodIgnore;
import org.poem.jwt.JwtHelper;
import org.poem.user.UserInfoService;
import org.poem.vo.ResultVo;
import org.poem.vo.UserInfoVO;
import org.poem.vo.auth.LoginSuccessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author poem
 */
@ShiroOauthodIgnore
@RestController
@RequestMapping("/v1/user")
@Api(value = "/v1/user", tags = {"01-授权管理"}, description = "用户")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * 登陆
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "0101-用户登陆", notes = "01-授权管理", httpMethod = "POST")
    public ResultVo<LoginSuccessVO> login(String userName, String password, HttpServletRequest request) {
        logger.info("find :userName: " + userName + " password:" + password);
        String ipAddr = IpUtils.getIpAddr(request);
        if (StringUtils.isBlank(userName)) {
            return new ResultVo<>(-9999, null, "用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return new ResultVo<>(-9999, null, "密码不能为空。");
        }
        UserInfoService userInfoService = SpringUtils.getBean(UserInfoService.class);
        UserInfoVO userInfoVO = userInfoService.login(userName, password, ipAddr);
        if (userInfoVO == null) {
            return new ResultVo<>(-9999, null, "用户名不存在。");
        }
        if (userInfoVO.getLocked() != null && userInfoVO.getLocked()) {
            return new ResultVo<>(-9999, null, "账号被锁定。");
        }
        Map<String, Object> claims = new HashMap<>(0);
        claims.put(Constant.JWT_CLAIM_KEY, JSON.toJSONString(userInfoVO));
        String token = JwtHelper.createJWT(claims, Constant.JWT_TTL);
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        loginSuccessVO.setToken(token);
        loginSuccessVO.setUserInfoVO(userInfoVO);
        return new ResultVo<>(0, loginSuccessVO, "账号被锁定。");
        return;
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    @ApiOperation(value = "0102-用户登出", notes = "登出", httpMethod = "POST")
    public ResultVo<String> loginout(HttpServletRequest request) {
        //清空session
        request.getSession().setMaxInactiveInterval(-1);
        return new ResultVo<>(0, "登出成功");
    }

}
