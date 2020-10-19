package co.dianjiu.swagger.pojo;


public class UserVO {
    private int id;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserVO() {
    }

    public UserVO(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
