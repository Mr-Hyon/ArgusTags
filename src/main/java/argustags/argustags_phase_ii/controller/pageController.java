package argustags.argustags_phase_ii.controller;

import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.serviceImpl.AdminImpl;
import argustags.argustags_phase_ii.serviceImpl.InitiatorImpl;
import argustags.argustags_phase_ii.serviceImpl.WorkerImpl;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class pageController {

    private WorkerService workerService;

    @Autowired
    public pageController(WorkerService workerService,InitiatorService initiatorService) {
        this.workerService = workerService;
        this.initiatorService = initiatorService;
    }

    private InitiatorService initiatorService;


    @RequestMapping("/")
    String mainpage(){
        return "index";
    }

    @RequestMapping("/redirect")
    String registerPage(){
        return "register";
    }

    @RequestMapping("/adminHome")
    String page3(){
        return "admin";
    }

    @RequestMapping("/releaseTask")
    String page4(){
        return "taskrl";
    }

    @RequestMapping("/detailMesIni")
    String page5(){
        return "detailMesIni";
    }

    @RequestMapping("/detailMesWor")
    String page6(){
        return "detailMesWor";
    }

    @PostMapping(value = "/login", produces="application/text; charset=utf-8")
    @ResponseBody
    public String login(@RequestParam("username") String username,
                 @RequestParam("password") String password,
                 @RequestParam("type") String type
    ){
        System.out.println("login"+username+" "+password);
        String result = "";

        if(type.equals("admin")){
            AdminService admin=new AdminImpl();
            return admin.login(username,password).toString();
        }

        if(type.equals("worker")){
            return workerService.login(username,password).toString();

        }

        if(type.equals("initiator")){
            return initiatorService.login(username,password).toString();
        }

        return result;
    }
    @PostMapping(value = "/register", produces="application/text; charset=utf-8")
    @ResponseBody
    public String register(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("type") String type
    ){
//        System.out.println("login"+username+" "+password);
        String result = "";


        if(type.equals("worker")){
            return workerService.register(username,password).toString();
        }

        if(type.equals("initiator")){
            return initiatorService.register(username,password).toString();
        }

        return result;
    }


}
