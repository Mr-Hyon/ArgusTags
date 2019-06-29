package argustags.argustags_phase_ii.vo;

import org.apache.tools.ant.taskdefs.Parallel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worker")
@SecondaryTable(name="worker_info")
public class WorkerVO  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int workerid;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwd;
    @Column(nullable = false)
    private String taskList;
    @Column(nullable = false)
    private int credit;
    @Column(table="worker_info")
    private int task1acc;
    @Column(table="worker_info")
    private int task2acc;
    @Column(table="worker_info")
    private int task1fin;
    @Column(table="worker_info")
    private int task2fin;
    @Column(table="worker_info")
    private int reward1get;
    @Column(table="worker_info")
    private int reward2get;
    @Column(table="worker_info")
    private int pic1done;
    @Column(table="worker_info")
    private int pic2done;
    @Column(table="worker_info")
    private int pic1corr;
    @Column(table="worker_info")
    private int pic2corr;


    public WorkerVO(){

    }

        public WorkerVO(String username, String password, ArrayList<Integer> taskList, int credit){
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

    public ArrayList<Integer> getTaskList(){
        String[] s1 = taskList.split(" ");
        ArrayList<Integer> res = new ArrayList<>();
        if(s1[0].equals(""))
            return res;
        else{
            for(int i = 0;i<s1.length;i++){
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
        if(!s1[0].equals("")){
            for(int i = 0;i<s1.length;i++){
                res.add(Integer.parseInt(s1[i]));
            }
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

    public int getTask1acc() {
        return task1acc;
    }

    public void setTask1acc(int task1acc) {
        this.task1acc = task1acc;
    }

    public int getTask2acc() {
        return task2acc;
    }

    public void setTask2acc(int task2acc) {
        this.task2acc = task2acc;
    }

    public int getTask1fin() {
        return task1fin;
    }

    public void setTask1fin(int task1fin) {
        this.task1fin = task1fin;
    }

    public int getTask2fin() {
        return task2fin;
    }

    public void setTask2fin(int task2fin) {
        this.task2fin = task2fin;
    }

    public int getReward1get() {
        return reward1get;
    }

    public void setReward1get(int reward1get) {
        this.reward1get = reward1get;
    }

    public int getReward2get() {
        return reward2get;
    }

    public void setReward2get(int reward2get) {
        this.reward2get = reward2get;
    }

    public int getPic1done() {
        return pic1done;
    }

    public void setPic1done(int pic1done) {
        this.pic1done = pic1done;
    }

    public int getPic2done() {
        return pic2done;
    }

    public void setPic2done(int pic2done) {
        this.pic2done = pic2done;
    }

    public int getPic1corr() {
        return pic1corr;
    }

    public void setPic1corr(int pic1corr) {
        this.pic1corr = pic1corr;
    }

    public int getPic2corr() {
        return pic2corr;
    }

    public void setPic2corr(int pic2corr) {
        this.pic2corr = pic2corr;
    }
}
