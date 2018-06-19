package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public interface InitiatorService {

//    public List<InitiatorVO> getAllUserlist();

    public ResultMessage register(String username, String password);

    public ResultMessage login(String username, String password);

    public ResultMessage update(InitiatorVO vo);

    public String getTaskProgress(int taskID);

    public int getTaskNum(String initName);

    public int getTaskOngoingNum(String initName);

    public int getTaskFinishedNum(String initName);

    public ArrayList<TaskVO> getTask(String initName);

    public int getCredit(String initName);

    public ResultMessage updateCredit(int credit,String initName);

    public InitiatorVO getByName(String name);
}
