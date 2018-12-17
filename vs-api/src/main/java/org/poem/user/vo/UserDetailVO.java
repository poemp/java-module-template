package org.poem.user.vo;

/**
 * @author poem
 */
public interface UserDetailVO {

    /**
     * 是否锁定
     * @return
     */
    public Boolean getLocked();

    /**
     * 获取密码
     * @return
     */
    public String getPassword();

    /**
     * 获取用户名
     * @return
     */
    public String getUserName();
}
