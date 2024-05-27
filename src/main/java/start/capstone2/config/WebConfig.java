package start.capstone2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import start.capstone2.config.Interceptor.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**", "/admin/**", "/user/**", "/portfolio/**"); // 여러 경로에 대해 인터셉터 적용
                // .addPathPatterns("/**");  // 모든 경로에 대해 인터셉터 적용
    }
}

