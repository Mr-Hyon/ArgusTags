package argustags.argustags_phase_ii.serviceImpl;

import argustags.argustags_phase_ii.repository.ImageRepository;
import argustags.argustags_phase_ii.repository.TagRepository;
import argustags.argustags_phase_ii.repository.TaskRepository;
import argustags.argustags_phase_ii.service.InitiatorService;
import argustags.argustags_phase_ii.service.TaskService;
import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
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

    public ResultMessage addTask(TaskVO vo) {
        taskRepository.saveAndFlush(vo);
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
    //根据图片id获取base64
    public String getBase64(int imgid){
        Image image = imageRepository.findById(imgid).get();
        return image.getBase64();
    }
    //根据工人用户名与图片编号获取tag
    public List<Tag> getTagbyWnT(String workerName,int imgid){
        Image image = imageRepository.findById(imgid).get();
        List<Tag> tags = image.getTags();
        List<Tag> res = new ArrayList<Tag>();
        for(Tag tag :tags){
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

    public Image findImageById(long id){
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

    public ResultMessage updateimage(Image image){
        imageRepository.saveAndFlush(image);
        return ResultMessage.SUCCESS;
    }


}