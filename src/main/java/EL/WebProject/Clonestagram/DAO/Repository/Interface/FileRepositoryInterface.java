package EL.WebProject.Clonestagram.DAO.Repository.Interface;

import java.util.List;

public interface FileRepositoryInterface {
    String saveProFileImgSrc(String src, String userid);

    String getProFileImgSrc(String userid);

    String savePostImgSrc(String postId, List<String> imageNames);

    String getPostImgSrc(String src, String userid);
}
