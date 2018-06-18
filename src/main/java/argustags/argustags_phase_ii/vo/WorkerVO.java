package argustags.argustags_phase_ii.vo;

import org.apache.tools.ant.taskdefs.Parallel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worker")
public class WorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int workerid;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwd;
    @Column(nullable = false)
    private ArrayList<Integer> taskList;
    @Column(nullable = false)
    private int credit;

    public WorkerVO(){

    }

        public WorkerVO(String username, String password, ArrayList<Integer> taskList, int credit){
        this.username = username;
        this.passwd = password;
        this.taskList = taskList;
        this.credit = credit;
    }

    public int getWorkerid() {
        return workerid;
    }

    public void setWorkerid(int userid) {
        this.workerid = userid;
    }

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

    public ArrayList<Integer> getTaskList(){return taskList;}

    public void setTaskList(ArrayList<Integer> taskList){
        this.taskList = taskList;
    }

    public void addTask(int TaskID){
       taskList.add(TaskID);
    }

    public int getCredit(){
        return credit;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
