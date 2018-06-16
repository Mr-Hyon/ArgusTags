package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminImpl implements AdminService {
    @Autowired
    private TaskRepository taskRepository;
    //  实现登录功能，判断admin用户名密码输入是否正确
    public ResultMessage login(String adminname, String password){
        if(adminname.equals("admin")&&password.equals("123456")){
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    //  找到initiatorList中的账号密码记录，除以2即为initiator总数
    public int getInitiatorNum(){
        String initiatorList = "initiatorList";
        FileOpe fo = new FileOpe();
        return fo.getLineNum(initiatorList)/2;
    }

    //  找到workerList中的账号密码记录，除以2即为worker总数
    public int getWorkerNum(){
        String workerList = "workerList";
        FileOpe fo = new FileOpe();
        return fo.getLineNum(workerList)/2;
    }

    //  找到taskList中的task记录，即为task总数
    public int getTaskNum(){
        String taskList = "taskList";
        FileOpe fo = new FileOpe();
        return fo.getLineNum(taskList);
    }

    //返回系统中所有task
    public ArrayList<TaskVO> getTask(){
        ArrayList<TaskVO> alltask = new ArrayList();
        alltask= (ArrayList)(taskRepository.findAll());
        return alltask;
    }

    //返回状态为ongoing的task记录总数
    public int getTaskOngoingNum(){
        String path = "taskList";
        FileOpe fo = new FileOpe();
        return fo.getStatusNum(path,"ongoing");
    }

    //返回状态为finished的task记录总数
    public int getTaskFinishedNum(){
        String path = "taskList";
        FileOpe fo = new FileOpe();
        return fo.getStatusNum(path,"finished");
    }
}
