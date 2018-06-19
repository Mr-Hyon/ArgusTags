package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.InitiatorRepository;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InitiatorImpl implements InitiatorService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private InitiatorRepository initiatorRepository;
    @Autowired
    private AdminService adminService;



    @Override
    public List<InitiatorVO> getAllUserlist(){
        return initiatorRepository.findAll();
    }

    //	实现注册功能
    @Override
    public ResultMessage register(String username, String password){
//        if(initiatorRepository.findAll()!=null) {
//            List<InitiatorVO> list1 = initiatorRepository.findAll();
//
//            int flag = 0;
//            for (InitiatorVO ini : list1) {
//                if (ini.getUsername() == username) {
//                    flag = flag + 1;
//                    break;
//                } else {
//                    ;
//                }
//            }
//            if (flag == 1) {
//                return ResultMessage.REPEATEDNAME;
//            } else {
                InitiatorVO initiator = new InitiatorVO();
                ArrayList<Integer> li = new ArrayList<>();
                initiator.setUsername(username);
                initiator.setPassword(password);
                initiator.setCredit(10240);
                initiator.setTaskList(li);
                initiatorRepository.saveAndFlush(initiator);
                return ResultMessage.SUCCESS;
//            }
//        }
//        else{
//            InitiatorVO initiator = new InitiatorVO();
//            initiator.setUsername(username);
//            initiator.setPassword(password);
//            initiator.setCredit(10240);
//            initiatorRepository.saveAndFlush(initiator);
//            return ResultMessage.SUCCESS;
//        }
    }

    //  实现登录功能
    public ResultMessage login(String username, String password){

        InitiatorVO initiator1 = getByName(username);
        if((username .equals(initiator1.getUsername()))&&(password.equals(initiator1.getPassword()) )){
            return ResultMessage.SUCCESS;
        }
        else{
            return ResultMessage.FAILED;
        }

    }


    //更新initiator信息
    @Override
    public ResultMessage update(InitiatorVO vo){
        initiatorRepository.saveAndFlush(vo);
        return ResultMessage.SUCCESS;
    }

    //得到该task的状态信息
    @Override
    public String getTaskProgress(int taskID){
        TaskVO task = taskService.getByID(taskID);
        int picnum = task.getImgList().size();

        int temp = task.getProcess();
        return ((100*temp)/picnum*10)+"%";
    }

    //得到该initiator的task总数
    @Override
    public int getTaskNum(String initName){
        InitiatorVO initiator = getByName(initName);
        int tasknum = initiator.getTaskList().size();
        return tasknum;
    }

    //得到该initiator对应的状态为ongoing的task总数
    @Override
    public int getTaskOngoingNum(String initName){
        ArrayList<TaskVO> allTask = getTask(initName);
        int count = 0;
        for(TaskVO task : allTask){
            if(task.getProcess()>= (task.getImgList().size())*10){
                ;
            }
            else{
                count++;
            }
        }
        return count;
    }

    //得到该initiator对应的状态为finished的task总数
    @Override
    public int getTaskFinishedNum(String initName){
        ArrayList<TaskVO> allTask = getTask(initName);
        int count = 0;
        for(TaskVO task : allTask){
            if(task.getProcess()>= (task.getImgList().size())*10){
        count++;
    }
            else{
        ;
    }
}
        return count;
    }

    //得到该initiator的所有task
    @Override
    public ArrayList<TaskVO> getTask(String initName){
        InitiatorVO initiator = getByName(initName);
        ArrayList<Integer> List1 = initiator.getTaskList();
        ArrayList<TaskVO> List2 = new ArrayList<>();
        TaskVO vo;
        for(int taskID : List1){
            vo = taskService.getByID(taskID);
            List2.add(vo);
        }
        return List2;
    }

    //得到对应initiator的credit信息
    @Override
    public int getCredit(String initName){
        InitiatorVO initiator1=getByName(initName);
        return initiator1.getCredit();
    }


    //更新对应Initiator的credit信息
    @Override
    public ResultMessage updateCredit(int credit,String initName){
        InitiatorVO initiator1=getByName(initName);
        initiator1.setCredit(credit);
        return ResultMessage.SUCCESS;
    }

    //根据名称查找对应initiator

    public InitiatorVO getByName(String name){
        List<InitiatorVO> li = initiatorRepository.findAll();
        InitiatorVO res=null;
        for(InitiatorVO initiator:li){
            if(initiator.getUsername().equals(name)){
                res=initiator;
                break;
            }
        }
        return res;
    }
}
