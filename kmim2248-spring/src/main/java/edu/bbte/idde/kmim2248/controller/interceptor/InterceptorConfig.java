package edu.bbte.idde.kmim2248.controller.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoggerInterceptor loggerInterceptor;

    @Autowired
    private DeleteAuthorizationInterceptor deleteAuthorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor).addPathPatterns("/**");

        registry.addInterceptor(deleteAuthorizationInterceptor)
                .addPathPatterns("/api/events/**");
    }
}

