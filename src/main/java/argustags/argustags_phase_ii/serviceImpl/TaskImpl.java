package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static argustags.argustags_phase_ii.util.UnZip.unZipFiles;

public class TaskImpl implements TaskService {

    public ResultMessage addTask(TaskVO vo) {
        InitiatorService is = new InitiatorImpl();
        if (is.getByName(vo.getInitName()).getCredit() < 0) {
            return ResultMessage.CREDITINSUFFICIENT;
        }
        File wf = new File("Task\\" + vo.getID());
        if (!wf.exists()) {
            wf.mkdir();
        }
        String infoPath = "Task\\" + vo.getID() + "\\" + "FundInfo";
        String workerPath = "Task\\" + vo.getID() + "\\" + "WorkerList";
        String imagePath = "Task\\" + vo.getID() + "\\" + "ImageList";
        FileOpe fo = new FileOpe();

        fo.write("taskList",vo.getID()+"\n");
        fo.write(infoPath,vo.getID()+"\n"+vo.getName()+"\n"+vo.getInitName()+"\n"+vo.getType()+"\n"+vo.getStatus()+"\n"+vo.getStartTime()+"\n"+vo.getEndTime()+"\n"+vo.getDescribe()+"\n"+vo.getObeject()+"\n"+vo.getCut()+"\n"+vo.getReward()+"\n"+vo.getWorkernum()+"\n");
        for(String worker:vo.getWorkers()){
            fo.write(workerPath,worker+"\n");

        }
        for (String img : vo.getImgList()) {
            fo.write(imagePath, img + "\n");
        }
        String initPath = "Initiator\\" + vo.getInitName() + "\\TaskList";
        fo.write(initPath, vo.getID() + "\n");
        return ResultMessage.SUCCESS;
    }

    public ResultMessage delTask(String id) {
        String path = "Task\\" + id;
        File f = new File(path);
        File f1 = new File(path + "\\FundInfo");
        File f2 = new File(path + "\\WorkerList");
        File f3 = new File(path + "\\ImageList");
        f1.delete();
        f2.delete();
        f3.delete();
        f.delete();
        return ResultMessage.SUCCESS;
    }

    public ResultMessage updateTask(TaskVO vo) {
        TaskService ts = new TaskImpl();
        ts.delTask(vo.getID());
        return ts.addTask(vo);
    }

    public TaskVO getByID(String taskID) {
        String infoPath = "Task\\" + taskID + "\\" + "FundInfo";
        String workerPath = "Task\\" + taskID + "\\" + "WorkerList";
        String imagePath = "Task\\" + taskID + "\\" + "ImageList";
        try {
            FileReader fr = new FileReader(infoPath);
            BufferedReader br = new BufferedReader(fr);
            String ID = br.readLine();
            String name = br.readLine();
            String initName = br.readLine();
            String type = br.readLine();
            String status = br.readLine();
            String startTime = br.readLine();
            String endTime = br.readLine();
            String describe = br.readLine();
            String object = br.readLine();
            double cut = Double.parseDouble(br.readLine());
            int reward = Integer.parseInt(br.readLine());
            int workernum = Integer.parseInt(br.readLine());
            FileOpe fo = new FileOpe();
            ArrayList<String> workers = fo.getLine(workerPath);
            ArrayList<String> imgList = fo.getLine(imagePath);

            TaskVO vo = new TaskVO(ID,name,initName,imgList,type,status,startTime,endTime,describe,object,cut,reward,workernum);

            vo.setWorkers(workers);
            vo.setEndTime(endTime);
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