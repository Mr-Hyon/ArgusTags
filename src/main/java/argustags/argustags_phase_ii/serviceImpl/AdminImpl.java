package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.InitiatorRepository;
import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.repository.WorkerRepository;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
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
    private WorkerRepository workerRepository;
    @Autowired
    private InitiatorRepository initiatorRepository;
    @Autowired
    private TaskRepository taskRepository;


    //  实现登录功能，判断admin用户名密码输入是否正确
    public ResultMessage login(String adminname, String password){
        if(adminname.equals("admin")&&password.equals("123456")){
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    //  返回initiator总数
    public int getInitiatorNum(){
        List<InitiatorVO> list = initiatorRepository.findAll();
        return list.size();
    }

    //  返回worker总数
    public int getWorkerNum(){
        List<WorkerVO> list = workerRepository.findAll();
        return list.size();
    }

    //  返回task总数
    public int getTaskNum(){
        List<TaskVO> list = taskRepository.findAll();
        return list.size();
    }

    //返回系统中所有task
    public List<TaskVO> getTask(){
        return taskRepository.findAll();
    }

    //返回状态为ongoing的task记录总数
    public int getTaskOngoingNum(){
        int count = 0;
        List<TaskVO> list = taskRepository.findAll();
        for(TaskVO task:list){
            if(task.getProcess()==0){
                count++;
            }
        }
        return count;
    }

    //返回状态为finished的task记录总数
    public int getTaskFinishedNum(){
        int count = 0;
        List<TaskVO> list = taskRepository.findAll();
        for(TaskVO task:list){
            if(task.getProcess()==1){
                count++;
            }
        }
        return count;
    }
}
