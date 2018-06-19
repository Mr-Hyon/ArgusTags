package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.Image;
import argustags.argustags_phase_ii.vo.Tag;
import argustags.argustags_phase_ii.vo.TaskVO;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public interface AdminService {

    public ResultMessage login(String adminname, String password);

    public int getTagNum();

    public int getInitiatorNum();

    public int getWorkerNum();

    public int getTaskNum();

    public List<TaskVO> getTask();

    public int getTaskOngoingNum();

    public int getTaskFinishedNum();

    public String getAnswer(int imgid);

    public ResultMessage rewardAndPunish0(int taskid);
}
