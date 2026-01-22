package com.example.testasync.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@SuppressWarnings("NullableProblems")
@Component
public class JwtFilters extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) { // nếu ko đúng định dạng thì dừng lại và cho request đi tiếp để security tự xử lý, nếu ko thì sẽ bị kẹt ở đây
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        String username;
        try {
            username = jwtUtils.extractUsername(token);
        } catch (Exception e) {
            System.out.println("JWT không hợp lệ: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        var securityContext = SecurityContextHolder.getContext(); //spring security lưu thông tin trong SecurityContext
        if (username != null && securityContext.getAuthentication() == null) { //nếu authentication == null thì có nghĩa là chưa xác thực
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //chứa các thông tin cơ bản, nếu phúc tạp hơn như cần id, ... thì implement UserDetail để tự định nghĩa lại

            System.err.println(userDetails.getUsername() + "\n"
                    + userDetails.getPassword() + "\n"
                    + userDetails.getAuthorities() + "\n"
                    + userDetails.isAccountNonLocked() + "\n"
                    + userDetails.isAccountNonExpired() + "\n"
                    + userDetails.isEnabled() + "\n"
                    + userDetails.isCredentialsNonExpired()
            );

            if(jwtUtils.validateToken(token, userDetails)){ // validate trước khi đăng nhập
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null, // để bằng null vì token đã xác minh rồi
                        userDetails.getAuthorities()
                );
                securityContext.setAuthentication(authenticationToken); //chính thức đăng nhập cho người dùng này trong request này
            }
        }
        filterChain.doFilter(request, response);
    }

}
