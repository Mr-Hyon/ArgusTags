package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    TaskService taskService;

    @PostMapping(value = "/getInfo", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getInfo(){
        int ininum = adminService.getInitiatorNum();
        int workernum = adminService.getWorkerNum();
        int tasknum = adminService.getTaskNum();
        int taskfn = adminService.getTaskFinishedNum();
        int taskog= adminService.getTaskOngoingNum();
        int imagenum=adminService.getImageNum();
        int usernum=ininum+workernum;
        int type0num=taskService.getByType("0").size();
        int type1num=taskService.getByType("1").size();
        int type2num=taskService.getByType("2").size();

        JsonObject obj = new JsonObject();
        obj.addProperty("usernum",usernum);
        obj.addProperty("ininum",ininum);
        obj.addProperty("workernum",workernum);
        obj.addProperty("tasknum",tasknum);
        obj.addProperty("taskfn",taskfn);
        obj.addProperty("taskog",taskog);
        obj.addProperty("imagenum",imagenum);
        obj.addProperty("type0num",type0num);
        obj.addProperty("type1num",type1num);
        obj.addProperty("type2num",type2num);
        return obj.toString();
    }


}
