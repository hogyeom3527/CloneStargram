package EL.WebProject.Clonestagram.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDomain {
    private String userid;
    private String email;
    private String phoneNumber;
    private String name;
    private String userName;
    private String password;
    private String introduce;
    private String profileImage;
}
