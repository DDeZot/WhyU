package com.WhyU.filters;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//@Component
public class RateLimitingFilter implements Filter {

    private final ConcurrentHashMap<String, Long> userLastRequestTime = new ConcurrentHashMap<>();
    private final long REQUEST_LIMIT_TIME = 3000;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = httpRequest.getRemoteAddr();

        long currentTime = System.currentTimeMillis();
        Long lastRequestTime = userLastRequestTime.get(userId);

        if (lastRequestTime != null && (currentTime - lastRequestTime) < REQUEST_LIMIT_TIME)
            return;

        userLastRequestTime.put(userId, currentTime);
        chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
