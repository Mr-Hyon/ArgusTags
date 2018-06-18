package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.repository.WorkerRepository;

import argustags.argustags_phase_ii.service.AdminService;

import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkerImpl implements WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private WorkerService workerService;


    @Override
    public List<WorkerVO> getAllUserlist(){
        return workerRepository.findAll();
    }

    //	实现注册
    @Override
    public ResultMessage register(String username, String password){
        if(workerRepository.findAll()!=null) {
            List<WorkerVO> list1 = workerRepository.findAll();
            int flag = 0;
            for (WorkerVO ini : list1) {
                if (ini.getUsername() == username) {
                    flag = flag + 1;
                    break;
                } else {
                    ;
                }
            }
            if (flag == 1) {
                return ResultMessage.REPEATEDNAME;
            } else {
                WorkerVO worker = new WorkerVO();
                worker.setUsername(username);
                worker.setPassword(password);
                worker.setCredit(100);
                workerRepository.saveAndFlush(worker);
                return ResultMessage.SUCCESS;
            }
        }
        else{
            WorkerVO worker = new WorkerVO();
            worker.setUsername(username);
            worker.setPassword(password);
            worker.setCredit(100);
            workerRepository.saveAndFlush(worker);
            return ResultMessage.SUCCESS;
        }
    }


    //  实现登录功能
    @Override
    public ResultMessage login(String username, String password) {
        WorkerVO worker1 = getByName(username);
        if(username == worker1.getUsername()&&password == worker1.getPassword()){
            return ResultMessage.SUCCESS;
        }
        else{
            return ResultMessage.FAILED;
        }

    }






    //更新worker信息
    public ResultMessage update(WorkerVO vo){
        workerRepository.saveAndFlush(vo);
        return ResultMessage.SUCCESS;
    }

    //在对应worker的taskList中加入此任务，并向接受此任务的工人列表中添加该worker
    public ResultMessage acceptTask(int id,String workerName){
        TaskVO task =new TaskVO();
        task = taskService.getByID(id);
        task.addWorker(workerName);
        WorkerVO worker = getByName(workerName);
        worker.addTask(id);
        workerRepository.saveAndFlush(worker);
        taskRepository.saveAndFlush(task);
        return ResultMessage.SUCCESS;
    }

    //读取worker对应的taskList，并根据这些taskID找到对应TaskVO
    public ArrayList<TaskVO> getTask(String workerName){
        WorkerVO worker = getByName(workerName);
        ArrayList<Integer> List1 = worker.getTaskList();
        ArrayList<TaskVO> List2 = new ArrayList<>();
        TaskVO vo;
        for(int taskID : List1){
            vo = taskService.getByID(taskID);
            List2.add(vo);
        }
        return List2;
    }

    //得到对应worker的credit信息
    public int getCredit(String username){
        WorkerVO worker1=getByName(username);
        return worker1.getCredit();
    }

    //更新对应worker的credit信息
    public ResultMessage updateCredit(int credit,String username){
        WorkerVO worker1=getByName(username);
        worker1.setCredit(credit);
        workerRepository.saveAndFlush(worker1);
        return ResultMessage.SUCCESS;
    }

    //更新该task的状态信息（有一个工人提交，任务进度+1）
    public ResultMessage submitTask(int taskID){
        TaskVO task = taskService.getByID(taskID);
        int p = task.getProcess();
        task.setProcess(p+1);
        taskRepository.saveAndFlush(task);
        return ResultMessage.SUCCESS;
    }

    //根据名称查找对应worker
    public WorkerVO getByName(String username){
        List<WorkerVO>li = workerRepository.findAll();
        WorkerVO res=new WorkerVO();
        for(WorkerVO worker:li){
            if(worker.getUsername().equals(username)){
                res=worker;
                break;
            }
        }
        return res;
    }


    //根据名称筛选对应task（剔除该worker已接受的task）
    public ArrayList<TaskVO> getFilteredTask(String workerName){

        ArrayList<TaskVO> list1 = (ArrayList) adminService.getTask();
        ArrayList<TaskVO> list2 = workerService.getTask(workerName);

        for(TaskVO workerTask : list2){
            for(TaskVO task : list1){
                if(workerTask.getID()==task.getID()){
                    list1.remove(task);
                    break;
                }
            }
        }
        return list1;
    }
}
