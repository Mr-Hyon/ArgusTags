package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import argustags.argustags_phase_ii.serviceImpl.TaskImpl;
import argustags.argustags_phase_ii.serviceImpl.WorkerImpl;
import argustags.argustags_phase_ii.util.Jsonhelper;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
import argustags.argustags_phase_ii.vo.Tag;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static argustags.argustags_phase_ii.util.Jsonhelper.fromJson;
import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Controller
public class WorkerController{
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TaskService taskservice ;
    @Autowired
    AdminService adminservice;

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
        int credit = workerService.getCredit(username);
        int numOfTasks = 0;
        if(workerService.getTask(username) == null){
            numOfTasks = 0;
        }
        else{
            numOfTasks = workerService.getTask(username).size();
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
        WorkerTask = workerService.getTask(username);
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
        WorkerTask = workerService.getFilteredTask(username);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        if(WorkerTask == null){
            obj.addProperty("num",0);
            array.add(obj);
        }
        else {
            obj.addProperty("num", WorkerTask.size());
            array.add(obj);

            for (int i = 0; i < WorkerTask.size(); i++) {
                TaskVO sample = WorkerTask.get(i);
                JsonObject temp = new JsonObject();
                temp.addProperty("id", sample.getID());
                temp.addProperty("name", sample.getName());
                temp.addProperty("num", sample.getImgList().size());
                temp.addProperty("end_date", sample.getEndTime());
                array.add(temp);
            }
        }
        return array.toString();
    }

    @PostMapping(value = "/workerAcceptTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String FindWorkerTask(@RequestParam("taskId") int taskId,
    @RequestParam("workerName") String workerName){
        ResultMessage rm = workerService.acceptTask(taskId,workerName);
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
                               @RequestParam("taskId") int taskId){
        TaskVO vo = taskservice.getByID(taskId);
        ArrayList<Integer> imgidList = vo.getImgList();
        ArrayList<String> imgList = new ArrayList<>();

        for(int i=0;i<imgidList.size();i++){
            String base64 = taskservice.getBase64(imgidList.get(i));
            List<Tag> tags = taskservice.getTagbyWnT(username,imgidList.get(i));
            String start="";
            String end="";
            String mark_messages="";
            for(int j=0;i<tags.size();j++){
                start = start + tags.get(j).getTagStart();
                end = end + tags.get(j).getTagEnd();
                mark_messages = mark_messages + tags.get(j).getTag();
                if(j!=tags.size()-1){
                    start=start+" ";
                    end=end+" ";
                    mark_messages=mark_messages+" ";
                }
            }
            JsonObject temp = new JsonObject();
            temp.addProperty("imgid",imgidList.get(i));
            temp.addProperty("origin_image",base64);
            temp.addProperty("tagstart",start);
            temp.addProperty("tagend",end);
            temp.addProperty("tagcontent",mark_messages);
            imgList.add(temp.toString());
        }

        String taskName = vo.getName();
        String type = vo.getType();
        String option = vo.getOption();

        JsonArray arr = new JsonArray();

        JsonObject basic_info = new JsonObject();
        basic_info.addProperty("taskName",taskName);
        basic_info.addProperty("type",type);
        basic_info.addProperty("pic_num",imgList.size());
        basic_info.addProperty("option",option);

        arr.add(basic_info);
        for(int i=0;i<imgList.size();i++){
            JsonObject obj = new JsonObject();
            obj.addProperty("image",imgList.get(i));
            //System.out.println(imgList.get(i));
            arr.add(obj);
        }
        return arr.toString();
    }

    @PostMapping(value = "/updateWorkerTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String UpdateWorkerTask(@RequestParam("username") String username,
                            @RequestParam("taskId") int taskId,
                            @RequestParam("marked_num") String marked_num,
                            @RequestParam("imgList") String image,
                            @RequestParam("pic_index") int pic_index){
        System.out.println("114514");
        System.out.println(image);
        TaskVO vo = taskservice.getByID(taskId);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(image);
        JsonObject obj = jsonElement.getAsJsonObject();
        ArrayList<String> list = new ArrayList<>();
        list = vo.getImgList();

        list.set(pic_index,image);
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
