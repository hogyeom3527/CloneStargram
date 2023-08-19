package EL.WebProject.Clonestagram.Controller;

import EL.WebProject.Clonestagram.DTO.JoinInfo;
import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Service.FileService;
import EL.WebProject.Clonestagram.Service.MemberService;
import EL.WebProject.Clonestagram.Service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ImsiController {
    private Member temp;

    private final MemberService memberService;
    private final FileService fileService;

    private final SessionService sessionService;

    @Autowired
    public ImsiController(MemberService memberService, FileService fileService, SessionService sessionService) {
        this.memberService = memberService;
        this.fileService = fileService;
        this.sessionService = sessionService;
    }

    // home 페이지 진입
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    // 회원가입 페이지 진입
    @GetMapping("/Member/memberJoin")
    public String memberJoin(){
        return "FrontEnd/javascript/signup";
    }

    // 로그인 페이지 진입
    @GetMapping("/Member/memberLogin")
    public String memberLogin() { return "FrontEnd/javascript/login";}

    // 회원 리스트 확인 페이지 진입
    @GetMapping("/Member/memberList")
    public String memberList(Model model){
        ArrayList<Member> memberList = memberService.lookUpMember();
        // Mapper 활용하여 ArrayList<joinMemberForm>에 해당하는 콜렉션을 ArrayList<Member> 형식으로 Mapping하였음

        model.addAttribute("List", memberList);

        return "Member/memberList";
    }

    // 프로필 페이지 진입
    @GetMapping("Member/memberProfile")
    public String memberProfile(HttpServletRequest request) {

        HttpSession session = request.getSession(); // 세션이 없을경우 새 세션 생성
        if (session.isNew()) { //  생성된 세션이 새 세션인 경우
            session.invalidate(); // 세션 삭제
            return "redirect:/"; // 로그인되지 않은채 로그인관련 페이지 진입 못하게끔 리다이렉트
        }

        return "FrontEnd/javascript/profile";
    }

    @GetMapping("/Member/memberPosting")
    public String memberPosting(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 해당 if문 방식으로 session 검증하는 것이 옳음.
        // session.isNew와 같이 진행 시, 세션의 존재, 미존재 여부만으로 로그인 관련 검증하므로 sessionId의 의미가 없음.
        if(!sessionService.isSession(request) || session.isNew()) {
            return "redirect:/";
        }

        return"Member/memberPosting";
    }

    @GetMapping("/Member/memberCheckPosting")
    public String memberPostingCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 해당 if문 방식으로 session 검증하는 것이 옳음.
        // session.isNew와 같이 진행 시, 세션의 존재, 미존재 여부만으로 로그인 관련 검증하므로 sessionId의 의미가 없음.
        if(!sessionService.isSession(request) || session.isNew()) {
            return "redirect:/";
        }

        return"Member/memberCheckPosting";
    }
    
    // 회원가입 정보 전송
    @PostMapping("/Member/memberJoin")
    public String memberSignUp(JoinInfo form) {
        // 버튼을 눌렀을 때, 기입한 정보를 Domain member로 만들어 Service의 회원가입 로직으로 보냄.
        // Service는 Repository의 회원가입 로직으로 해당 member Domain 보냄...

        memberService.join(form);

        return "redirect:/";
        // PostMapping과 별개로 사용자를 home으로 보냄.
    }


    //'세션'으로 로그인 되는지 확인 -> loginResult를 확인하기위한 '''임시''' 함수.
    @GetMapping("/Member/sessionLogin")
    public String home(HttpServletRequest request, Model model) {


        HttpSession session = request.getSession(false);

        if(session == null) { // 세션이 null 삽입되는 것은 세션이 없는 경우 새 세션 안만들고 null 반환하는경우
            return "login"; // == 로그인 실패
        }


        // 세션이 지닌 Attribute 중 해당 String의 값 반환
        // 즉, 세션에 이메일을 name으로하는 value값인 세션 id를 가져오는것???
        String userId = (String)session.getAttribute("userId"); // null 부분은 db에 저장되어 있는 유저의 email 가져오기

        
        // 멤버를 바로 땡겨올게 아니라, 세션에 있는 세션 ID값 지닌 데이터베이스 참조하여서 해당 멤버 가져오고
        // 그 멤버 정보를 다시 땡겨오기
        Optional<Member> findMemberOptional = memberService.findMemberByUserId(userId);
        Member member = findMemberOptional.orElse(null);

        if(member == null) {
            return "login";
        }
        
        // 로그인으로 찾아낸 멤버의 값 들여보냄.
        model.addAttribute("user", member);
        return "/Member/loginResult";
    }

    @PostMapping("/Member/memberLogin") // 로그인 및 세션 생성
    public String login(@ModelAttribute LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response) {
        // 로그인 멤버 객체 생성
        // 로그인멤버 널이면 로그인 실패로 홈으로 리다이렉트
        // 성공시 조건문 무시 후 쿠키 구워옴 + 구우면서 해당 세션 id를 세션서비스에도 저장!
        // 구워온 쿠키를 addCookie로 프론트 엔드로 보내고, 세션 결과와 대질한다.



        // 받아온 로그인 폼으로 아이디 및 비밀번호 대질하여 멤버 객체 생성
        Member loginMember = memberService.getUserInfo(loginInfo).orElse(null);

        // 로그인 멤버 null 인경우 == 로그인 실패한 경우
        if(Objects.isNull(loginMember)) {
            System.out.println("로그인 실패");
            return "redirect:/"; // home 페이지로 리다이렉트
        }

        // createSession 하면서, 세션 서비스 내에 세션이 담긴 쿠키를 Store.
        // 동시에 쿠키를 리턴받아서 클라이언트의 쿠키로 addCookie 해줌.
        Cookie loginCookie = sessionService.createSession(loginMember.getUserid());
        response.addCookie(loginCookie); // 프론트엔드로 세션 ID 담은 쿠키 '전송'

        // 클라이언트에 비교를 위한 세션을 생성!!! => 쿠키와 세션은 다르다!!!
        HttpSession session = request.getSession(); // 세션 생성
        session.setAttribute("userId", loginMember.getUserid()); // null 부분은 db에 저장되어 있는 유저의 email 가져오기
        // 생성된 세션 내에 유저 기본키를 name tag로 삼는 유저 기본키 값 저장
        
        // 세션 정상적으로 생성됬는지 확인
        System.out.println("Postlogin(): " + session.getAttribute("userId"));

        return "redirect:/Member/sessionLogin";
    }

    //세션 로그아웃
    @PostMapping("/Member/memberLogout")
    public String logout(HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        if(!sessionService.isSession(request)) {
            return "redirect:/";
        }

        // 삭제를 위한 userId 가져온 이후
        // 클라이언트에 저장된 세션 삭제
        String userId = (String)session.getAttribute("userId");
        session.invalidate();

        // 쿠키는 냅둬도 된다.
        // 다만, store에 저장된 값도 날려야함.
        sessionService.expire(request, userId);

        return "redirect:/";
    }



}
