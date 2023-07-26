package EL.WebProject.Clonestagram.DTO;

public class Member {
    // DTO for Member
    // 가변객체
    // 가져온 회원정보를 수정할 일이 있다!
    // ex. 회원가입 이후, 회원 정보를 수정하는 경우
    // 페이지에 불러온 데이터를 수정하여 다시 전달하는 등...?
    
    private String email;
    private String phoneNumber;
    private String name;
    private String userName;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

