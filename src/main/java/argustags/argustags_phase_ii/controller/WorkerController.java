package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import argustags.argustags_phase_ii.serviceImpl.TaskImpl;
import argustags.argustags_phase_ii.serviceImpl.WorkerImpl;
import argustags.argustags_phase_ii.util.Jsonhelper;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

import static argustags.argustags_phase_ii.util.Jsonhelper.fromJson;
import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Controller
public class WorkerController{

    WorkerService workerservice=new WorkerImpl();
    TaskService taskservice = new TaskImpl();
    AdminService adminservice = new AdminImpl();

    @RequestMapping("/workerHome")
    String workerMainPage(){
        return "workermain";
    }

    @RequestMapping("/worker/yourtask")
    String yourTask(){
        return "workerOngoingTask";
    }

    @RequestMapping("/worker/findtask")
    String findTask(){
        return "WorkerFindTask";
    }

    @RequestMapping("/worker/markzone")
    String markzone(){return "markzone";}

    @PostMapping(value = "/getWorkerInfo", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getWorkerInfo(@RequestParam("username") String username){
        int credit = workerservice.getCredit(username);
        int numOfTasks = 0;
        if(workerservice.getTask(username) == null){
            numOfTasks = 0;
        }
        else{
            numOfTasks = workerservice.getTask(username).size();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("username",username);
        obj.addProperty("credit",credit);
        obj.addProperty("numOfTasks",numOfTasks);
        return obj.toString();
    }

    @PostMapping(value = "/getWorkerTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getWorkerTask(@RequestParam("username") String username){
        ArrayList<TaskVO> WorkerTask = new ArrayList<>();
        WorkerTask = workerservice.getTask(username);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        obj.addProperty("num",WorkerTask.size());
        array.add(obj);

        for(int i = 0; i < WorkerTask.size() ; i++){
            TaskVO sample = WorkerTask.get(i);
            JsonObject temp = new JsonObject();
            temp.addProperty("id",sample.getID());
            temp.addProperty("name",sample.getName());
            temp.addProperty("num",1);
            temp.addProperty("end_date",sample.getEndTime());
            array.add(temp);
        }
        return array.toString();
    }

    @PostMapping(value = "/workerFindTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String FindWorkerTask(@RequestParam("username") String username){
        ArrayList<TaskVO> WorkerTask = new ArrayList<>();
        WorkerTask = adminservice.getTask();
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        obj.addProperty("num",WorkerTask.size());
        array.add(obj);

        for(int i = 1; i < WorkerTask.size() ; i++){
            TaskVO sample = WorkerTask.get(i);
            JsonObject temp = new JsonObject();
            temp.addProperty("id",sample.getID());
            temp.addProperty("name",sample.getName());
            temp.addProperty("num",1);
            temp.addProperty("end_date",sample.getEndTime());
            array.add(temp);
        }
        return array.toString();
    }

    @PostMapping(value = "/workerAcceptTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String FindWorkerTask(@RequestParam("taskId") String taskId,
    @RequestParam("workerName") String workerName){
        ResultMessage rm = workerservice.acceptTask(taskId,workerName);
        if(rm == ResultMessage.SUCCESS){
            return "success";
        }
        else{
            return "fail";
        }
    }

    @PostMapping(value = "/getImageList", produces="application/text; charset=utf-8")
    @ResponseBody
    public String GetImageList(@RequestParam("username") String username,
                               @RequestParam("taskId") String taskId){
        TaskVO vo = taskservice.getByID(taskId);
        ArrayList<String> imgList = vo.getImgList();
        String taskName = vo.getName();
        String type = vo.getType();

        JsonArray arr = new JsonArray();

        JsonObject basic_info = new JsonObject();
        basic_info.addProperty("taskName",taskName);
        basic_info.addProperty("type",type);
        basic_info.addProperty("pic_num",imgList.size());
        arr.add(basic_info);
        for(int i=0;i<imgList.size();i++){
            JsonObject obj = new JsonObject();
            obj.addProperty("image",imgList.get(i));
            System.out.println(imgList.get(i));
            arr.add(obj);
        }
        return arr.toString();
    }

    @PostMapping(value = "/updateWorkerTask", produces="application/text; charset=utf-8")
    @ResponseBody
    String updateWorkerTask(@RequestParam("username") String username,
                            @RequestParam("taskId") String taskId,
                            @RequestParam("marked_num") int marked_num,
                            @RequestParam("imgList") String imgList){
        Jsonhelper helper = new Jsonhelper();
        TaskVO vo = taskservice.getByID(taskId);
        JsonObject obj = helper.fromJson(imgList,JsonObject.class);
        ArrayList<String> list = new ArrayList<>();
        JsonArray arr = obj.getAsJsonArray("imageData");
        for(int i=0;i<arr.size();i++){
            list.add(arr.get(i).toString());
        }
        vo.setImgList(list);
        ResultMessage rm1 = taskservice.updateTask(vo);

        if( rm1 == ResultMessage.SUCCESS){
            return "success";
        }
        else{
            return "fail";
        }
    }


}
