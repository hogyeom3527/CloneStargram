package EL.WebProject.Clonestagram.DTO;


// 로그인 관련 정보
public class LoginInfo {
    private String id;

    private String password;

    private boolean isLogin = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean login) {
        this.isLogin = login;
    }
}
