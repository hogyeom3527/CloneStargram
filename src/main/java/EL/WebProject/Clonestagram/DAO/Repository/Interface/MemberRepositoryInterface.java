package EL.WebProject.Clonestagram.DAO.Repository.Interface;

import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;

import java.util.ArrayList;
import java.util.Optional;

public interface MemberRepositoryInterface {
    // 회원가입
    void joinMember(Member member);

    // 모든 사용자 조회
    ArrayList<Member> getAllMemberList();

    Optional<Member> findMemberByUserId(String userId);

    // 기입된 형식 따른 로그인
    Optional<Member> findLoginMember(LoginInfo loginInfo, String idForm);

    // 입력된 이메일 정규식 확인
    boolean isMemberEmail(String email);

    // 입력된 전화번호 정규식 확인
    boolean isMemberPhNum(String phNum);

    String whatMemberImage(String userId);
}
