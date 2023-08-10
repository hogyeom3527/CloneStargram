package EL.WebProject.Clonestagram.Repository;

import EL.WebProject.Clonestagram.DAO.Repository.JdbcRepository;
import EL.WebProject.Clonestagram.DTO.postDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class PostRepository extends JdbcRepository {

    public PostRepository(DataSource dataSource) {
        super(dataSource);
    }

    // 게시물 관련 DB에 post(게시글) 내용 삽입
    public void setPost(postDTO newPost) {
        String sql = "insert into post(postvalue, postuserid, postlike, commentlistid) values(?,?,?,?)";
        // 게시글이 만들어지면, 그 게시글에 작성될 댓글또한 저장되어야함.
        // 즉, 게시글을 작성한 시점에서 댓글리스트테이블에 해당 게시글에 작성될 댓글을 저장할 리스트 작성???
        // => 댓글 관련은 추후에 다시 생각하고, 일단 포스팅 관련부터 해결.

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);


            pstmt.setString(1, newPost.getPostValue());
            pstmt.setString(2, newPost.getPostUserId());
            pstmt.setString(3, newPost.getPostLike());
            pstmt.setString(4, newPost.getCommentListId());

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public postDTO getPostValueforTEST(String userId, String postId) {
        String sql = "select postid, postuserid, postvalue from post where postid = ? and postuserid = ?";
        postDTO getPost = new postDTO();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, postId);
            pstmt.setString(2, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                getPost.setPostId(rs.getString("postid"));
                getPost.setPostUserId(rs.getString("postuserid"));
                getPost.setPostValue(rs.getString("postvalue"));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return getPost;
    }
}