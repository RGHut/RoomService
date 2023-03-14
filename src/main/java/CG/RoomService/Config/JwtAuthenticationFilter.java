package CG.RoomService.Config;

import CG.RoomService.Models.AuthenticationResponse;
import CG.RoomService.Models.ExceptionResponse;
import CG.RoomService.Service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JwtService instance to perform JWT related operations
    private final JwtService jwtService;
    // UserDetailsService instance to load user by username
    private final UserDetailsService userDetailsService;
    // String variable to hold the value of jwt.header.string from properties file
    @Value("${jwt.header.string}")
    public String HEADER_STRING;
    // String variable to hold the value of jwt.token.prefix from properties file
    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    // Overriding the doFilterInternal method from OncePerRequestFilter
    @Override

    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the auth header from the request
        final String authHeader = request.getHeader(HEADER_STRING);
        // String variable to hold the JWT token
        final String jwt;
        // String variable to hold the user email
        final String userEmail;
        try{
        // Check if the auth header is null or doesn't start with the expected prefix
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            // If true, continue with the filter chain
            filterChain.doFilter(request, response);
            return;
        }
        // Extract the JWT token from the auth header
        jwt = authHeader.substring(7);
        // Extract the username (user email) from the JWT token
        userEmail = jwtService.extractUsername(jwt);
        // Check if the user email is not null and the current authentication is null
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // If true, load the user details by email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Check if the JWT token is valid for the user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // If true, create an instance of UsernamePasswordAuthenticationToken with user details, null and user authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // set the WebAuthenticationDetailsSource as the details of authToken
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // set the authToken as the current authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // continue with the filter chain
        filterChain.doFilter(request, response);
    }catch(ExpiredJwtException ex){
            ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());

            // Set the response status code to 401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            // Set the response content type to application/json
            response.setContentType("application/json");

            // Write the authenticationResponse object as a JSON string to the response body
            response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));

            // End the filter chain
            return;

        }
}
}
