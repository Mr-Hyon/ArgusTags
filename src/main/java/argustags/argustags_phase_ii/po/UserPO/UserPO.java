package argustags.argustags_phase_ii.po.UserPO;

import javax.persistence.*;

import java.io.Serializable;

@Entity

@Table (name = "userpo")

public class UserPO implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;
    private String username;
    private String password;
    private String position;
    private String authority;
    private String userID;
    private int credit;
    public UserPO(String username, String passwords, String position,String authority,String userID,int credit) {
        this.username = username;
        this.password = passwords;
        this.position = position;
        this.authority=authority;
        this.userID=userID;
        this.credit=credit;
    }
    public UserPO() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name="authority")
    public String getAuthority(){
        return authority;
    }

    public void setAuthority(String authority){
        this.authority=authority;
    }

    @Column(name="userID")
    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID=userID;
    }

    @Column(name="Credit")
    public int getContact(){
        return credit;
    }

    public void setContact(int credit){
        this.credit=credit;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

