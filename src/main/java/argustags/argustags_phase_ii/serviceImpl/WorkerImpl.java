package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.repository.WorkerRepository;

import argustags.argustags_phase_ii.service.AdminService;

import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.RegisterLogin;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.*;
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
    //	实现注册功能，在workerList文件中写入用户名和密码，并创建worker目录

    @Override
    public List<WorkerVO> getAllUserlist(){
        return workerRepository.findAll();
    }

    @Override
    public void register(String username, String password){
      WorkerVO worker = new WorkerVO();
      ArrayList<String> temp = new ArrayList();
      temp.add("");
      worker.setUsername(username);
      worker.setPassword(password);
      worker.setCredit(100);
      worker.setTaskList(temp);
      System.out.println(worker.getUsername());
      System.out.println(worker.getPassword());
      System.out.println(worker.getCredit());
      System.out.println("imhere");
      workerRepository.saveAndFlush(worker);

    }



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

    //  实现登录功能，读取workerList文件，将用户名和密码分别存在两个ArrayList中，并判断输入用户名和密码是否存在且对应




    //删除原有worker信息，新增目前worker信息
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
        return ResultMessage.SUCCESS;
    }

    //更新该task的状态信息为finished（只要有一个工人提交，任务状态就会更改）
    public ResultMessage submitTask(String taskID){
        TaskService ts = new TaskImpl();
        TaskVO vo = ts.getByID(taskID);
        vo.setStatus("finished");
        return ts.updateTask(vo);
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
        AdminService as = new AdminImpl();
        WorkerService ws = new WorkerImpl();

        ArrayList<TaskVO> list1 = as.getTask();
        ArrayList<TaskVO> list2 = ws.getTask(workerName);

        for(TaskVO workerTask : list2){
            for(TaskVO task : list1){
                if(workerTask.getID().equals(task.getID())){
                    list1.remove(task);
                    break;
                }
            }
        }
        return list1;
    }
}
