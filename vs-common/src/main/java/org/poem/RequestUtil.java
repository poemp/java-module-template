package org.poem;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.poem.jwt.JwtHelper;
import org.poem.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.poem.Constant.AUTHORIZATION;


/**
 * @author poem
 */
public class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * 获取Authorization
     *
     * @param request
     * @return
     */
    public static String getAuthorization(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(AUTHORIZATION);
        }
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return token;
    }


    /**
     * 获取LoginSuccessVo
     *
     * @param request
     * @return
     */
    public static UserInfoVO getLoginSuccessVo(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        try {
            Map<String, Object> map = JwtHelper.extractInfo(authorization);
            if (map == null) {
                return null;
            }
            String token = (String) map.get(Constant.JWT_CLAIM_KEY);
            UserInfoVO userInfoVO = JSONObject.parseObject(token, UserInfoVO.class);
            return userInfoVO;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return null;

    }
}
