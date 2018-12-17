package org.poem.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author poem
 */
@Api(value = "登陆实体",tags = {"01-登陆"})
public class UserLoginVo {

    @ApiModelProperty(value = "登陆名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
