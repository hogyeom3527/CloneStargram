package EL.WebProject.Clonestagram.Controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/** profile page에서의 iframe 제공을 위한 컨트롤러  */
@Controller
public class ProfileIframeController {

    /** imageController에서 같이 제공하기에는 애매함. */
    @GetMapping("/iframe/{iframeName}")
    public ModelAndView iframeReturner(@PathVariable String iframeName) {
        return new ModelAndView("FrontEnd/javascript/" + iframeName)
                .addObject("code", iframeName);
    }
}
