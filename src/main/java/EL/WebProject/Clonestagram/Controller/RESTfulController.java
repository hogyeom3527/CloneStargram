package EL.WebProject.Clonestagram.Controller;

import EL.WebProject.Clonestagram.DTO.postDTO;
import EL.WebProject.Clonestagram.DTO.userContentsDTO;
import EL.WebProject.Clonestagram.DTO.userStatusDTO;
import EL.WebProject.Clonestagram.Service.FileService;
import EL.WebProject.Clonestagram.Service.MemberService;
import EL.WebProject.Clonestagram.Service.PostService;
import EL.WebProject.Clonestagram.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// RestController는 ajax 통신에서 type: "PUT", "DELETE", "GET", "POST"와 같이 진행
@RestController
public class RESTfulController {
    private final MemberService memberService;
    private final FileService fileService;
    private final SessionService sessionService;
    private final PostService postService;

    @Autowired
    public RESTfulController(MemberService memberService, FileService fileService, SessionService sessionService, PostService postService) {
        this.memberService = memberService;
        this.fileService = fileService;
        this.sessionService = sessionService;
        this.postService = postService;
    }
    /*
    // 로그인한 사용자 userId 가져오기
    @GetMapping("/Member/getUserId") 
    public String getUserId(HttpServletRequest request) {
        if(sessionService.isSession(request)) {
            HttpSession session = request.getSession(false);
            if(session == null) {
                return null;
            }
            return (String)session.getAttribute("userId");
        }
        else {
            return null;
        }
    }
    */




    @GetMapping("/load/profileImg")
    public String loadProfileImage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session.isNew()) {
            return null;
        }

        return fileService.getProfileImage((String)session.getAttribute("userId"));
    }


    // 파일 업로드 및 로드까지 수행하도록 하였음
    @PostMapping("/Member/upload/profileImg")
    public ResponseEntity<String> uploadProfileImage(/*@PathVariable int userId*/HttpServletRequest request, @RequestParam MultipartFile[] file){
        HttpSession session = request.getSession(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");

        if(sessionService.isSession(request)) {
            String userId = (String)session.getAttribute("userId");
            fileService.setProfileImage(file, userId); // 특정 사용자의 프로필 이미지의 DB 정보 update 및 파일 upload.
            // 파일 업로드간에 처음으로 작성된 파일에 대해 '덮어씌우게끔' 작성해보기
        }
        else {
            System.out.println("파일 업로드 실패.");
            throw new IllegalStateException();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileService.getProfileImage((String)session.getAttribute("userId")));
    }

    // 포스팅 시 좋아요, 댓글 등의 개수는 0개로 고정되어있음이 당연하다.
    // 프론트엔드 단에서 받아오는 데이터는 이미지파일의 저장 및 게시글에 저장될 이미지 파일'들'과
    // 게시글의 내용까지만 받아오면 될 듯함.
    // 게시글 '포스팅'

    // 게시글 내용 및 이미지 까지 동시에 받아와야 한다!
    @PostMapping("/Member/writingPostTest")
    public void setPostTest (HttpServletRequest request, @RequestParam String postValue) {
        HttpSession session = request.getSession(false);

        if(sessionService.isSession(request)) {
            String userId = (String)session.getAttribute("userId");

            // userId 기준하여 사용자 정보 가져오지 않아도 됨.
            // userId는 게시글 테이블의 외래키로 저장되어 사용자 테이블과 연결될 예정.
            // 게시글 테이블은 게시글 번호, 게시물 내용, 좋아요, 댓글리스트 번호, userId를 저장함
            postDTO newPost = new postDTO();

            newPost.setPostValue(postValue);
            newPost.setPostUserId(userId);
            newPost.setPostLike(0);
            newPost.setCommentListId("0");

            Timestamp now = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now())); // 현재 연, 월, 일, 시, 분, 초 저장

            newPost.setDate(now);

            postService.setPostValue(newPost);
        }
        else {
            System.out.println("SomeThing Wrong in setPostTest()");
            throw new IllegalStateException();
        }
    }

    // 본 함수는 postId를 직접 기입하여 해당 id의 내용을 가져오는 것을 기반으로 함.
    @GetMapping("/Member/getPostValueTEST/{postId}")
    public postDTO getPostTEST(HttpServletRequest request, @PathVariable String postId) {
        if(sessionService.isSession(request)) {
            HttpSession session = request.getSession(false);
            return postService.getPostValueByUserId((String)session.getAttribute("userId"), postId);
        }
        else {
            System.out.println("본인 게시글만 열람 가능.");
            throw new IllegalStateException();
        }
    }


    @PostMapping("/Member/postTesting")
    public void postTest(HttpServletRequest request, @RequestParam("images") MultipartFile[] files, @RequestParam("postValue") String postValue) {
        HttpSession session = request.getSession(false);

        if(sessionService.isSession(request)) {
            String userId = (String)session.getAttribute("userId");

            // userId 기준하여 사용자 정보 가져오지 않아도 됨.
            // userId는 게시글 테이블의 외래키로 저장되어 사용자 테이블과 연결될 예정.
            // 게시글 테이블은 게시글 번호, 게시물 내용, 좋아요, 댓글리스트 번호, userId를 저장함
            postDTO newPost = new postDTO();
            Timestamp now = Timestamp.valueOf(LocalDate.now().atTime(LocalTime.now())); // 현재 연, 월, 일, 시, 분, 초 저장

            newPost.setPostValue(postValue);
            newPost.setPostUserId(userId);
            newPost.setPostLike(0);
            newPost.setCommentListId("0");
            newPost.setDate(now);
            postService.setPostValue(newPost); // 게시글 정보를 작성한다.

            // 가져온 파일을 각각 저장하고, 그 이름을 DB에 저장해야한다.
            // 이때, DB에 파일명을 저장하는 것은 '''원본이 되는 게시글 정보가 작성된 이후'''에 되어야 하므로...

            fileService.setPostImages(newPost.getPostId(), files); // 외부경로에 이미지 저장 및 DB에 이미지 명을 저장한다.
        }
        else {
            System.out.println("SomeThing Wrong in setPostTest()");
            throw new IllegalStateException();
        }
    }


    // 사용자가 작성한 게시글 개수, 팔로우한 계정 수, 팔로우 받은 계정 수 제공
    @GetMapping("/Member/Profile/getUserStatus")
    public ResponseEntity<userStatusDTO> getUserStatus() {

        return null;
    }


    @GetMapping("/Member/Profile/getUserContents")
    public ResponseEntity<List<userContentsDTO>> getUserContents(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(sessionService.isSession(request)) {
            String userId = (String)session.getAttribute("userId");
            List<userContentsDTO> DTO = postService.getSemiPostValue(userId);

            if(DTO.size() != 0)
                return ResponseEntity.ok().body(postService.getSemiPostValue(userId));
            else return ResponseEntity.ok().body(null); // 내용 없으면 null 제공하여 에러 방지

        }
        else {
            throw new IllegalStateException("프로필 페이지 로딩 중 오류");
        }
    }







}
