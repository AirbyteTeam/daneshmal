package com.airbyte.daneshmal.security.config;

import com.airbyte.daneshmal.security.auth.ApplicationUserService;
import com.airbyte.daneshmal.security.jwt.JwtAuthenticationFilter;
import com.airbyte.daneshmal.security.jwt.JwtTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    public SecurityConfig(PasswordEncoder passwordEncoder,
                          ApplicationUserService applicationUserService) {

        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("GET", "/api/v1/company/{category}").permitAll()
                .antMatchers("GET", "/api/v1/company/categories").permitAll()
                .antMatchers("GET", "/api/v1/company/{category}/findAll").permitAll()
                .antMatchers("GET", "/api/v1/company/findAll").permitAll()
                .antMatchers("POST", "/api/v1/comment/save").permitAll()
                .anyRequest()
                .authenticated();

        http
                .cors(request -> {
                    CorsConfigurationSource configurationSource = x -> {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                        corsConfiguration.setAllowCredentials(true);
                        corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization", "Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods", "Access-Control-Allow-Method"));
                        corsConfiguration.setExposedHeaders(List.of("Content-Type", "Authorization", "Access-Control-Allow-Methods", "Access-Control-Allow-Headers", "Access-Control-Allow-Method"));
                        return corsConfiguration;
                    };
                    request.configurationSource(configurationSource);
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}

