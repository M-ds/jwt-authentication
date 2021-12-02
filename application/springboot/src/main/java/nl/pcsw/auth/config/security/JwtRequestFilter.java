package nl.pcsw.auth.config.security;

import nl.pcsw.auth.security.service.MyPersonDetailsService;
import nl.pcsw.auth.text.TextUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProperties jwtTokenProperties;
    private final MyPersonDetailsService myPersonDetailsService;

    public JwtRequestFilter(JwtTokenProperties jwtTokenProperties, MyPersonDetailsService myPersonDetailsService) {
        this.jwtTokenProperties = jwtTokenProperties;
        this.myPersonDetailsService = myPersonDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        //TODO: Check if the token is almost expired. If so generate a new one!
        String jwtToken = parseJwtToken(request);
        if (TextUtils.isNullOrBlank(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenUsername = jwtTokenProperties.getUsernameFromToken(jwtToken);
        if (!TextUtils.isNullOrBlank(tokenUsername) || SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = myPersonDetailsService.loadUserByUsername(tokenUsername);
            if (jwtTokenProperties.validateToken(jwtToken, userDetails.getUsername())) {
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwtToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (TextUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
