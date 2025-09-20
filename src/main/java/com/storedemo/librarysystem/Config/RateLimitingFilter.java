package com.storedemo.librarysystem.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, long[]> requestCounts = new ConcurrentHashMap<>();

    private static final Map<String, Integer> ENDPOINT_LIMITATIONS = Map.of("/api/auth/login", 5
    , "/api/auth/register", 5,
            "/api/books", 10, "/api/users", 15, "/api/loans", 10, "/api/authors", 10);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
        String clientIp = request.getRemoteAddr();
        String path = request.getRequestURI();
        String key = clientIp + ":" + path;
        int requestLimit = ENDPOINT_LIMITATIONS
                .entrySet()
                .stream()
                .filter(
                        e -> path
                                .startsWith(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(10);
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        long[] data = requestCounts.computeIfAbsent(key, k -> new long[]{0, currentTimeInSeconds});
        if(currentTimeInSeconds - data[1] >= 60){
            data[0] = 0;
            data[1] = currentTimeInSeconds;
        }
        if(data[0] >= requestLimit){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests - try again later");
            return;
        }
        data[0]++;
        filterChain.doFilter(request, response);
    }
}
