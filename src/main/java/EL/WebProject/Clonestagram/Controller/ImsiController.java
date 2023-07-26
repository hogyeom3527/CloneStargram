package EL.WebProject.Clonestagram.Controller;

import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Domain.JoinMemberForm;
import EL.WebProject.Clonestagram.Service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class ImsiController {

    private final MemberService service;

    private Member temp = new Member();

    @Autowired
    public ImsiController(MemberService service) {
        this.service = service;
    }

    // home 페이지 진입
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    // 회원가입 페이지 진입
    @GetMapping("/Member/memberJoin")
    public String memberJoin(){
        return "Member/memberJoin";
    }

    // 로그인 페이지 진입
    @GetMapping("/Member/memberLogin")
    public String memberLogin() {return "Member/memberLogin";}

    // 로그인 확인용 임시 페이지 진입
    @GetMapping("/loginResult")
    public String loginResult(Model model) {
        model.addAttribute("user", temp);
        return "loginResult";
    }

    // 회원 리스트 확인 페이지 진입
    @GetMapping("/Member/MemberList")
    public String memberList(Model model){
        ArrayList<Member> memberList = service.lookUpMember();
        // Mapper 활용하여 ArrayList<joinMemberForm>에 해당하는 콜렉션을 ArrayList<Member> 형식으로 Mapping하였음

        model.addAttribute("List", memberList);

        return "Member/MemberList";
    }


    
    
    // 회원가입 정보 전송
    @PostMapping("/Member/memberJoin")
    public String memberSignUp(Member form) {
        // 버튼을 눌렀을 때, 기입한 정보를 Domain member로 만들어 Service의 회원가입 로직으로 보냄.
        // Service는 Repository의 회원가입 로직으로 해당 member Domain 보냄...

        service.join(form);

        return "redirect:/";
        // PostMapping과 별개로 사용자를 home으로 보냄.
    }

    // 로그인 정보 전송
    @PostMapping("/Member/memberLogin")
    public String memberLogin(LoginInfo form){
        temp = service.getUserInfo(form);

        return "redirect:/loginResult";
    }



        // 추후 작업용
        /*
    // 회원가입 - 프론트엔드 협업간에 사용 예정
//    @PostMapping("/accounts/emailsignup")
//    public String join() {
//        // 받아온 값을 joinMemberForm 형식의 객체에 저장
//        JoinMemberForm member = new JoinMemberForm();
//
//        // service.join(member);
//        // 저장한 객체를 매개변수로 넘겨줌
//
//        return "redirect:/";
//    }

         */

}
