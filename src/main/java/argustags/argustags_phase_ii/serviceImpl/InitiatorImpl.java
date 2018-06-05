package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.RegisterLogin;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;

import java.io.*;
import java.util.ArrayList;

public class InitiatorImpl implements InitiatorService {

    //	实现注册功能，在initiatorList文件中写入用户名和密码，并创建initiator和task目录
    public ResultMessage register(String username, String password){
        File inif = new File("Initiator");
        if(!inif.exists()){
            inif.mkdir();
        }
        File taskf = new File("Task");
        if(!taskf.exists()) {
            taskf.mkdir();
        }
        InitiatorService is = new InitiatorImpl();
        ArrayList<String> taskList = new ArrayList<>();
        InitiatorVO vo = new InitiatorVO(username,password,taskList,10240);
        is.add(vo);
        String initiatorList = "initiatorList";
        RegisterLogin rl = new RegisterLogin();
        return rl.register(initiatorList,username,password);
    }

    //  实现登录功能，读取initiatorList文件，将用户名和密码分别存在两个ArrayList中，并判断输入用户名和密码是否存在且对应
    public ResultMessage login(String username, String password){
        String initiatorList = "initiatorList";
        RegisterLogin rl = new RegisterLogin();
        return rl.login(initiatorList,username,password);
    }

    //新增一条initiator信息
    public ResultMessage add(InitiatorVO vo){
        File wf = new File("Initiator\\"+vo.getUsername());
        if(!wf.exists()){
            wf.mkdir();
        }
        String infoPath = "Initiator\\"+vo.getUsername()+"\\"+"FundInfo";
        String taskPath = "Initiator\\"+vo.getUsername()+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        fo.write(infoPath,vo.getUsername()+"\n"+vo.getPassword()+"\n"+vo.getCredit()+"\n");
        fo.write(taskPath,"");
        for(String task:vo.getTaskList()){
            fo.write(taskPath,task+"\n");
        }
        return ResultMessage.SUCCESS;
    }

    //删除原有initiator信息，新增目前initiator信息
    public ResultMessage update(InitiatorVO vo){
        String infoPath = "Initiator\\"+vo.getUsername()+"\\"+"FundInfo";
        String taskPath = "Initiator\\"+vo.getUsername()+"\\"+"TaskList";
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

    //得到该task的状态信息
    public String getTaskProgress(String taskID){
        TaskService ts = new TaskImpl();
        return ts.getByID(taskID).getStatus();
    }

    //得到该initiator的task总数
    public int getTaskNum(String initName){
        String path = "Initiator\\"+initName+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        return fo.getLineNum(path);
    }

    //得到该initiator对应的状态为ongoing的task总数
    public int getTaskOngoingNum(String initName){
        String path = "Initiator\\"+initName+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        return fo.getStatusNum(path,"ongoing");
    }

    //得到该initiator对应的状态为finished的task总数
    public int getTaskFinishedNum(String initName){
        String path = "Initiator\\"+initName+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        return fo.getStatusNum(path,"finished");
    }

    //得到该initiator的所有task
    public ArrayList<TaskVO> getTask(String initName){
        String path = "Initiator\\"+initName+"\\"+"TaskList";
        FileOpe fo = new FileOpe();
        ArrayList<String> List1 = fo.getLine(path);
        ArrayList<TaskVO> List2 = new ArrayList<>();
        TaskVO vo;
        TaskService ts = new TaskImpl();
        for(String taskID : List1){
            vo = ts.getByID(taskID);
            List2.add(vo);
        }
        return List2;
    }

    //得到对应initiator的剩余credit信息
    public int getRestCredit(String initName){
        String path = "Initiator\\"+initName+"\\"+"FundInfo";
        FileOpe fo = new FileOpe();
        return Integer.parseInt(fo.getThirdLine(path));
    }

    //得到对应initiator花费的credit信息
    public int getSpentCredit(String initName){
        InitiatorService is = new InitiatorImpl();
        return 10240 - is.getRestCredit(initName);
    }

    //更新对应Initiator的credit信息
    public ResultMessage updateCredit(int credit,String initName){
        String path = "Initiator\\"+initName+"\\"+"FundInfo";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            InitiatorVO vo = new InitiatorVO();
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

    //根据名称查找对应initiator
    public InitiatorVO getByName(String name){
        String infoPath = "Initiator\\"+name+"\\"+"FundInfo";
        String taskPath = "Initiator\\"+name+"\\"+"TaskList";
        try {
            FileReader fr = new FileReader(infoPath);
            BufferedReader br = new BufferedReader(fr);
            String username = br.readLine();
            String password = br.readLine();
            int credit = Integer.parseInt(br.readLine());
            FileOpe fo = new FileOpe();
            ArrayList<String> taskList = fo.getLine(taskPath);
            InitiatorVO vo = new InitiatorVO(username,password,taskList,credit);
            return vo;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
