package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class  TaskVO implements Serializable {
    @Id
    private int id;
    private String name;
    private String initName;

    private ArrayList<String> workers;

    private ArrayList<String> imgList;
    private String type;
    private String status;
    private String startTime;
    private String endTime;
    private String description;
    private String object;


    public TaskVO(){

    }

    public TaskVO(int ID,String name,String initName,ArrayList<String> imgList,String type,String status,String startTime,String endTime,String describe,String object){
        this.id = ID;
        this.name = name;
        this.initName = initName;
        this.imgList = imgList;
        this.type = type;
        this.status = status;
        this.startTime = startTime;
        this.endTime=endTime;
        this.description = describe;
        this.object = object;

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
        return name;
    }

    public void setName(String name){
        this.name = name;
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

    public ArrayList<String> getImgList(){
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList){
        this.imgList = imgList;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
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

    public String getObeject(){
        return object;
    }

    public void setObject(String object){
        this.object = object;
    }


}
