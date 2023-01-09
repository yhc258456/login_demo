package com.rachel.logindemo.config.web;

import com.rachel.logindemo.bizlogic.CustomAccessDecisionManager;
import com.rachel.logindemo.bizlogic.CustomFilterInvocationSecurityMetadataSource;
import com.rachel.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    // 配置用户及其对应的角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

//    // 配置 URL 访问权限
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // 开启 HttpSecurity 配置
//                .antMatchers("/admin/**").hasRole("ADMIN") // admin/** 模式URL必须具备ADMIN角色
//                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')") // 该模式需要ADMIN或USER角色
//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')") // 需ADMIN和DBA角色
//                .anyRequest().authenticated() // 用户访问其它URL都必须认证后访问（登录后访问）
//                .and().formLogin().loginProcessingUrl("/login").permitAll() // 开启表单登录并配置登录接口
//                .and().csrf().disable(); // 关闭csrf
//    }


    // 配置基于数据库的 URL 访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(accessMustRoles());
                        object.setAccessDecisionManager(rolesCheck());
                        return object;
                    }
                })
                .and().formLogin()
                .permitAll()//开启表单登录并配置登录接口
                .and().csrf().disable(); // 关闭csrf
    }


    @Bean
    public CustomFilterInvocationSecurityMetadataSource accessMustRoles() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    @Bean
    public CustomAccessDecisionManager rolesCheck() {
        return new CustomAccessDecisionManager();
    }


    // 配置角色继承关系
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_DBA > ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


}
