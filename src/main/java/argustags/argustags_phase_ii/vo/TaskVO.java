package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class  TaskVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @Column(nullable = false)
    private String taskname;
    @Column(nullable = false)
    private String initName;
    @Column(nullable = false)
    private ArrayList<String> workers;
    @Column(nullable = false)
    private ArrayList<Integer> imgList;
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

    public TaskVO(int ID,String name,String initName,ArrayList<Integer> imgList,String type,int process,String startTime,String endTime,String describe,String option){
        this.id = ID;
        this.taskname = name;
        this.initName = initName;
        this.imgList = imgList;
        this.typ = type;
        this.process = process;
        this.startTime = startTime;
        this.endTime=endTime;
        this.description = describe;
        this.opt = option;

        endTime = "no data";
        ArrayList<String> list = new ArrayList<>();
        workers = list;
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
        return workers;
    }

    public void setWorkers(ArrayList<String> workers){
        this.workers = workers;
    }

    public void addWorker(String workerName){
        workers.add(workerName);
    }

    public ArrayList<Integer> getImgList(){
        return imgList;
    }

    public void setImgList(ArrayList<Integer> imgList){
        this.imgList = imgList;
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
