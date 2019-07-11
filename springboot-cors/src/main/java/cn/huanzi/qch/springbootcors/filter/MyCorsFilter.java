package cn.huanzi.qch.springbootcors.filter;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@ServletComponentScan
@WebFilter(filterName = "myCorsFilter", //过滤器名称
        urlPatterns = "/cors/corsByMyCorsFilter",//url路径
        initParams = {
                @WebInitParam(name = "allowOrigin", value = "https://www.cnblogs.com"),//允许的请求源，可用,分隔，*表示所有
                @WebInitParam(name = "allowMethods", value = "POST"),//允许的请求方法，可用,分隔，*表示所有
                @WebInitParam(name = "allowCredentials", value = "true"),
                @WebInitParam(name = "allowHeaders", value = "*"),
                @WebInitParam(name = "maxAge", value = "3600"),//60秒 * 60，相当于一个小时
        })
public class MyCorsFilter implements Filter {

    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String maxAge;

    @Override
    public void init(FilterConfig filterConfig) {
        //读取@WebFilter的initParams
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        maxAge = filterConfig.getInitParameter("maxAge");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!StringUtils.isEmpty(allowOrigin)) {
            if (allowOrigin.equals("*")) {
                response.setHeader("Access-Control-Allow-Origin", allowOrigin);
            } else {
                List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
                if (allowOriginList.size() > 0) {
                    //如果来源在允许来源内
                    String currentOrigin = request.getHeader("Origin");
                    if (allowOriginList.contains(currentOrigin)) {
                        response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (!StringUtils.isEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (!StringUtils.isEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (!StringUtils.isEmpty(maxAge)) {
            response.setHeader("Access-Control-Max-Age", maxAge);
        }

        //执行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

