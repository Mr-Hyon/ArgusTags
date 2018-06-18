package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;


@Entity
@Table(name = "initiator")
public class InitiatorVO  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwd;
    @Column(nullable = false)
    private ArrayList<Integer> taskList;
    @Column(nullable = false)
    private int credit;

    public InitiatorVO(){

    }

        public InitiatorVO(String username,String password,ArrayList<Integer> taskList,int credit){
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

    public ArrayList<Integer> getTaskList(){
        return taskList;
    }

    public void setTaskList(ArrayList<Integer> taskList){
        this.taskList = taskList;
    }

    public int getCredit(){
        return credit;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
