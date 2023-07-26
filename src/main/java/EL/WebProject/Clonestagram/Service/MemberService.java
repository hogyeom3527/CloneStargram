package EL.WebProject.Clonestagram.Service;


import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.DAO.Repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MemberService {

    private final JdbcRepository repository;

    @Autowired
    public MemberService(JdbcRepository repository) {
        this.repository = repository;
    }

    // 회원 확인
    public ArrayList<Member> lookUpMember(){
        ArrayList<Member> memberList = repository.getAllMemberList();
        return memberList;
    }

    // 회원 가입
    public void join(Member member) {
        repository.joinMember(member);
    }

    public Member getUserInfo(LoginInfo loginInfo) {
        if(login(loginInfo) == 0) {
            Member loginMember = repository.findLoginMember(loginInfo);
            return loginMember;
        }
        else {
            Member temp = new Member();
            temp.setEmail("0");
            temp.setPhoneNumber("0");
            temp.setName("0");
            temp.setUserName("0");
            temp.setPassword("0");

            return temp;
        }
        // optional 및 에러처리 내역은 초안 작성에서 고려하지 않음
        // 해당 내역은 0721 이후의 진행에서 작성 예정.
    }

    public int login(LoginInfo loginInfo) { return repository.loginMember(loginInfo); }

}
