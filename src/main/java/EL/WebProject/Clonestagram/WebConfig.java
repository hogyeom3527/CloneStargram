package EL.WebProject.Clonestagram;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 외부경로 사용 허용을 위한 Tomcat 설정 변경용도의 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Add a resource handler for external images (change "/external-images/**" to your desired URL pattern)
//        registry.addResourceHandler("/files/**")
//                .addResourceLocations("file:Z:/Clonstagram_FileBase"); // Replace with your actual external path
//    }
}
