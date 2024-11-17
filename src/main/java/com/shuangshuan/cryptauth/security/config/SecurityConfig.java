package com.shuangshuan.cryptauth.security.config;


import com.shuangshuan.cryptauth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.shuangshuan.cryptauth.security.filter.JwtAuthenticationFilter;
import com.shuangshuan.cryptauth.security.service.UserAccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {


    private final UserAccountServiceImpl userDetailsService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(UserAccountServiceImpl userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    // 配置 JWT 认证过滤器
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService, jwtAuthenticationEntryPoint);
    }

    // 配置 HTTP 安全
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/login/**","/swagger-ui/**", "/v3/api-docs/**",
                                        "/swagger-resources/**", "/webjars/**").permitAll()
                                .anyRequest().authenticated()).
                addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtAuthenticationEntryPoint), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 设置认证失败时的处理逻辑
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    // 配置认证管理器


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // 创建 AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // 配置 userDetailsService 和 passwordEncoder
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        // 返回构建好的 AuthenticationManager
        return authenticationManagerBuilder.build();
    }


    // 配置密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/webjars/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**");
    }

}
