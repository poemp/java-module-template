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
     * @param userLoginVo´
     * @return
     */
    ResultVo<UserInfoVO> login(UserLoginVo userLoginVo, String ip);

}
