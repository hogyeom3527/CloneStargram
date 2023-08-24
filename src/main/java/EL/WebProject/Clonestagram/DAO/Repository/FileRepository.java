package EL.WebProject.Clonestagram.DAO.Repository;

import EL.WebProject.Clonestagram.DAO.Repository.Interface.FileRepositoryInterface;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

// 본 리포지토리는 사용자의 파일 업로드, 다운로드 등에 대해 다룹니다.
@Repository
public class FileRepository extends JdbcRepository implements FileRepositoryInterface {

    public FileRepository(DataSource dataSource) {
        super(dataSource);
    }



    // 파일이 이미 저장되어있으면 저장 파일명을 리턴, 없으면 DB에 저장

    /** 사용자의 프로필 이미지를 저장하기위한 함수 */
    @Override
    public String saveProFileImgSrc(String src, String userid) {
        String sql = "select profileimage from member where userid = ?";

        String subSql = "update member set profileImage = ? where userid = ?";

        String profileSrc = "";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(subSql);
            pstmt.setString(1, src);
            pstmt.setString(2, userid);
            pstmt.executeUpdate(); // 파일 저장까지 수행


            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();

            if(rs.next()) profileSrc = rs.getString("profileimage");

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return profileSrc;
    }

    /** 사용자의 프로필 이미지를 가져오기 위한 함수 */
    @Override
    public String getProFileImgSrc(String userid) {
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
            else {
                profile = "";
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return profile;
    }

    /** 게시글의 이미지를 서버에 저장하기 위한 함수 */
    @Override
    public String savePostImgSrc(String postId, List<String> imageNames) {
        String sql = "insert into POSTIMAGES values(?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);



            pstmt.setString(1, postId);


            for(int i = 0; i < imageNames.size(); i++) {
                String imageName = imageNames.get(i);

                pstmt.setInt(2, i); // 이미지 순서
                pstmt.setString(3, imageName); // 이미지 저장 이름
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return sql; // 나중에 지울것
    }

    @Override
    public String getPostImgSrc(String src, String userid) {
        return null;
    }


}
