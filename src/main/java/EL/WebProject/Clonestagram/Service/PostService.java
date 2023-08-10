package EL.WebProject.Clonestagram.Service;

import EL.WebProject.Clonestagram.DAO.Repository.PostRepository;
import EL.WebProject.Clonestagram.DTO.postDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 테스트 용도의 함수. 이를 확장시켜 게시글 전체 정보를 가져오게끔 수정 예정.
    public postDTO getPostValueByUserId(String userId, String postId) {
        return postRepository.getPostValueforTEST(userId, postId);
    }

}
