package EL.WebProject.Clonestagram.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Member {
    // DTO for Member
    // 가변객체
    // 가져온 회원정보를 수정할 일이 있다!
    // ex. 회원가입 이후, 회원 정보를 수정하는 경우
    // 페이지에 불러온 데이터를 수정하여 다시 전달하는 등...?
    private String userid;
    private String email;
    private String phoneNumber;
    private String name;
    private String userName;
    private String password;
    private String introduce;
    private String profileImage;
    private List<PostImage> images;
}

