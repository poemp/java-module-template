package org.poem.vo;

import org.poem.user.vo.UserDetailVO;

/**
 * @author poem
 */
public interface UserInfoVO extends UserDetailVO {

    /**
     * 设置是否锁定
     * @param locked
     * @return
     */
    public Boolean setLocked(Boolean locked);

    /**
     * 设置密码
     * @param password
     * @return
     */
    public String setPassword(String password);

    /**
     * 设置用户名
     * @param userName
     * @return
     */
    public String setUserName(String userName);


}
