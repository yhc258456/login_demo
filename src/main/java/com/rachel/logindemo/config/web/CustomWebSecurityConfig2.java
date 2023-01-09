//package com.rachel.logindemo.config.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rachel.logindemo.bizlogic.CustomAccessDecisionManager;
//import com.rachel.logindemo.bizlogic.CustomFilterInvocationSecurityMetadataSource;
//import com.rachel.logindemo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
//import org.springframework.security.authentication.*;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class CustomWebSecurityConfig2 extends WebSecurityConfigurerAdapter {
//
//    // 配置用户及其对应的角色
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("root").password("123").roles("DBA")
//                .and()
//                .withUser("admin").password("123").roles("ADMIN")
//                .and()
//                .withUser("hangge").password("123").roles("USER");
//    }
//
//    // 配置 URL 访问权限
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // 开启 HttpSecurity 配置
//                .antMatchers("/db/**").hasRole("DBA") // db/** 模式URL需DBA角色
//                .antMatchers("/admin/**").hasRole("ADMIN") // admin/** 模式URL需ADMIN角色
//                .antMatchers("/user/**").hasRole("USER") // user/** 模式URL需USER角色
//                .anyRequest().authenticated() // 用户访问其它URL都必须认证后访问（登录后访问）
//                .and().formLogin()  // 开启登录表单功能
//                .loginPage("/login_page") // 使用自定义的登录页面，不再使用SpringSecurity提供的默认登录页
//                .loginProcessingUrl("/login") // 配置登录请求处理接口，自定义登录页面、移动端登录都使用该接口
//                .usernameParameter("name") // 修改认证所需的用户名的参数名（默认为username）
//                .passwordParameter("passwd") // 修改认证所需的密码的参数名（默认为password）
//                // 定义登录成功的处理逻辑（可以跳转到某一个页面，也可以返会一段 JSON）
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest req,
//                                                        HttpServletResponse resp,
//                                                        Authentication auth)
//                            throws IOException, ServletException {
//                        // 我们可以跳转到指定页面
//                        // resp.sendRedirect("/index");
//
//                        // 也可以返回一段JSON提示
//                        // 获取当前登录用户的信息，在登录成功后，将当前登录用户的信息一起返回给客户端
//                        Object principal = auth.getPrincipal();
//                        resp.setContentType("application/json;charset=utf-8");
//                        PrintWriter out = resp.getWriter();
//                        resp.setStatus(200);
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("status", 200);
//                        map.put("msg", principal);
//                        ObjectMapper om = new ObjectMapper();
//                        out.write(om.writeValueAsString(map));
//                        out.flush();
//                        out.close();
//                    }
//                })
//                // 定义登录失败的处理逻辑（可以跳转到某一个页面，也可以返会一段 JSON）
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest req,
//                                                        HttpServletResponse resp,
//                                                        AuthenticationException e)
//                            throws IOException, ServletException {
//                        resp.setContentType("application/json;charset=utf-8");
//                        PrintWriter out = resp.getWriter();
//                        resp.setStatus(401);
//                        Map<String, Object> map = new HashMap<>();
//                        // 通过异常参数可以获取登录失败的原因，进而给用户一个明确的提示。
//                        map.put("status", 401);
//                        if (e instanceof LockedException) {
//                            map.put("msg", "账户被锁定，登录失败!");
//                        }else if(e instanceof BadCredentialsException){
//                            map.put("msg","账户名或密码输入错误，登录失败!");
//                        }else if(e instanceof DisabledException){
//                            map.put("msg","账户被禁用，登录失败!");
//                        }else if(e instanceof AccountExpiredException){
//                            map.put("msg","账户已过期，登录失败!");
//                        }else if(e instanceof CredentialsExpiredException){
//                            map.put("msg","密码已过期，登录失败!");
//                        }else{
//                            map.put("msg","登录失败!");
//                        }
//                        ObjectMapper mapper = new ObjectMapper();
//                        out.write(mapper.writeValueAsString(map));
//                        out.flush();
//                        out.close();
//                    }
//                })
//                .permitAll() // 允许访问登录表单、登录接口
//                .and().csrf().disable(); // 关闭csrf
//    }
//
//}
