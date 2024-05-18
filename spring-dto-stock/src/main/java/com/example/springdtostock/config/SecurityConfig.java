package com.example.springdtostock.config;

import com.example.springdtostock.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity()
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    //login
    // log pas
    //UDS
    //security context (user)

    //request -> SecurityFilterChain -> response (200, 401, 403)
    //1. AuthenticationFilter
    //2. AuthorizationFilter
    //3. CsrfFilter
    //4. AuthorizationFilter: @PreAuthorize & @PostAuthorize

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //    @Profile("db")
//    @ConditionalOnProperty(value = "profile", havingValue = "db")
    @Bean
    public UserDetailsService userDetailsService(ApplicationUserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }


    //    @Profile("db")
//    @ConditionalOnProperty(value = "profile", havingValue = "db")
    @Bean
    public DaoAuthenticationProvider dbDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register").permitAll()
//                        .requestMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/hello").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }


//    @Bean
//    @ConditionalOnProperty(value = "profile", havingValue = "inMemory")
//    @Profile("inMemory")
//    public UserDetailsService inMemoryDaoAuthenticationProvider() {
//        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
//        UserDetails user = User
//                .withUsername("Linda")
//                .password(passwordEncoder().encode("124"))
//                .roles("ADMIN")
//                .authorities("READ", "WRITE")
//                .build();
//        uds.createUser(user);
//
//        return uds;
//    }
}