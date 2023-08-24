package EL.WebProject.Clonestagram.Service;

import EL.WebProject.Clonestagram.DAO.Repository.PostRepository;
import EL.WebProject.Clonestagram.DTO.postDTO;
import EL.WebProject.Clonestagram.DTO.userContentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {


    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void setPostValue(postDTO newPost) {
        // DB에 줄바꿈까지 정상적으로 기입되므로, 개행문자 관련 걱정은 X
        postRepository.setPost(newPost);
    }

    /** postID를 참조하여 가져옴. */
    public postDTO getPostValueByPostId(String userId, String postId) {
        return postRepository.getPostValueforTEST(userId, postId);
    }

    /** 프로필페이지에서 확인할 게시글의 미리보기 이미지 및 게시글id를 가져옴. */
    public List<userContentsDTO> getSemiPostValue(String userId) {
        return postRepository.getSemiPostList(userId);
    }

}
