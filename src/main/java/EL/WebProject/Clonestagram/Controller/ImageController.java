package EL.WebProject.Clonestagram.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
public class ImageController {
    
    /** 페이지의 정적 리소스 이미지 src를 제공하기 위한 함수 */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new ClassPathResource("static/img/" + filename);
        // ClassPathResource는 classpath의 사용과 같은 효과를 지닌다!
        // 프로젝트 내부의 resources/ 까지의 경로를 기본으로 설정해둠.
    }
    
    
    /** 사용자의 프로필 이미지 src를 제공하기 위한 함수 */
    @ResponseBody
    @GetMapping("/profile/{filename}")
    public Resource showProfileImage(@PathVariable String filename) throws MalformedURLException {
        System.out.println("fileName : " + filename);
        return new FileSystemResource("C:/Clonestagram_FileBase/user_Profile/" + filename);
    }
    
    /** 게시글의 이미지 src를 제공하기위한 함수 */
    @ResponseBody
    @GetMapping("/postImages/{filename}")
    public Resource showPostImage(@PathVariable String filename) throws MalformedURLException {
        return new FileSystemResource("C:/Clonestagram_FileBase/post_Images/" + filename);
    }

}
