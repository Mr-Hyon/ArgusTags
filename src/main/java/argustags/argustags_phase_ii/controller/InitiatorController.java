package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.serviceImpl.InitiatorImpl;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.Tag;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InitiatorController {

    @Autowired
    private InitiatorService initiatorService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/initiatorHome")
    String initiatorMainPage() {
        return "inimain";
    }


    @RequestMapping("/initiator/message")
    String message() {
        return "inimes";
    }

    @RequestMapping("/showResult")
    String showResult(){return "showResult";}

    @PostMapping(value = "/getInitiatorInfo", produces = "application/text; charset=utf-8")
    @ResponseBody
    public String getInitiatorInfo(@RequestParam("username") String username) {
        int credit = initiatorService.getCredit(username);
        int tasknum = initiatorService.getTaskNum(username);
        int taskfn = initiatorService.getTaskFinishedNum(username);
        int taskog = initiatorService.getTaskOngoingNum(username);
        int creditcost = 10240 - initiatorService.getCredit(username);

        JsonObject obj = new JsonObject();
        obj.addProperty("credit", credit);
        obj.addProperty("tasknum", tasknum);
        obj.addProperty("taskfn", taskfn);
        obj.addProperty("taskog", taskog);
        obj.addProperty("creditcost", creditcost);
        return obj.toString();
    }

    @PostMapping(value = "/getInitiatorTask", produces = "application/text; charset=utf-8")
    @ResponseBody
    public String getInitiatorTask(@RequestParam("username") String username) {
        ArrayList<TaskVO> InitiatorTask = new ArrayList<>();
        InitiatorTask = initiatorService.getTask(username);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        obj.addProperty("num", InitiatorTask.size());
        array.add(obj);

        for (int i = 0; i < InitiatorTask.size(); i++) {
            TaskVO sample = InitiatorTask.get(i);
            JsonObject temp = new JsonObject();
            temp.addProperty("id", sample.getID());
            temp.addProperty("type", sample.getType());
            temp.addProperty("obj", sample.getDescribe());
            temp.addProperty("num", sample.getImgList().size());
            temp.addProperty("status", initiatorService.getTaskProgress(sample.getID()));
            temp.addProperty("end_date", sample.getEndTime());
            array.add(temp);
        }
        return array.toString();
    }


    @PostMapping(value = "/endTask", produces = "application/text; charset=utf-8")
    @ResponseBody
    public String endTask(@RequestParam("taskId") int TaskId) {
        System.out.println("yes");
        String type = taskService.getByID(TaskId).getType();
        if (type.equals("0"))
            adminService.rewardAndPunish0(TaskId);
        else
            adminService.rewardAndPunish12(TaskId);
        return "success";
    }

    @PostMapping(value = "/checkResult", produces = "application/text; charset=utf-8")
    @ResponseBody
    public String checkResult(@RequestParam("taskId") int taskId){
        TaskVO vo = taskService.getByID(taskId);
        String task_type = vo.getType();
        int type = Integer.parseInt(task_type);
        ArrayList<Integer> imgidList = vo.getImgList();
        ArrayList<String> imgList = new ArrayList<>();

        for(int i=0;i<imgidList.size();i++){
            String base64 = taskService.getBase64(imgidList.get(i));
            List<Tag> tags = new ArrayList<>();
            if(type == 0) tags = adminService.getAnswerTag(imgidList.get(i));
            else if (type == 1) tags = adminService.getFrames(taskId,imgidList.get(i));
            else if (type == 2) tags = adminService.getFrameAndAnswer(taskId,imgidList.get(i));
            String start="";
            String end="";
            String mark_messages="";
            for(int j=0;j<tags.size();j++){
                System.out.println(tags.get(j).getTag());
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
        String option = vo.getOption();
        String disb = vo.getDescribe();

        JsonArray arr = new JsonArray();

        JsonObject basic_info = new JsonObject();
        basic_info.addProperty("taskName",taskName);
        basic_info.addProperty("type",task_type);
        basic_info.addProperty("pic_num",imgList.size());
        basic_info.addProperty("option",option);
        basic_info.addProperty("discription",disb);

        arr.add(basic_info);
        for(int i=0;i<imgList.size();i++){
            JsonObject obj = new JsonObject();
            obj.addProperty("image",imgList.get(i));
            //System.out.println(imgList.get(i));
            arr.add(obj);
        }
        return arr.toString();
    }
}
