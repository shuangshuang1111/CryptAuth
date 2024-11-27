package com.shuangshuan.cryptauth.security.config;


import com.shuangshuan.cryptauth.security.entrypoint.JwtAccessDeniedHandler;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {


    private final UserAccountServiceImpl userDetailsService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(UserAccountServiceImpl userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // 配置 JWT 认证过滤器
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService, jwtAuthenticationEntryPoint);
    }

    // 配置 HTTP 安全
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/login/**", "/swagger-ui/**", "/v3/api-docs/**",
                                        "/swagger-resources/**", "/webjars/**").permitAll()
                                .anyRequest().authenticated()).
                addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)  // 设置认证失败时的处理逻辑
                                .accessDeniedHandler(jwtAccessDeniedHandler) // 配置授权失败处理
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 手动启用 CORS 配置
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        ;
        //是的，sessionCreationPolicy(SessionCreationPolicy.STATELESS) 配置表示你的应用程序将不使用 HTTP 会话来存储用户认证信息。在这种情况下，用户的身份验证信息通常是通过
        // JWT（或其他类似的令牌）在每个请求中传递的，而不是通过服务器端会话来维持的。 无需写等处逻辑

        return http.build();
    }

    // CORS 配置方法
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许所有来源（根据需要限制特定域名）
        corsConfiguration.addAllowedMethod("*"); // 允许所有 HTTP 方法（GET, POST, PUT, DELETE, OPTIONS 等）
        corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
        corsConfiguration.setAllowCredentials(true); // 允许携带凭证

        // 创建一个 URL 基础的配置源，允许所有路径的跨域请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
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
