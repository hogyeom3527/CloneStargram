package EL.WebProject.Clonestagram.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
public class ImageController {
    
    // 정적 리소스
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new ClassPathResource("static/img/" + filename);
        // ClassPathResource는 classpath의 사용과 같은 효과를 지닌다!
    }
    
    
    // 애초에 return을 해주는 김에 여기서 파일 이름을 찾아오는게 편하지 않으려나..
    @ResponseBody
    @GetMapping("/profile/{filename}")
    public Resource showProfileImage(@PathVariable String filename) throws MalformedURLException {
        System.out.println("fileName : " + filename);
        return new FileSystemResource("C:/Clonestagram_FileBase/user_Profile/" + filename);
    }

    @ResponseBody
    @GetMapping("/postImages/{filename}")
    public Resource showPostImage(@PathVariable String filename) throws MalformedURLException {
        return new FileSystemResource("C:/Clonestagram_FileBase/post_Images/" + filename);
    }

}
