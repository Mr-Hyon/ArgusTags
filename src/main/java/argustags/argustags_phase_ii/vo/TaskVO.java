package argustags.argustags_phase_ii.vo;

import java.util.ArrayList;

public class  TaskVO {

    private String ID;
    private String name;
    private String initName;
    private ArrayList<String> workers;
    private ArrayList<String> imgList;
    private String type;
    private String status;
    private String startTime;
    private String endTime;
    private String describe;
    private String object;
    private double cut;
    private int reward;
    private int workernum;

    public TaskVO(String ID,String name,String initName,ArrayList<String> imgList,String type,String status,String startTime,String endTime,String describe,String object,double cut,int reward,int workernum){
        this.ID = ID;
        this.name = name;
        this.initName = initName;
        this.imgList = imgList;
        this.type = type;
        this.status = status;
        this.startTime = startTime;
        this.endTime=endTime;
        this.describe = describe;
        this.object = object;
        this.cut = cut;
        this.reward = reward;
        this.workernum = workernum;
        endTime = "no data";
        ArrayList<String> list = new ArrayList<>();
        workers = list;
    }

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
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
        return describe;
    }

    public void setDescribe(String describe){
        this.describe = describe;
    }

    public String getObeject(){
        return object;
    }

    public void setObject(String object){
        this.object = object;
    }

    public double getCut(){
        return cut;
    }

    public void setCut(int cut){
        this.cut = cut;
    }

    public int getReward(){
        return reward;
    }

    public void setReward(int reward){
        this.reward = reward;
    }

    public int getWorkernum(){
        return workernum;
    }

    public void setWorkernum(int workernum){
        this.workernum = workernum;
    }
}
