package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.*;
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
    @Autowired
    private ImageRepository imageRepository;


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

    // 返回image总数
    public int getImageNum(){
        List<Image> list = imageRepository.findAll();
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
        List<Tag> list = new ArrayList<>();
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

    public List<Tag> getFrames(int taskid, int imgid){
        TaskVO vo = taskService.getByID(taskid);
        List<String> workers = vo.getWorkers();
        List<Tag> options = taskService.getTagbyWnT(workers.get(0),imgid);
        int count;
        List<Tag> tags;
        for(Tag t : options) {
            count = 0;
            for (int i = 1; i < workers.size(); i++) {
                tags = taskService.getTagbyWnT(workers.get(i), imgid);
                for (Tag t1 : tags) {
                    if ((Math.abs(t.getMiddle()[0] - t1.getMiddle()[0]) <= 5) && (Math.abs(t.getMiddle()[1] - t1.getMiddle()[1]) <= 5)) {
                        count++;
                        break;
                    }
                }
                if(count>=5) break;
            }
            if(count<5){
                options.remove(t);
            }
        }
        return options;
    }

    public List<Tag> getFrameAndAnswer(int taskid, int imgid){
        TaskVO vo = taskService.getByID(taskid);
        List<String> workers = vo.getWorkers();
        List<Tag> position = adminService.getFrames(taskid, imgid);

        String str;
        String temp;
        List<Tag> tags;
        List<String> contents;
        int count;
        int maximum;
        String answerTag;

        for(Tag t : position){
            str = "";
            maximum = 0;
            answerTag = "";
            contents = new ArrayList<>();
            for (int i = 0; i < workers.size(); i++) {
                tags = taskService.getTagbyWnT(workers.get(i), imgid);
                for (Tag t1 : tags) {
                    if ((Math.abs(t.getMiddle()[0] - t1.getMiddle()[0]) <= 5) && (Math.abs(t.getMiddle()[1] - t1.getMiddle()[1]) <= 5)){
                        str += t1.getTag();
                        contents.add(t1.getTag());
                        break;
                    }
                }
            }
            for (int i = 0; i < contents.size(); i++) {
                temp = str.replaceAll(contents.get(i), "");
                count = (str.length() - temp.length()) / contents.get(i).length();
                if (count > maximum) {
                    maximum = count;
                    answerTag = contents.get(i);
                }
            }
            t.setTag(answerTag);
        }

        return position;
    }

    public ResultMessage rewardAndPunish(List<String> workers,List<Integer> numOfTrueTags,int total){
        double percent = 0;
        int credit = 0;
        System.out.println(workers.get(0)+"ygu");
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

    public ResultMessage rewardAndPunish0(int taskid){
        TaskVO vo = taskRepository.findById(taskid).get();
        ArrayList<Integer> imgs = vo.getImgList();
        ArrayList<String> workers = vo.getWorkers();
        ArrayList<Integer> numOfTrueTags = new ArrayList<>();
        for(String name : workers){
            numOfTrueTags.add(0);
        }
        int total = imgs.size();
        String answer = "";
        Image img = null;
        List<Tag> tags = new ArrayList<>();
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
                            break;
                        }
                    }
                }
            }
        }
        return adminService.rewardAndPunish(workers,numOfTrueTags,total);
    }

    public ResultMessage rewardAndPunish12(int taskid){
        TaskVO vo = taskRepository.findById(taskid).get();
        ArrayList<Integer> imgs = vo.getImgList();
        ArrayList<String> workers = vo.getWorkers();
        ArrayList<Integer> numOfTrueTags = new ArrayList<>();
        for(String name : workers){
            numOfTrueTags.add(0);
        }
        int total = imgs.size();
        List<Tag> answerTag;
        List<Tag> tags;
        int numOfTags;
        int sign;
        String name;
        for(int imgid : imgs){
            answerTag = adminService.getFrameAndAnswer(taskid, imgid);
            numOfTags = answerTag.size();
            for(int i = 0;i<workers.size();i++) {
                name = workers.get(i);
                tags = taskService.getTagbyWnT(name,imgid);
                sign = 0;
                for(Tag t : answerTag){
                    for(Tag t1 : tags){
                        if((Math.abs(t.getMiddle()[0]-t1.getMiddle()[0])<=5)&&(Math.abs(t.getMiddle()[1]-t1.getMiddle()[1])<=5)&&t.getTag().equals(t1.getTag())){
                            sign++;
                            break;
                        }
                    }
                }
                if(sign == numOfTags) numOfTrueTags.set(i,numOfTrueTags.get(i)+1);
            }
        }
        return adminService.rewardAndPunish(workers,numOfTrueTags,total);
    }

}
