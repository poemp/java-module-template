package org.poem.config.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.poem.OAuthIgnoreUtils;
import org.poem.config.filter.ShiroOauthFilter;
import org.poem.config.ralm.ShiroConfigRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author poem
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * / 通用过滤器，任何请求允许访问
     * anon(AnonymousFilter.class),
     * // 表单认证过滤器
     * authc(FormAuthenticationFilter.class),
     * // 基于Http请求的认证过滤器
     * authcBasic(BasicHttpAuthenticationFilter.class),
     * // 登出过滤器
     * logout(LogoutFilter.class),
     * // 不创建Session过滤器
     * noSessionCreation(NoSessionCreationFilter.class),
     * // 权限认证过滤器
     * perms(PermissionsAuthorizationFilter.class),
     * // 端口过滤器
     * port(PortFilter.class),
     * //请求处理为权限的一种过滤器
     * rest(HttpMethodPermissionFilter.class),
     * // 角色过滤器
     * roles(RolesAuthorizationFilter.class),
     * // SSL过滤器 ssl(SslFilter.class),
     * // 用户过滤器，检测用户是否登录
     * user(UserFilter.class);
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        logger.info("shiroconfiguration-shirFilter");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会拦截的链接
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        Set<String> path = OAuthIgnoreUtils.getOAthodPath("org.poem");
        for (String s : path) {
            logger.info("Mapped No OAuth:" + s);
            filterChainDefinitionMap.put(s, "anon");
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        Map<String, Filter> filters = new HashMap<>(2);
//        filters.put("logout", getShiroLogoutFilter());
        filters.put("oauth", getShiroOAuth2Filter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

//    /**
//     * 登出拦截
//     * @return
//     */
//    @Bean
//    ShiroLogoutFilter getShiroLogoutFilter() {
//        return new ShiroLogoutFilter();
//    }

    /**
     * 自定义拦截器
     *
     * @return
     */
    @Bean
    public ShiroOauthFilter getShiroOAuth2Filter() {
        return new ShiroOauthFilter();
    }

    /**
     * 访问没有权限的页面的处理
     * UnauthorizedUrl 无效果
     *
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver s = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/reLogin");
        s.setExceptionMappings(properties);
        return s;
    }


    /**
     * 配置自定义的权限登录器
     *
     * @return
     */
    @Bean
    public ShiroConfigRealm shiroConfigRealm() {
        logger.info("配置自定义的权限登录器: shiroConfigRealm()");
        ShiroConfigRealm shiroConfigRealm = new ShiroConfigRealm();
        return shiroConfigRealm;
    }

    /**
     * 配置核心安全事务管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        logger.info("配置核心安全事务管理器: securityManager()");
        securityManager.setRealm(shiroConfigRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.info("ShiroConfiguration.getLifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }

    /**
     * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
     * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
     *
     * @return
     */
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager() {
        logger.info("ShiroConfiguration.ehCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * Sets the underlying delegate {@link SessionManager} instance that will be used to support this implementation's
     * <tt>SessionManager</tt> method calls.
     * <p/>
     * This <tt>SecurityManager</tt> implementation does not provide logic to support the inherited
     * <tt>SessionManager</tt> interface, but instead delegates these calls to an internal
     * <tt>SessionManager</tt> instance.
     * <p/>
     * If a <tt>SessionManager</tt> instance is not set, a default one will be automatically created and
     * initialized appropriately for the the existing runtime environment.
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        return new DefaultWebSessionManager();
    }


    /**
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
