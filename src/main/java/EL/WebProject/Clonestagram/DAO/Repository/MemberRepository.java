package EL.WebProject.Clonestagram.DAO.Repository;

import EL.WebProject.Clonestagram.DAO.Repository.Interface.MemberRepositoryInterface;
import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Domain.MemberDomain;
import EL.WebProject.Clonestagram.StructMapper.MemberMapper;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

// 본 리포지토리는 사용자의 회원가입, 로그인, 로그아웃에 대해 다룹니다.
@Repository
public class MemberRepository extends JdbcRepository implements MemberRepositoryInterface {

    // 이메일 및 전화번호 패턴
    private String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    private String phNumPattern = "^\\d{3}-\\d{3,4}-\\d{4}$";

    private boolean isMatching;

    // 입력된 이메일 정규식 확인
    @Override
    public boolean isMemberEmail(String email) {
        isMatching = email.matches(emailPattern);
        return isMatching;
    }

    // 입력된 전화번호 정규식 확인
    @Override
    public boolean isMemberPhNum(String phNum) {
        isMatching = phNum.matches(phNumPattern);
        return isMatching;
    }

    public MemberRepository(DataSource dataSource) {
        super(dataSource);
    }


    // 회원가입
    @Override
    public void joinMember(Member member) {
        String sql = "insert into member(email, phonenumber, name, username, password) values(?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            MemberDomain memberDomain = MemberMapper.INSTANCE.toMemberDomain(member);

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberDomain.getEmail());
            pstmt.setString(2, memberDomain.getPhoneNumber());
            pstmt.setString(3, memberDomain.getName());
            pstmt.setString(4, memberDomain.getUserName());
            pstmt.setString(5, memberDomain.getPassword());

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    // 모든 사용자 조회
    @Override
    public ArrayList<Member> getAllMemberList(){
        ArrayList<MemberDomain> domainList = new ArrayList<>();
        ArrayList<Member> dtoList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "select * from member";
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()){
                MemberDomain member = new MemberDomain();
                member.setUserid(rs.getString(1));
                member.setEmail(rs.getString(2));
                member.setPhoneNumber(rs.getString(3));
                member.setName(rs.getString(4));
                member.setUserName(rs.getString(5));
                member.setPassword(rs.getString(6));
                member.setIntroduce(rs.getString(7));
                member.setProfileImage(rs.getString(8));
                domainList.add(member);
            }
            dtoList = MemberMapper.INSTANCE.toMemberALDTO(domainList); // mapping


        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return dtoList;
    }

    @Override
    public Optional<Member> findMemberByUserId(String userId) {
        MemberDomain memberDomain;
        Optional<Member> memberDTO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from member where userid = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                memberDomain = new MemberDomain();

                memberDomain.setUserid(rs.getString("userid"));
                memberDomain.setEmail(rs.getString("email"));
                memberDomain.setPhoneNumber(rs.getString("phoneNumber"));
                memberDomain.setName(rs.getString("name"));
                memberDomain.setUserName(rs.getString("userName"));
                memberDomain.setPassword(rs.getString("password"));

                memberDTO = Optional.ofNullable(MemberMapper.INSTANCE.toMemberDTO(memberDomain));
            }



        } catch (Exception e) {
            // System.out.println("아예 안돌아가는뎁쇼? MemberRepository-findMemberByUserId");
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return memberDTO;
        // 모든 멤버 중 email(== id)가 같은 첫 번째 멤버 반환

    }



    // 기입된 형식 따른 로그인
    @Override
    public Optional<Member> findLoginMember(LoginInfo loginInfo, String idForm) {
        MemberDomain memberDomain = new MemberDomain();
        Optional<Member> memberDTO;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from member where " + idForm + " = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginInfo.getId());
            rs = pstmt.executeQuery();


            if (rs.next()) {
                String dbPw = rs.getString("password");
                if (dbPw.equals(loginInfo.getPassword())) {
                    memberDomain.setUserid(rs.getString("userid"));
                    memberDomain.setEmail(rs.getString("email"));
                    memberDomain.setPhoneNumber(rs.getString("phoneNumber"));
                    memberDomain.setName(rs.getString("name"));
                    memberDomain.setUserName(rs.getString("userName"));
                    memberDomain.setPassword(rs.getString("password"));
                }
                else return Optional.empty(); // 확인 결과 없으면 empty 반환
            }
            else return Optional.empty(); // 확인 결과 없으면 empty 반환


            memberDTO = Optional.ofNullable(MemberMapper.INSTANCE.toMemberDTO(memberDomain));
            System.out.println(memberDomain.getEmail());

        } catch (Exception e){
            throw new IllegalStateException(e);
        }
        finally {
            close(conn, pstmt, rs);
        }

        return memberDTO;
    }


    /*
    * // 사용처 없는 것으로 추정
//    public int loginMember(LoginInfo loginInfo){
//
//        int checkLogin = 0;
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "select * from member where email = ?";
//
//            conn = getConnection();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, loginInfo.getId());
//            rs = pstmt.executeQuery();
//
//            if (rs.next() == true){
//                String dbPw = rs.getString("password");
//                if (dbPw.equals(loginInfo.getPassword())){
//                    checkLogin = 0;
//                }
//                else{
//                    checkLogin = 1;
//                }
//            }
//            else {
//                checkLogin = 2;
//            }
//
//            conn.close();
//
//        } catch (Exception e){
//            throw new IllegalStateException(e);
//        } finally {
//            close(conn, pstmt, rs);
//        }
//
//        return checkLogin;
//    }
    * */





    @Override
    public String whatMemberImage(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userImageSrc;

        try {
            String sql = "select profileimage from member where userId = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();


            if (rs.next()) {
                userImageSrc = rs.getString("profileimage");
            }
            else {
                userImageSrc = "";
            }


        } catch (Exception e){
            throw new IllegalStateException(e);
        }
        finally {
            close(conn, pstmt, rs);
        }


        return userImageSrc;
    }



    // 프로필 데이터 가져오기
    public Optional<Member> findProfile(String userId) {
        MemberDomain memberDomain;
        Optional<Member> memberDTO = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            updatePostCnt(userId);

            String sql = "select * from member where userid = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                memberDomain = new MemberDomain();

                memberDomain.setUserid(rs.getString("userid"));
                memberDomain.setUserName(rs.getString("userName"));
                memberDomain.setIntroduce(rs.getString("introduce"));
                memberDomain.setPostCnt(rs.getInt("postCnt"));

                memberDTO = Optional.ofNullable(MemberMapper.INSTANCE.toUserProfileDTO(memberDomain));
            }



        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return memberDTO;
    }

    // 게시물 개수 가져오기
    public void updatePostCnt(String userId){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "update member " +
                    "set postcnt = (select count(postuserid) from post where postuserid = ?) " +
                    "where userid = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userId);

            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
        }
    }

}
