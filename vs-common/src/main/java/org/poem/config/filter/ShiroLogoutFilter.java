package org.poem.config.filter;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登出
 * @author poem
 */
public class ShiroLogoutFilter extends LogoutFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroLogoutFilter.class);
    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        logger.info("ShiroConfigRealm.doGetAuthenticationInfo");
        //在这里执行退出系统前需要清空的数据
        Subject subject= getSubject(request,response);
        String redirectUrl = "/v1/user/logout";
        ServletContext context= request.getServletContext();
        try {
            subject.logout();
            context.removeAttribute("error");
        }catch (SessionException e){
            e.printStackTrace();
        }
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}
