package EL.WebProject.Clonestagram.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class postDTO {
    private String postId;
    private String postValue;
    private String postUserId;
    private String postLike;
    private String commentListId;
}
