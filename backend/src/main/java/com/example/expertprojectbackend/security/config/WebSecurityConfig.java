package com.example.expertprojectbackend.security.config;

import com.example.expertprojectbackend.security.roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/api/v1/verify/**").permitAll();
                    auth.requestMatchers("/api/v1/password-reset/**").permitAll();
                    auth.requestMatchers("/api/v1/categories/all").permitAll();

                    /* swagger endpoints */
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();

                    /* these endpoints only for testing purposes */
                    auth.requestMatchers("/api/test/home").authenticated();
                    auth.requestMatchers("/api/test/count").authenticated();
                    /* --- */

                    auth.requestMatchers("api/v1/change-password").authenticated();

                    auth.requestMatchers("/api/client/**").hasRole(Role.CLIENT.name());
                    auth.requestMatchers("/**").hasRole(Role.ADMIN.name());
                })
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/swagger-ui.html"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(Customizer.withDefaults())
                .build();
    }
}
