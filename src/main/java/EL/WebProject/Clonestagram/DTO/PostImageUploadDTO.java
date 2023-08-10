package EL.WebProject.Clonestagram.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostImageUploadDTO {
    private MultipartFile file;
    private String content;

    public PostImage toEntity(String postid, String content, String postimages){
        return PostImage.builder()
                .postid(postid)
                .content(content)
                .postimages(postimages)
                .build();
    }

}
