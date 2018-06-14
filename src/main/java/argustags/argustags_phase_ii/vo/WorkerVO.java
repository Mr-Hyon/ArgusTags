package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worker")
public class WorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String username;

    private String password;

    private List<String> taskList;

    private int credit;

    public WorkerVO(){

    }

    public WorkerVO(String username, String password, List<String> taskList, int credit){
        this.username = username;
        this.password = password;
        this.taskList = taskList;
        this.credit = credit;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public List<String> getTaskList(){
        return taskList;
    }

    public void setTaskList(List<String> taskList){
        this.taskList = taskList;
    }

    public int getCredit(){
        return credit;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
