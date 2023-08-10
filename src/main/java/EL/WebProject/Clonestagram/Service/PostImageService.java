package EL.WebProject.Clonestagram.Service;

import EL.WebProject.Clonestagram.DAO.Repository.PostImageRepository;
import EL.WebProject.Clonestagram.DTO.PostImage;
import EL.WebProject.Clonestagram.DTO.PostImageUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PostImageService {

    @Autowired
    PostImageRepository postImageRepository;

    @Transactional
    public boolean postImage(PostImageUploadDTO postImageUploadDTO, String userid){

        // @Value("{file.upload.path}")로 Appliacion.properties에 선언된
        // /src/main/resources/static/ 경로 String 가져올 수 있음
        // String 앞에 "classpath:" 붙이는 것으로, 해당 String에서의 경로가
        // 본 프로젝트의 resources 디렉토리를 루트 디렉토리로 삼게끔 설정 가능

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String postUploadForder = Paths.get("C:", "", "").toString(); //파일경로 아직 미설정
        String profileUploadForder = Paths.get("", today).toString(); //게시글 이미지 설정하는 곳
        String postUploadPath = Paths.get(postUploadForder, profileUploadForder).toString();
        
        // 이미지 저장을 위한 파일 객체 생성
        File dir = new File(postUploadPath);

        // 파일 객체가 가리키는 경로들이 존재하지 않는경우, 해당 경로의 파일들을 직접 생성하는 조건문.
        if (dir.exists() == false){
            dir.mkdirs();
        }
        
        // 프로필 이미지 이름 작성
        UUID uuid = UUID.randomUUID();
        String profileImageName = uuid+"_"+postImageUploadDTO.getFile().getOriginalFilename();

        try {
            File saveFile = new File(postUploadPath, profileImageName);
            postImageUploadDTO.getFile().transferTo(saveFile);
        }catch (Exception e){
            return false;
        }

        String postimages=(profileUploadForder+"\\"+profileImageName);

        PostImage postImage = postImageUploadDTO.toEntity(userid, postImageUploadDTO.getContent(), postimages);

        postImageRepository.save(postImage);

        return true;
    }

}
