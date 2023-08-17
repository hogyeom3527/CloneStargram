package EL.WebProject.Clonestagram.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

// 프로필 페이지 '최초' 확인 시 사용할 DTO?
@Getter
@Setter
public class ProfileDTO {
    private String userId;
    private String userName;
    private File userImg;
    private int userPost;
    private int userFollower;
    private int userFollow;

}
