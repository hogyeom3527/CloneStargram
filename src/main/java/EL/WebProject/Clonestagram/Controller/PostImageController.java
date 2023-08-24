package EL.WebProject.Clonestagram.Controller;

import EL.WebProject.Clonestagram.DTO.PostImageUploadDTO;
import EL.WebProject.Clonestagram.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostImageController {

    private SessionService sessionService;

    @Autowired
    public PostImageController(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @PostMapping("/qwer")
    public ResponseEntity<String> PostImageUpload(PostImageUploadDTO postImageUploadDTO, HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(sessionService.isSession(request)) {
            return  ResponseEntity.ok().body("완료");
        }
        else {
            return ResponseEntity.badRequest().body("실패");
        }
    }
}
