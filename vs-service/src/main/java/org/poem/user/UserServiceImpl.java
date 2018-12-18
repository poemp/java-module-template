package org.poem.user;

import org.poem.vo.UserInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author poem
 */
@Service
public class UserServiceImpl implements UserInfoService {


    @Override
    public UserInfoVO findByUsername(String userName) {
        return null;
    }

    @Override
    public UserInfoVO login(String userName, String password, String ip) {

        return new UserInfoVO() {
            @Override
            public Boolean setLocked(Boolean locked) {
                return false;
            }

            @Override
            public String setPassword(String password) {
                return "123";
            }

            @Override
            public String setUserName(String userName) {
                return "ss";
            }

            @Override
            public Boolean getLocked() {
                return false;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUserName() {
                return "";
            }
        };
    }
}
