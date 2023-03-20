package CG.RoomService.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * Security configuration for the application
 *
 * @Configuration annotation indicates that this class provides
 * bean definitions for the application context.
 * @EnableWebSecurity annotation enables Spring Security configuration for web applications
 * @EnableGlobalAuthentication configure global authentication
 * @RequiredArgsConstructor Lombok-annotation automatically creates a constructor with all final fields as arguments
 */
@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor

public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    /**
     * JWT Authentication filter
     */
    private final JwtAuthenticationFilter jwtAuthFilter;
    /**
     * Authentication provider
     */
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "https://sixmatom.github.io/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    /**
     * Bean definition for security filter chain
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disable csrf protection
                .csrf()
                .disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
//                .requestMatchers(toH2Console())
//                .permitAll()
                .requestMatchers("/test/**")
                .permitAll()
                // permit requests to /demo-controller/admin/** for users with ADMIN authority
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN","SYSTEM_ADMIN")
                .requestMatchers("/demo-controller/admin/**").hasAnyAuthority("ADMIN","SYSTEM_ADMIN")
                // authenticate all other requests
                .anyRequest()
                .authenticated()
                .and()
                 // configure session management
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                // set the authentication provider
                .authenticationProvider(authenticationProvider)
                // add the JWT authentication filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // return the built filter chain
        return http.build();


    }
}

