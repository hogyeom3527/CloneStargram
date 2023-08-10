package EL.WebProject.Clonestagram.DAO.Repository;

import EL.WebProject.Clonestagram.DAO.Repository.Interface.FileRepositoryInterface;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 본 리포지토리는 사용자의 파일 업로드, 다운로드 등에 대해 다룹니다.
@Repository
public class FileRepository extends JdbcRepository implements FileRepositoryInterface {

    public FileRepository(DataSource dataSource) {
        super(dataSource);
    }



    // 파일이 이미 저장되어있으면 저장 파일명을 리턴, 없으면 DB에 저장
    @Override
    public String saveFileSrc(String src, String userid) {
        String sql = "select profileimage from member where userid = ?";

        String subSql = "update member set profileImage = ? where userid = ?";

        String profileSrc = "";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();

            // 이미 저장된 값이 있으면 해당 값 가져와서 리턴하고 종료.
            if(rs.next()) {
                profileSrc = rs.getString("profileimage");
                close(conn, pstmt, rs);
                return profileSrc;
            }

            pstmt = conn.prepareStatement(subSql);
            pstmt.setString(1, src);
            pstmt.setString(2, userid);

            pstmt.executeUpdate(); // 파일 저장까지 수행

            pstmt = conn.prepareStatement(sql);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return profileSrc;
    }

    @Override
    public String getFileSrc(String userid) {
        String profile = "";

        String sql = "select profileImage from member where userid = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if(rs.next()) {

                profile = rs.getString("profileimage");
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return profile;
    }


}
