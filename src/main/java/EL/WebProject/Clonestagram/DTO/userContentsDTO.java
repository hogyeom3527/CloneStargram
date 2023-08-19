package EL.WebProject.Clonestagram.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userContentsDTO {
    private String postId;
    private String imgSrc;
    private int commentsNum;
}
