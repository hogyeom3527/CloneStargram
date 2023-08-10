package EL.WebProject.Clonestagram.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
public class ImageController {

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new ClassPathResource("static/img/" + filename);
        // ClassPathResource는 classpath의 사용과 같은 효과를 지닌다?
    }

    @ResponseBody
    @GetMapping("/profile/{filename}")
    public Resource showProfileImage(@PathVariable String filename) throws MalformedURLException {
        System.out.println("fileName : " + filename);
        return new ClassPathResource("static/userProfile/" + filename);
    }

}
