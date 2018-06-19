package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class  TaskVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column(nullable = false)
    private String taskname;
    @Column(nullable = false)
    private String initName;
    @Column(nullable = false)
    private String workers;
    @Column(nullable = false)
    private String imgList;
    @Column(nullable = false)
    private String typ;
    @Column(nullable = false)
    private int process;
    @Column(nullable = false)
    private String startTime;
    @Column(nullable = false)
    private String endTime;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String opt;


    public TaskVO(){

    }

    
    public TaskVO(String name,String initName,ArrayList<Integer> imgList,String type,int process,String startTime,String endTime,String describe,String option){

        this.taskname = name;
        this.initName = initName;
        String s = "";
        for(int i = 0;i<imgList.size();i++){
            int temp = imgList.get(i);
            s=s+temp;
            if(i==imgList.size()-1){
               s=s+"" ;
            }
            else{
                s=s+" ";
            }
        }
        this.imgList = s;

        this.typ = type;
        this.process = process;
        this.startTime = startTime;
        this.endTime=endTime;
        this.description = describe;
        this.opt = option;

        endTime = "no data";

        workers = new String();
    }

    public int getID(){
        return id;
    }

    public void setID(int ID){
        this.id = ID;
    }

    public String getName(){
        return taskname;
    }

    public void setName(String name){
        this.taskname = name;
    }

    public String getInitName(){
        return initName;
    }

    public void setInitName(String initName){
        this.initName = initName;
    }

    public ArrayList<String> getWorkers(){
        String[] s1 = workers.split(" ");
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0;i<s1.length;i++){
            res.add(s1[i]);
        }
        return res;
    }

    public void setWorkers(ArrayList<String> workers){
        String s = "";
        for(int i = 0;i<workers.size();i++){
            String temp = workers.get(i);
            s=s+temp;
            if(i==workers.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.imgList = s;
    }

    public void addWorker(String workerName){
        String[] s1 = workers.split(" ");
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0;i<s1.length;i++){
            res.add(s1[i]);
        }
        String s = "";
        for(int i = 0;i<res.size();i++){
            String temp = res.get(i);
            s=s+temp;
            if(i==res.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.imgList = s;

    }

    public ArrayList<Integer> getImgList(){
        if(imgList.equals("")){
            ArrayList<Integer> li = new ArrayList<>();
            return li;
        }
        else {
            String[] s1 = imgList.split(" ");
            ArrayList<Integer> res = new ArrayList<>();
            for (int i = 0; i < s1.length; i++) {
                res.add(Integer.parseInt(s1[i]));
            }
            return res;
        }
    }

    public void setImgList(ArrayList<Integer> imgList){
        String s = "";
        for(int i = 0;i<imgList.size();i++){
            int temp = imgList.get(i);
            s=s+temp;
            if(i==imgList.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.imgList = s;
    }

    public String getType(){
        return typ;
    }

    public void setType(String type){
        this.typ = type;
    }

    public int getProcess(){
        return process;
    }

    public void setProcess(int process){
        this.process = process;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public String getDescribe(){
        return description;
    }

    public void setDescribe(String describe){
        this.description = describe;
    }

    public String getOption(){
        return opt;
    }

    public void setOption(String option){
        this.opt = option;
    }


}
