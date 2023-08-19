package EL.WebProject.Clonestagram.Controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileIframeController {

    @GetMapping("/iframe/{iframeName}")
    public ModelAndView iframeReturner(@PathVariable String iframeName) {
        return new ModelAndView("FrontEnd/javascript/" + iframeName)
                .addObject("code", iframeName);
    }



}
