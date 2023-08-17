package EL.WebProject.Clonestagram.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// 해당 DTO는 기본키 생성이 DB에 의존하지 않아야 함.
// 이미지를 별도의 테이블로 관리하는 이상, postId가 자바 상에서 결정되어야 참조 가능.
@Entity
@Getter
@Setter
public class postDTO {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String postId;

    // 야매.
    // DTO의 생성과 동시에 기본키를 갖게끔 하는 것을 기준으로 하여 아래와 같이 작성하였음.
    // GeneratedValue와 같은 형식은 실질적으로 Repository에서 저장된 이후에 값을 가진다는 특징이 있기 때문.
    public postDTO() {
        this.postId = UUID.randomUUID().toString();
    }

    private String postValue;
    private String postUserId;
    private int postLike;
    private String commentListId;

    @ElementCollection
    private List<String> postImageName;

    private Timestamp date;
}
