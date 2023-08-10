package EL.WebProject.Clonestagram.Service;

import EL.WebProject.Clonestagram.DAO.Repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final MemberRepository repository;

    @Autowired
    public SessionService(MemberRepository repository) {
        this.repository = repository;
    }

    // 세션을 지닌 쿠키의 이름은 myToken으로 저장예정
    public static final String SESSION_COOKIE_NAME = "myToken";


    // 세션 저장을 위한 서버상의 세션 저장공간. 즉, 세션 자체는 DB에 저장치 아니하도록한다.
    private Map<String, Cookie> sessionStore = new ConcurrentHashMap<>();

    //세션 생성
    public Cookie createSession(String userid) {

        //세션 토큰을 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString(); // == 세션 ID 생성
        //쿠키 생성 => 쿠키에 세션 고유 아이디 저장
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        // DB 저장할 값
        sessionStore.put(userid, mySessionCookie); // userid로 세션 저장

        return mySessionCookie;
    }

    //세션 조회 -> 클라이언트에서 올라온 쿠키와 본 세션에 저장된 세션 id 값을 대질한다.
    public boolean isSession(HttpServletRequest request) {

        // 세션스토어에서 세션 id를 지니고있는 쿠키 찾기
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        boolean isLogin = false;
        
        // 세션이 없는경우(== 로그인하지 않은경우) 널 반환
        if (sessionCookie == null) {
            return false;
        }


        HttpSession session = request.getSession(false);
        if(session == null) {
            System.out.println("만약에 이게 뜨면 정말 심각한건데");
            throw new IllegalStateException();
        }
        String userId = (String)session.getAttribute("userId");

        Cookie storeCookie = sessionStore.get(userId);

        // 세션에 실제로 저장된 세션id와 리퀘스트에서 가지고 온 쿠키에서 얻은 value가 동일한 경우
        if(storeCookie.getValue().equals(sessionCookie.getValue())) isLogin = true;
        // 세션은 각 클라이언트별로 관리되므로, 상관하지 말자.


        return isLogin;
    }

    //세션 만료
    public void expire(HttpServletRequest request, String userId) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(userId); // 저장되어있는 쿠키의
            // DB에 위치한 해당 세션 id의
        }
    }

    
    // '''리퀘스트'''에 딸려올라온 쿠키를 확인
    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }

        // request에 딸려오는 모든 쿠키 중, 이름이 cookie name과 동일한 쿠키 찾아서 반환 없으면 null 반환
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}