package cn.xuqplus.adminlte.context.filter;

import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@WebFilter(urlPatterns = "/public/**", filterName = "demoFilter")
public class DemoFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> params = new HashMap();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = ((RequestFacade) request).getRequestURL().toString();
        String method = ((RequestFacade) request).getMethod();
        LOGGER.info(String.format("%s %s %s", method, url, params));
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
