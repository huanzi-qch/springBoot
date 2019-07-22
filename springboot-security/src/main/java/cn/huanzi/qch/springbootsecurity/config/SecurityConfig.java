package cn.huanzi.qch.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CaptchaFilterConfig captchaFilterConfig;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private LoginFailureHandlerConfig loginFailureHandlerConfig;

    @Autowired
    private LoginSuccessHandlerConfig loginSuccessHandlerConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //用户认证处理
                .userDetailsService(userConfig)
                //密码处理
                .passwordEncoder(passwordConfig);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                //定制url访问权限
                .authorizeRequests()
                .antMatchers("/layui/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/getVerifyCodeImage").permitAll()
                //系统相关、非业务接口只能是管理员以上有权限，例如获取系统权限接口、系统用户接口、系统菜单接口、以及用户与权限、菜单关联接口
                .antMatchers("/sysUser/**","/sysAuthority/**","/sysMenu/**","/sysUserAuthority/**","/sysUserMenu/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SA")
                //admin接口测试
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SA")
                .anyRequest().authenticated()
                .and()

                //登录处理
                .addFilterBefore(captchaFilterConfig, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/loginPage")
                .failureHandler(loginFailureHandlerConfig)
                .successHandler(loginSuccessHandlerConfig)
                .permitAll()
                .and()

                //登出处理
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/loginPage")
                .permitAll()
        ;
    }
}
