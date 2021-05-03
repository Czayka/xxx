package com.xxx.getway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        //  1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //  1.1 允许的域
        config.addAllowedOrigin("http://manage.xxx.com");
        config.addAllowedOrigin("http://www.xxx.com");
        config.addAllowedOrigin("http://image.xxx.com");
        config.addAllowedOrigin("http://song.xxx.com");
        //  1.2 是否发生COOKIE信息
        config.setAllowCredentials(true);
        //  1.3 允许的请求方式
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.HEAD);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.PATCH);
        //  1.4 允许的头信息
        config.addAllowedHeader("*");
        //  1.5 有效时长
        config.setMaxAge(3600L);

        //  2.添加映射路径    拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",config);

        //  3.返回新的CorFilter
        return new CorsFilter(configurationSource);
    }
}
