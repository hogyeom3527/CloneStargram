package EL.WebProject.Clonestagram.Service;

import EL.WebProject.Clonestagram.DAO.Repository.FileRepository;
import EL.WebProject.Clonestagram.DAO.Repository.MemberRepository;
import EL.WebProject.Clonestagram.DTO.ProfileImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// 파일 저장을 위한 Service 단계 클래스
@Service
public class FileService {
    // 사용자가 저장하는 파일명은, 다른 사용자가 저장하는 파일명과 중복 가능
    // 이에 의거하여 사용자가 지정한 파일명 대신, 서버에서 별도로 새로운 파일명을 작성
    // 새롭게 작성한 파일명을 활용하여 파일 저장 및 DB에 파일명 저장
    // 이미지 파일이 위치한 PATH는 아예 상수로 따로 저장. -> PATH + storeFilename의 형식으로 이미지 파일을 사용한다.

    // 인스타그램에 올리는 파일에는 이미지, gif, 동영상 등이 있음.
    // 여러 파일 형식에 각각 사용하기 편하게 함수를 분리할 필요가 있다는 것.

    private final FileRepository fileRepository;
    private final MemberRepository memberRepository;

    // 회원 프로필 이미지 파일 저장 경로 저장
    // /src/main/resources/static/
    @Value("${file.upload.path}")
    private String PATH;

    @Autowired
    public FileService(FileRepository fileRepository, MemberRepository memberRepository) {
        this.fileRepository = fileRepository;
        this.memberRepository = memberRepository;
    }


    // 이미지 저장 및 이미지 경로를 DB에 저장하기위한 메소드
    // 안되면 String 다시 int로
    public void setProfileImage(MultipartFile file, String userId) {
        String userImageSrc = memberRepository.whatMemberImage(userId);
        System.out.println("사용자 현재 이미지 src : " + userImageSrc);
        if (userImageSrc == null) { // 저장된 이미지가 없는 경우
            System.out.println("이미지 없어서 새로 저장");

            String storeFilename = binFileSave(file, ""); // 파일 외부경로 위치에 저장
            fileRepository.saveFileSrc(storeFilename, userId); // 외부경로 위치 및 파일명을 DB에 저장
        }
        else { // 저장된 이미지가 있는경우, 해당 이미지 명에 그대로 저장
            System.out.println("이미지 존재하므로 그대로 덮어씌움");

            String alreadyStoredFilename = fileRepository.saveFileSrc(userImageSrc, userId);
            binFileSave(file, alreadyStoredFilename); // 이미 있는 거에 덮어씌우기
        }

    }

    public String getProfileImage(String userId) {
        return fileRepository.getFileSrc(userId);
    }
    
    // 확장자명 분리 메소드
    private String extractExt(String originalFilename) {
        int idx = originalFilename.lastIndexOf("."); // 기본 파일 이름에서 제일 마지막에 위치한 .의 인덱스 값 저장
        String ext = originalFilename.substring(idx); // 인덱스값 위치부터 끝까지 내용 리턴 == 확장자 명
        return ext; // 확장자 반환
    }

    // 저장 용도의 별도 이름 생성용 메소드
    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String storeFilename = uuid + ext; // 새롭게 생성한 명칭 + 확장자명으로 저장용도의 이름 생성

        return storeFilename;
    }


    // 바이너리 파일(이미지, 동영상 등)의 저장을 위한 함수로, 매개변수로 빈 문자열 입력받으면 새로운 파일 이름 제작.
    private String binFileSave(MultipartFile file, String savedFileName) {
        // 파일 저장 위한 함수.
        // DB가 아니라, 프로젝트 내부 경로에 저장 위함이므로 repository와 상관 X?

        String storeFilename;

        if (savedFileName.equals("")) {
            storeFilename = createStoreFileName(file.getOriginalFilename());
        }
        else {
            storeFilename = savedFileName;
        }

        String relativePath = PATH + "userProfile/"+ storeFilename;
        Path absolutePath = Paths.get("").toAbsolutePath();

        System.out.println("absolutePath : " + absolutePath);
        String savePath =  absolutePath + relativePath; // 파일 저장을 위해 사용할 임시 절대경로

        System.out.println("파일 업로드 성공: " + savePath);
        System.out.println("relativePath: " + PATH + "userProfile/"+ storeFilename);
        System.out.println("absolutePath: " + absolutePath); // 프로젝트 저장된 clonestagram 가져옴

        try {
            if (!file.isEmpty()) {
                // 파일 저장
                byte[] bytes = file.getBytes();
                try (FileOutputStream fos = new FileOutputStream(savePath)) {
                    fos.write(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            System.out.println("파일 업로드 실패 : " + e.getMessage());

        }

        return storeFilename;
    }
}
