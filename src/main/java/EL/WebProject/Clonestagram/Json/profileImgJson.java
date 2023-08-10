package EL.WebProject.Clonestagram.Json;

public class profileImgJson {
    private int memberId;
    private String imgSrc; // 컴퓨터에 저장할 (path + 세부명) 중 세부명을 db에 저장하기 위한 필드.

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getimgSrc() {
        return imgSrc;
    }

    public void setimgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
