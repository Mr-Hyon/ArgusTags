package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
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

    @PostMapping(value = "/getInfo", produces="application/text; charset=utf-8")
    @ResponseBody
    public String getInfo(){
        int ininum = adminService.getInitiatorNum();
        int workernum = adminService.getWorkerNum();
        int tasknum = adminService.getTaskNum();
        int taskfn = adminService.getTaskFinishedNum();
        int taskog= adminService.getTaskOngoingNum();
        int usernum=ininum+workernum;

        JsonObject obj = new JsonObject();
        obj.addProperty("usernum",usernum);
        obj.addProperty("ininum",ininum);
        obj.addProperty("workernum",workernum);
        obj.addProperty("tasknum",tasknum);
        obj.addProperty("taskfn",taskfn);
        obj.addProperty("taskog",taskog);
        return obj.toString();
    }


}
