package authentication.com.Authentications.Components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import authentication.com.Authentications.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTUtils jwtUtils; 

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException {
            // 1. Read the Authorization header
            String authHeader = request.getHeader("Authorization");
            
            // 2. Check if it starts with "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")){
                // 3. Extract the token (cut off "Bearer ")
                filterChain.doFilter(request, response);
                return; 
            }

            String token = authHeader.substring(7); // remove "Bearer "
            String username = jwtUtils.extractUsername(token);


            // 4. Ask JWTUtils to validate it
            // 5. If valid → create Authentication object and set it in SecurityContext
            // 6. Then call filterChain.doFilter(request, response)
            
    }
}
