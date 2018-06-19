package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.InitiatorRepository;
import argustags.argustags_phase_ii.repository.TagRepository;
import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.repository.WorkerRepository;
import argustags.argustags_phase_ii.service.AdminService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.service.WorkerService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private AdminService adminService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private TagRepository tagRepository;


    //  实现登录功能，判断admin用户名密码输入是否正确
    public ResultMessage login(String adminname, String password){
        if(adminname.equals("admin")&&password.equals("123456")){
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAILED;
    }

    // 返回tag总数
    public int getTagNum(){
        List<Tag> list = tagRepository.findAll();
        return list.size();
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
        for(TaskVO task : list){
            if(task.getProcess()>= (task.getImgList().size())*10){
                ;
            }
            else{
                count++;
            }
        }
        return count;
    }

    //返回状态为finished的task记录总数
    public int getTaskFinishedNum(){
        int count = 0;
        List<TaskVO> list = taskRepository.findAll();
        for(TaskVO task : list){
            if(task.getProcess()>= (task.getImgList().size())*10){
                count++;
            }
        }
        return count;
    }

    public String getAnswer(int imgid) {
        Image img = taskService.findImageById(imgid);
        List<Integer> tags = img.getTags();
        List<Tag> list = new ArrayList<Tag>();
        for(Integer t :tags){
            list.add(taskService.getTagById(t));
        }

        String str = "";
        int count = 0;
        int maximum = 0;
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            str += list.get(i).getTag();
        }
        for (int i = 0; i < list.size(); i++) {
            String temp = str.replaceAll(list.get(i).getTag(), "");
            count = (str.length() - temp.length()) / list.get(i).getTag().length();
            if (count > maximum) {
                maximum = count;
                result = list.get(i).getTag();
            }
        }
        return result;
    }

    public ResultMessage rewardAndPunish0(int taskid){
        TaskVO vo = taskRepository.findById(taskid).get();
        ArrayList<Integer> imgs = vo.getImgList();
        ArrayList<String> workers = vo.getWorkers();
        ArrayList<Integer> numOfTrueTags = new ArrayList();
        for(String name : workers){
            numOfTrueTags.add(0);
        }
        int total = imgs.size();
        double percent = 0;
        int credit = 0;
        String answer = "";
        Image img = null;
        List<Tag> tags = new ArrayList();
        for(int imgid : imgs){
            img = taskService.findImageById(imgid);
            answer = adminService.getAnswer(imgid);
            List<Integer> list = img.getTags();
            for(Integer t :list){
                tags.add(taskService.getTagById(t));
            }
            for(Tag t : tags){
                if(t.getTag().equals(answer)){
                    for(int i = 0; i<workers.size(); i++){
                        if(workers.get(i).equals(t.getWorkerName())){
                            numOfTrueTags.set(i,numOfTrueTags.get(i)+1);
                        }
                    }
                }
            }
        }
        for(int i = 0; i<workers.size(); i++){
            credit = workerService.getCredit(workers.get(i));
            percent = (double)numOfTrueTags.get(i)/total;
            if(percent >= 0.95){
                workerService.updateCredit(credit+total,workers.get(i));
            }
            else if(percent >= 0.75){
                workerService.updateCredit(credit+numOfTrueTags.get(i),workers.get(i));
            }
            else if(percent < 0.5){
                workerService.updateCredit(credit+2*numOfTrueTags.get(i)-total,workers.get(i));
            }
        }
        return ResultMessage.SUCCESS;
    }
}
