package org.poem.user;


import org.poem.vo.ResultVo;
import org.poem.vo.UserInfoVO;
import org.poem.vo.UserLoginVo;

/**
 * @author poem
 */
public interface UserInfoService {

    /**
     * 根据名字查询用户信息
     * @param userName
     * @return
     */
    UserInfoVO findByUsername(String userName);


    /**
     * 登陆
     * @param userName
     * @param password
     * @param ip
     * @return
     */
    ResultVo<UserInfoVO> login(String userName, String password, String ip);

}
