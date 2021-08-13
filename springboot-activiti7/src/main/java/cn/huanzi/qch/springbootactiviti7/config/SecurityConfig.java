package cn.huanzi.qch.springbootactiviti7.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security框架配置
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                //定制url访问权限
                .authorizeRequests()

                //无限登录即可访问
                .antMatchers("/**").permitAll()

                //需要特定权限
//                .antMatchers("/sysUser/**","/sysAuthority/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SA")

                //其他接口登录才能访问
//                .anyRequest().authenticated()
                .and()
        ;
    }
}
