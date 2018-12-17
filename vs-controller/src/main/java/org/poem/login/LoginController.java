package org.poem.login;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.poem.IpUtils;
import org.poem.SpringUtils;
import org.poem.config.annotation.ShiroOauthodIgnore;
import org.poem.user.UserInfoService;
import org.poem.vo.ResultVo;
import org.poem.vo.UserInfoVO;
import org.poem.vo.UserLoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author poem
 */
@ShiroOauthodIgnore
@RestController
@RequestMapping("/v1/user")
@Api(value = "/v1/user", description = "用户")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * 登陆
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登陆", notes = "登陆", httpMethod = "POST")
    public ResultVo<UserInfoVO> login(String userName, String password,  HttpServletRequest request) {
        logger.info("find :userName: " + userName + " password:" + password);
        String ipAddr = IpUtils.getIpAddr(request);
        UserInfoService userInfoService = SpringUtils.getBean(UserInfoService.class);
        return userInfoService.login(userName,password, ipAddr);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    @ApiOperation(value = "用户登出", notes = "登出", httpMethod = "POST")
    public ResultVo<String> loginout(HttpServletRequest request) {
        //清空session
        request.getSession().setMaxInactiveInterval(0);
        logger.info("loginout");
        return new ResultVo<>(0, "登出成功");
    }

}
