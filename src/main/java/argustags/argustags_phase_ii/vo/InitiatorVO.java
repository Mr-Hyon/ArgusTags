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
    private String taskList;
    @Column(nullable = false)
    private int credit;

    public InitiatorVO(){

    }

        public InitiatorVO(String username,String password,ArrayList<Integer> taskList,int credit){
            this.username = username;
            this.passwd = password;
            String s = "";
            for(int i = 0;i<taskList.size();i++){
                int temp = taskList.get(i);
                s=s+temp;
                if(i==taskList.size()-1){
                    ;
                }
                else{
                    s=s+" ";
                }
            }

            this.taskList = s;
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
        if(taskList.equals("")){
            ArrayList<Integer> li = new ArrayList<>();
            return li;
        }
        else {
            String[] s1 = taskList.split(" ");
            ArrayList<Integer> res = new ArrayList<>();
            for (int i = 0; i < s1.length; i++) {

                res.add(Integer.parseInt(s1[i]));
            }
            return res;
        }
    }

    public void setTaskList(ArrayList<Integer> taskList){
        String s = "";
        for(int i = 0;i<taskList.size();i++){
            int temp = taskList.get(i);
            s=s+temp;
            if(i==taskList.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.taskList = s;
    }

    public void addTask(int TaskID){
        String[] s1 = taskList.split(" ");
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0;i<s1.length;i++){
            res.add(Integer.parseInt(s1[i]));
        }
        res.add(TaskID);
        String s = "";
        for(int i = 0;i<res.size();i++){
            int temp = res.get(i);
            s=s+temp;
            if(i==res.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.taskList = s;
    }

    public int getCredit(){
        return credit;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
