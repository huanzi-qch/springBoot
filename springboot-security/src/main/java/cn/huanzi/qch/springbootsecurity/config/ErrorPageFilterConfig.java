package cn.huanzi.qch.springbootsecurity.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 自定义errorPage拦截器
 */
@Component
public class ErrorPageFilterConfig implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (Arrays.asList(403,404, 500).contains(response.getStatus())) {
            response.sendRedirect("/error/" + response.getStatus());
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
