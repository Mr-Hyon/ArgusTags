package argustags.argustags_phase_ii.serviceImpl;

<<<<<<< HEAD
import argustags.argustags_phase_ii.repository.WorkerRepository;
=======
import argustags.argustags_phase_ii.service.AdminService;
>>>>>>> 674116cbd2a890f8df0ee9a6efd3c81cc0785337
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.RegisterLogin;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;

public class WorkerImpl implements WorkerService {

    @Autowired
    private WorkerRepository workerRepository;
    //	实现注册功能，在workerList文件中写入用户名和密码，并创建worker目录
    public WorkerVO register(String username, String password){
      WorkerVO worker = new WorkerVO();
      worker.setUsername(username);
      worker.setPassword(password);
      worker.setCredit(100);
      return workerRepository.save(worker);
    }

    //  实现登录功能，读取workerList文件，将用户名和密码分别存在两个ArrayList中，并判断输入用户名和密码是否存在且对应
    public ResultMessage login(String username, String password){
        String workerList = "workerList";
        RegisterLogin rl = new RegisterLogin();
        return rl.login(workerList,username,password);
    }

    //新增一条worker信息
    public ResultMessage add(WorkerVO vo){
        File wf = new File("Worker\\"+vo.getUsername());
        if(!wf.exists()){
            wf.mkdir();
        }
        String infoPath = "Worker\\"+vo.getUsername()+"\\"+"FundInfo";
        String taskPath = "Worker\\"+vo.getUsername()+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        fo.write(infoPath,vo.getUsername()+"\n"+vo.getPassword()+"\n"+vo.getCredit()+"\n");
        fo.write(taskPath,"");
        for(String task:vo.getTaskList()){
            fo.write(taskPath,task+"\n");
        }
        return ResultMessage.SUCCESS;
    }

    //删除原有worker信息，新增目前worker信息
    public ResultMessage update(WorkerVO vo){
        String infoPath = "Worker\\"+vo.getUsername()+"\\"+"FundInfo";
        String taskPath = "Worker\\"+vo.getUsername()+"\\"+"TaskList";
        File f1 = new File(infoPath);
        File f2 = new File(taskPath);
        f1.delete();
        f2.delete();
        FileOpe fo = new FileOpe();
        fo.write(infoPath,vo.getUsername()+"\n"+vo.getPassword()+"\n"+vo.getCredit()+"\n");
        for(String task:vo.getTaskList()){
            fo.write(taskPath,task+"\n");
        }
        return ResultMessage.SUCCESS;
    }

    //在对应worker的taskList中加入此任务，并向接受此任务的工人列表中添加该worker
    public ResultMessage acceptTask(String id,String workerName){
        TaskService ts = new TaskImpl();
        //if((ts.getByID(id).getWorkers().size())>=(ts.getByID(id).getWorkernum())){
        ////    return ResultMessage.LIMITEDWORKERS;
       // }
        String taskPath = "Worker\\"+workerName+"\\"+"TaskList";
        String workerPath = "Task\\"+id+"\\WorkerList";
        FileOpe fo = new FileOpe();
        fo.write(taskPath,id+"\n");
        fo.write(workerPath,workerName+"\n");
        return ResultMessage.SUCCESS;
    }

    //读取worker对应的taskList，并根据这些taskID找到对应TaskVO
    public ArrayList<TaskVO> getTask(String workerName){
        String taskPath = "Worker\\"+workerName+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        ArrayList<String> List1 = fo.getLine(taskPath);
        ArrayList<TaskVO> List2 = new ArrayList<>();
        TaskVO vo;
        TaskService ts = new TaskImpl();
        for(String taskID : List1){
            vo = ts.getByID(taskID);
            List2.add(vo);
        }
        return List2;
    }

    //得到对应worker的credit信息
    public int getCredit(String username){
        String path = "Worker\\"+username+"\\"+"FundInfo";
        FileOpe fo = new FileOpe();
        return Integer.parseInt(fo.getThirdLine(path));
    }

    //更新对应worker的credit信息
    public ResultMessage updateCredit(int credit,String username){
        String path = "Worker\\"+username+"\\"+"FundInfo";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            WorkerVO vo = new WorkerVO();
            vo.setUsername(br.readLine());
            vo.setPassword(br.readLine());
            vo.setCredit(credit);
            br.close();
            File f = new File(path);
            f.delete();
            FileOpe fo = new FileOpe();
            fo.write(path,vo.getUsername()+"\n"+vo.getPassword()+"\n"+vo.getCredit()+"\n");
            return ResultMessage.SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
    }

    //更新该task的状态信息为finished（只要有一个工人提交，任务状态就会更改）
    public ResultMessage submitTask(String taskID){
        TaskService ts = new TaskImpl();
        TaskVO vo = ts.getByID(taskID);
        vo.setStatus("finished");
        return ts.updateTask(vo);
    }

    //根据名称查找对应worker
    public WorkerVO getByName(String name){
        String infoPath = "Worker\\"+name+"\\"+"FundInfo";
        String taskPath = "Worker\\"+name+"\\"+"TaskList";
        try {
            FileReader fr = new FileReader(infoPath);
            BufferedReader br = new BufferedReader(fr);
            String username = br.readLine();
            String password = br.readLine();
            int credit = Integer.parseInt(br.readLine());
            FileOpe fo = new FileOpe();
            ArrayList<String> taskList = fo.getLine(taskPath);
            WorkerVO vo = new WorkerVO(username,password,taskList,credit);
            return vo;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
