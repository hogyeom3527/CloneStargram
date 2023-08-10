package EL.WebProject.Clonestagram.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;

    @NotNull
    private String postid;
    private String content;
    private String postimages;

    @CreationTimestamp
    private Timestamp createDate;

}
