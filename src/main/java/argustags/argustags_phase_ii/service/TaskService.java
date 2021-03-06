package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
import argustags.argustags_phase_ii.vo.Tag;
import argustags.argustags_phase_ii.vo.TaskVO;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public interface TaskService {

    ResultMessage addTask(TaskVO vo,String ininame);

    public ResultMessage delTask(int id);

    public ResultMessage updateTask(TaskVO vo);

    public TaskVO getByID(int taskID);

    public ArrayList<Integer> getImageList(int taskId);

    public String getBase64(int imgid);

    public List<Tag> getTagbyWnT(String workerName, int imgid);

    public ResultMessage modifyTag(Tag tag);

    public ResultMessage addTag(Tag tag);

    public ResultMessage deleteTag(Tag tag);

    public ResultMessage addimage(Image image);

    public Image findImageById(int id);

    public Tag getTagById(int id);

    public ResultMessage updateimage(Image image);

    public List<TaskVO> getByType(String type);
}
