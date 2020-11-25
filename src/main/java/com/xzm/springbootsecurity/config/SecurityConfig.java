package com.xzm.springbootsecurity.config;

import com.xzm.springbootsecurity.service.RbacService;
import com.xzm.springbootsecurity.service.impl.MyUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证,在方法执行前进行验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsServiceImpl myUserDetailsService;

    public SecurityConfig(MyUserDetailsServiceImpl myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
             .antMatchers("/", "/test").permitAll()
             .anyRequest()
             //.authenticated()   // 其他地址的访问均需验证权限
             .access("@rbacService.hasPermission(request, authentication)")
             .and()
             .formLogin()
             //.loginPage("/login")   //  登录页,开启就是自定义注册界面了
             .failureUrl("/login-error").permitAll()
             .and()
             .logout()
             .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
