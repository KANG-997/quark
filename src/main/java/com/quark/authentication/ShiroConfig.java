package com.quark.authentication;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean factoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters =  new LinkedHashMap<>();
        filters.put("jwt",new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String,String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/","anon");
        filterRuleMap.put("/static/**","anon");
        filterRuleMap.put("/login","anon");
        filterRuleMap.put("/error","anon");
        filterRuleMap.put("/**","jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

}
