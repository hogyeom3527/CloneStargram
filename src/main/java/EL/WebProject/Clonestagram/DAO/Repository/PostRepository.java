package EL.WebProject.Clonestagram.DAO.Repository;

import EL.WebProject.Clonestagram.DTO.postDTO;
import EL.WebProject.Clonestagram.DTO.userContentsDTO;
import EL.WebProject.Clonestagram.Domain.MemberDomain;
import EL.WebProject.Clonestagram.StructMapper.MemberMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository extends JdbcRepository{

    public PostRepository(DataSource dataSource) {
        super(dataSource);
    }

    // 게시물 관련 DB에 post(게시글) 내용 삽입
    public void setPost(postDTO newPost) {
        String sql = "insert into post values(?,?,?,?,?,?)";
        // 게시글이 만들어지면, 그 게시글에 작성될 댓글또한 저장되어야함.
        // 즉, 게시글을 작성한 시점에서 댓글리스트테이블에 해당 게시글에 작성될 댓글을 저장할 리스트 작성???
        // => 댓글 관련은 추후에 다시 생각하고, 일단 포스팅 관련부터 해결.

//        String subSql = "insert into postImages values(?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newPost.getPostId());
            pstmt.setString(2, newPost.getPostValue());
            pstmt.setString(3, newPost.getPostUserId());
            pstmt.setInt(4, newPost.getPostLike());
            pstmt.setString(5, newPost.getCommentListId());
            pstmt.setTimestamp(6, newPost.getDate());

            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }


    }

    /** 포스트 내용을 가져옴. */
    public postDTO getPostValueforTEST(String userId, String postId) {
        String sql = "select postid, postuserid, postvalue, posttime from post where postid = ? and postuserid = ?";
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
                getPost.setDate(rs.getTimestamp("posttime"));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return getPost;
    }

    public void setPostImage() {

    }


    /** 프로필페이지 작성을 위해 포스트id, 포스트 0번째이미지, 포스트 댓글 개수를 가져옴. */
    public List<userContentsDTO> getSemiPostList(String userId) {


        List<userContentsDTO> DTOList = new ArrayList<>();

        String sql = "select postid, imgsrc from forsemipost where userid = ?";


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                userContentsDTO DTO = new userContentsDTO();
                DTO.setPostId(rs.getString(1));
                DTO.setImgSrc(rs.getString(2));
                DTO.setCommentsNum(0); // 댓글 기능 구현 시 db view 및 해당 부문 추가 작성 필요함.
                DTOList.add(DTO);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }


        return DTOList;
    }



}
