package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.FileOpe;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static argustags.argustags_phase_ii.util.UnZip.unZipFiles;
@RestController
public class TaskImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
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

        fo.createFile(infoPath);
        fo.createFile(workerPath);
        fo.createFile(imagePath);

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
        String path = "Task" + File.separator + id;
        File f = new File(path);
        File f1 = new File(path + File.separator + "FundInfo");
        File f2 = new File(path + File.separator + "WorkerList");
        File f3 = new File(path + File.separator + "ImageList");
        f1.delete();
        f2.delete();
        f3.delete();
        f.delete();
        if(f.exists()){
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    public ResultMessage updateTask(TaskVO vo) {
        TaskService ts = new TaskImpl();
        ResultMessage rm = ts.delTask(vo.getID());
        if(rm==ResultMessage.FAILED){
            return rm;
        }
        return ts.addTask(vo);
    }

    public TaskVO getByID(int taskID) {
        return taskRepository.findById(taskID).get();
    }


}