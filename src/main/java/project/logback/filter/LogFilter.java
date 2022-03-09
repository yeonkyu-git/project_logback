package project.logback.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        try {
            log.info("Filter Starting ...");

            if (session == null || session.getAttribute("loginMemberName") == null) {
                log.info("not login User");
                MDC.put("loginMemberName", "No User");
            } else {
                log.info("login User");
                MDC.put("loginMemberName", session.getAttribute("loginMemberName").toString());
            }
            filter.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("Filter End...");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
