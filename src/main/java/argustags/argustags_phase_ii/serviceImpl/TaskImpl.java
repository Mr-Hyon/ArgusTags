package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.ImageRepository;
import argustags.argustags_phase_ii.repository.InitiatorRepository;
import argustags.argustags_phase_ii.repository.TagRepository;
import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.Tag;
import argustags.argustags_phase_ii.vo.TaskVO;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static argustags.argustags_phase_ii.util.UnZip.unZipFiles;
@RestController
public class TaskImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private InitiatorService initiatorService;
    @Autowired
    private InitiatorRepository initiatorRepository;

    public ResultMessage addTask(TaskVO vo,String ininame) {
        taskRepository.saveAndFlush(vo);
        InitiatorVO ini = initiatorService.getByName(ininame);
        ArrayList<Integer> lis1 = ini.getTaskList();
        lis1.add(vo.getID());
        ini.setTaskList(lis1);
        initiatorRepository.saveAndFlush(ini);

        return ResultMessage.SUCCESS;
    }

    public ResultMessage delTask(int id) {
        TaskVO task = taskRepository.findById(id).get();
        taskRepository.delete(task);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage updateTask(TaskVO vo) {
        taskRepository.saveAndFlush(vo);
        return ResultMessage.SUCCESS;
    }
    //根据id获取task
    public TaskVO getByID(int taskID) {
        return taskRepository.findById(taskID).get();
    }
    //根据taskid获取图片id列表
    public ArrayList<Integer> getImageList(int taskId){
        TaskVO task = taskRepository.findById(taskId).get();
        return task.getImgList();
    }
    //根据type获取task
    public List<TaskVO> getByType(String type) {
        List<TaskVO> lis1 = taskRepository.findAll();
        List<TaskVO> lis2 = new ArrayList<TaskVO>();
        for(TaskVO task:lis1){
            if(task.getType().equals(type)){
                lis2.add(task);
            }
        }
        return lis2;
    }
    //根据图片id获取base64
    public String getBase64(int imgid){
        Image image = imageRepository.findById(imgid).get();
        return image.getBase64();
    }
    //根据工人用户名与图片编号获取tag
    public List<Tag> getTagbyWnT(String workerName,int imgid){
        Image image = imageRepository.findById(imgid).get();
        List<Integer> tags = image.getTags();
        List<Tag> temp = new ArrayList<Tag>();
        List<Tag> res = new ArrayList<Tag>();
        for(Integer t :tags){
            temp.add(getTagById(t));
        }
        for(Tag tag:temp){
            if(tag.getWorkerName().equals(workerName)){
                res.add(tag);
            }
            else{
                ;
            }
        }
        return res;
    }

    public ResultMessage modifyTag(Tag tag){
        tagRepository.saveAndFlush(tag);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage addTag(Tag tag){
        tagRepository.saveAndFlush(tag);
        return ResultMessage.SUCCESS;
    }
    //删除某一项标注
    public ResultMessage deleteTag(Tag tag){
        tagRepository.delete(tag);
        return ResultMessage.SUCCESS;
    }

    public ResultMessage addimage(Image image){
        imageRepository.saveAndFlush(image);
        return ResultMessage.SUCCESS;
    }

    public Image findImageById(int id){
        List<Image> image = imageRepository.findAll();
        Image img = null;
        for(Image i:image){
            if(i.getId()==id){
                img = i;
                break;
            }
        }
        return img;
    }

    public Tag getTagById(int id){
        List<Tag> tags = tagRepository.findAll();
        Tag tag = null;
        for(Tag t:tags){
            if(t.getId()==id){
                tag = t;
                break;
            }
        }
        return tag;
    }

    public ResultMessage updateimage(Image image){
        imageRepository.saveAndFlush(image);
        return ResultMessage.SUCCESS;
    }


}