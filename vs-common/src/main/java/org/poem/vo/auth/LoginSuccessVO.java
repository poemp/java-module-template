package org.poem.vo.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.poem.vo.UserInfoVO;

@Api(value = "登陆成功。")
public class LoginSuccessVO {

    @ApiModelProperty(value = "鉴权")
    private String token;

    @ApiModelProperty(value = "信息")
    private UserInfoVO userInfoVO;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }
}
