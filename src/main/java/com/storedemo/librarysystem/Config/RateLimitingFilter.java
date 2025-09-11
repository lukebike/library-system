package com.storedemo.librarysystem.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS_PER_MINUTE = 5;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
        String clientIp = request.getRemoteAddr();

        requestCounts.putIfAbsent(clientIp, 0);
        int requestCount = requestCounts.get(clientIp);
        if(requestCount >= MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests - try again later");
            return;
        }
        requestCounts.put(clientIp, requestCount + 1);
        filterChain.doFilter(request, response);
    }
}
