package EL.WebProject.Clonestagram.Service;


import EL.WebProject.Clonestagram.DTO.JoinInfo;
import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.DAO.Repository.MemberRepository;
import EL.WebProject.Clonestagram.StructMapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    // 회원 확인
    public ArrayList<Member> lookUpMember(){
        ArrayList<Member> memberList = repository.getAllMemberList();
        return memberList;
    }

    // 회원 가입
    public void join(JoinInfo member) {
    // 회원가입 및 로그인 시에는 입력된 아이디 값이 이메일인지, 전화번호인지, 둘다 아닌지 대질하는 부분 필요.
        // 이를 리포지토리에서 함수화하여 사용하는 것이 편리할 것.
        // 회원가입 시에는 기입받은 값을 멤버 DTO로 바꿔 없는 id 관련을 null로.
        // 로그인 시에는 LoginInfo 사용.
        // 정규식 돌려서 기입받은 값 확인 이후 그 값에 맞는 데이터로 DB 대질해야 로그인 가능

        if(repository.isMemberEmail(member.getId())) {
            Member joinForm = MemberMapper.INSTANCE.toJoinMemberWithEmailDTO(member);
            repository.joinMember(joinForm);
        }
        else if(repository.isMemberPhNum(member.getId())) {
            Member joinForm = MemberMapper.INSTANCE.toJoinMemberWithPhNumDTO(member);
            repository.joinMember(joinForm);
        }
        else {
            throw new IllegalStateException("입력된 값이 이메일이나 전화번호 양식에 맞지 않습니다. 에러코드 301");
        }


    }

    // 회원 로그인 데이터 유무 대질 -> 로그인 대질 및 로그인 동시시행
    public Optional<Member> getUserInfo(LoginInfo loginInfo) {
        Optional<Member> forNullReturn = Optional.empty();
        // Service에서 UnWrapping 하는 것으로 객체 또는 null을 반환.
        // 로그인 실패 == null 값 반환과 동일한 상황이므로, Controller 단에서 null 처리 관련 작성요망.

        if (repository.isMemberEmail(loginInfo.getId())) {
            return repository.findLoginMember(loginInfo, "email");
        } else if (repository.isMemberPhNum(loginInfo.getId())) {
            return repository.findLoginMember(loginInfo, "phoneNumber");
        } else {
            return forNullReturn;
        }

    }

    public Optional<Member> findMemberByUserId(String userId) {
        return repository.findMemberByUserId(userId);
    }


    // 프로필 데이터 가져오기
    public Optional<Member> findProfile(String userId){
        return repository.findProfile(userId);
    }





}
