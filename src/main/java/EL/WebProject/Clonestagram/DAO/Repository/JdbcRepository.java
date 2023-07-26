package EL.WebProject.Clonestagram.DAO.Repository;

import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Domain.JoinMemberForm;
import EL.WebProject.Clonestagram.StructMapper.MemberMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class JdbcRepository {

    private final DataSource dataSource;

    public JdbcRepository(DataSource dataSource) {
                this.dataSource = dataSource;
            }


    public void joinMember(Member member) {
        String sql = "insert into member values(?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            JoinMemberForm memberDomain = MemberMapper.INSTANCE.toMemberDomain(member);

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

    public ArrayList<Member> getAllMemberList(){
        ArrayList<JoinMemberForm> domainList = new ArrayList<>();
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
                JoinMemberForm member = new JoinMemberForm();
                member.setEmail(rs.getString(1));
                member.setPhoneNumber(rs.getString(2));
                member.setName(rs.getString(3));
                member.setUserName(rs.getString(4));
                member.setPassword(rs.getString(5));
                domainList.add(member);
            }
            dtoList = MemberMapper.INSTANCE.toMemberALDTO(domainList);


        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return dtoList;
    }

    public int loginMember(LoginInfo loginInfo){

        int checkLogin = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from member where email = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginInfo.getEmail());
            rs = pstmt.executeQuery();

            if (rs.next() == true){
                String dbPw = rs.getString("password");
                if (dbPw.equals(loginInfo.getPassword())){
                    checkLogin = 0;
                }
                else{
                    checkLogin = 1;
                }
            }
            else {
                checkLogin = 2;
            }

            conn.close();

        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return checkLogin;
    }


    public Member findLoginMember(LoginInfo loginInfo) {
        JoinMemberForm memberDomain = new JoinMemberForm();
        Member memberDTO = new Member();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from member where email = ?";

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginInfo.getEmail());
            rs = pstmt.executeQuery();

            while(rs.next()) {
                memberDomain.setEmail(rs.getString("email"));
                memberDomain.setPhoneNumber(rs.getString("phoneNumber"));
                memberDomain.setName(rs.getString("name"));
                memberDomain.setUserName(rs.getString("userName"));
                memberDomain.setPassword(rs.getString("password"));
            }

            memberDTO = MemberMapper.INSTANCE.toMemberDTO(memberDomain);

        } catch (Exception e){
            throw new IllegalStateException(e);
        }
        finally {
            close(conn, pstmt, rs);
        }

        return memberDTO;
    }



    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
