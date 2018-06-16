package argustags.argustags_phase_ii.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;


@Entity
@Table(name = "initiator")
public class InitiatorVO implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String passwd;
    private ArrayList<String> taskList;
    private int credit;

    public InitiatorVO(){

    }

    public InitiatorVO(String username,String password,ArrayList<String> taskList,int credit){
        this.username = username;
        this.passwd = password;
        this.taskList = taskList;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){ this.id = id; }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return passwd;
    }

    public void setPassword(String password){
        this.passwd = password;
    }

    public ArrayList<String> getTaskList(){
        return taskList;
    }

    public void setTaskList(ArrayList<String> taskList){
        this.taskList = taskList;
    }

    public int getCredit(){
        return credit;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
