package kahlua.KahluaProject.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://kahluaband.com/", "http://kahluaband.com/",
                        "https://www.kahluaband.com/", "http://www.kahluaband.com/", "https://kahluabandver20-caminobelllos-projects.vercel.app",
                        "https://api.kahluaband.com/", "http://api.kahluaband.com/");
    }
}