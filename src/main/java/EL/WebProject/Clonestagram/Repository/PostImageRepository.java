package EL.WebProject.Clonestagram.Repository;

import EL.WebProject.Clonestagram.DTO.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
}
