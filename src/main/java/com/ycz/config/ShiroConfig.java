package com.ycz.config;/*
 @author ycz
 @date 2021-07-30-23:43
*/


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        //添加shiro内置过滤器
        /**
         * anon : 无需认证即可通过
         * authc : 必须认证才能通过
         * user：必须拥有某个资源授权才能访问
         * perms ： 拥有对某个资源的权限才能访问
         * role ： 拥有某个角色权限才能使用
         */
        Map<String,String> filterMap=new LinkedHashMap<>();
//
//        filterMap.put("/user/add","perms[user:add]");
//        filterMap.put("/user/update","perms[user:update]");
        /**
         *
         filterChainDefinitionMap.put("/logout", "logout");
         // 配置不会被拦截的链接 顺序判断
         filterChainDefinitionMap.put("/static/**", "anon");
         filterChainDefinitionMap.put("/ajaxLogin", "anon");
         filterChainDefinitionMap.put("/login", "anon");
         filterChainDefinitionMap.put("/**", "authc");
         */
        filterMap.put("/sysuser/logout.do","logout"); //不拦截登出
        filterMap.put("/sysuser/login.do","anon");//不拦截登录
        filterMap.put("/user/**","anon");
        filterMap.put("/car/page2.do","anon");
        filterMap.put("/resources/**", "anon"); //不拦截静态文件
        filterMap.put("/upload/**", "anon"); //不拦截静态文件
        filterMap.put("/", "anon"); //不拦截主页面，登录页面
        filterMap.put("/index", "anon");//不拦截主页面，登录页面
        filterMap.put("/sysuser/checkCodeServlet.do","anon");
        //用户管理
        filterMap.put("/user/list.do","roles[admin]");
        filterMap.put("/sysuser/page.do","roles[admin]");
        filterMap.put("/sysuser/add.do","roles[admin]");
        filterMap.put("/sysuser/reset.do","roles[admin]");
        filterMap.put("/sysuser/delete.do","roles[admin]");
        filterMap.put("/sysuser/resetPassword.do","roles[admin]");
        //角色管理
        filterMap.put("/role/page.do","roles[admin]");
        filterMap.put("/role/add.do","roles[admin]");
        filterMap.put("/role/update.do","roles[admin]");
        filterMap.put("/role/all.do","roles[admin]");
        filterMap.put("/role/setRole.do","roles[admin]");
        filterMap.put("/role/userRoles.do","roles[admin]");
        filterMap.put("/role/permissionIds.do","roles[admin]");
        filterMap.put("/role/setRolePermission.do","roles[admin]");
        //权限管理
        filterMap.put("/permission/leftMenu.do","roles[admin]");
        filterMap.put("/permission/all.do","roles[admin]");
        filterMap.put("/permission/page.do","roles[admin]");
        filterMap.put("/permission/add.do","roles[admin]");
        filterMap.put("/permission/update.do","roles[admin]");
        filterMap.put("/permission/delete.do","roles[admin]");
        //车辆管理
        filterMap.put("/*/page.do","roles[salesman]");
        filterMap.put("/*/page2.do","roles[salesman]");
        filterMap.put("/*/add.do","roles[salesman]");
        filterMap.put("/*/edit.do","roles[salesman]");
        filterMap.put("/*/delete.do","roles[salesman]");
        filterMap.put("/*/export.do","roles[salesman]");
        filterMap.put("/*/import.do","roles[salesman]");
        filterMap.put("/**","authc");//拦截所有
        bean.setFilterChainDefinitionMap(filterMap);
        System.out.println("拦截了");
        bean.setUnauthorizedUrl("/noauth");//设置未经授权无法登录

        bean.setLoginUrl("/index");
        return bean;
    }

    //DefaultWebSecurityManger:2
    //@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false", matchIfMissing = true)
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        //关联userRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建Realm对象 需要自定义 ：1
    @Bean(name="userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }


    /**
     * 注入这个是是为了在thymeleaf中使用shiro的自定义tag。
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * 下面的代码是添加注解支持
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
