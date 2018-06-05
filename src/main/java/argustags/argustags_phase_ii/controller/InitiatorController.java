package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.serviceImpl.InitiatorImpl;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
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

@Controller
public class InitiatorController{

    InitiatorService Initiatorservice=new InitiatorImpl();

    @RequestMapping("/initiatorHome")
    String initiatorMainPage(){
        return "inimain";
    }


    @RequestMapping("/initiator/message")
    String message(){
        return "inimes";
    }

    @PostMapping(value = "/getInitiatorInfo", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getInitiatorInfo(@RequestParam("username") String username){
        int credit = Initiatorservice.getRestCredit(username);
        int tasknum=Initiatorservice.getTaskNum(username);
        int taskfn=Initiatorservice.getTaskFinishedNum(username);
        int taskog=Initiatorservice.getTaskOngoingNum(username);
        int creditcost=Initiatorservice.getSpentCredit(username);

        JsonObject obj = new JsonObject();
        obj.addProperty("credit",credit);
        obj.addProperty("tasknum",tasknum);
        obj.addProperty("taskfn",taskfn);
        obj.addProperty("taskog",taskog);
        obj.addProperty("creditcost",creditcost);
        return obj.toString();
    }

    @PostMapping(value = "/getInitiatorTask", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getInitiatorTask(@RequestParam("username") String username) {
        ArrayList<TaskVO> InitiatorTask = new ArrayList<>();
        InitiatorTask = Initiatorservice.getTask(username);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        obj.addProperty("num", InitiatorTask.size());
        array.add(obj);

        for (int i = 0; i < InitiatorTask.size(); i++) {
            TaskVO sample = InitiatorTask.get(i);
            JsonObject temp = new JsonObject();
            temp.addProperty("id", sample.getID());
            temp.addProperty("type", sample.getType());
            temp.addProperty("obj",sample.getObeject());
            temp.addProperty("num", sample.getImgList().size());
            temp.addProperty("status",sample.getStatus());
            temp.addProperty("end_date", sample.getEndTime());
            array.add(temp);
        }
        return array.toString();
    }

}
