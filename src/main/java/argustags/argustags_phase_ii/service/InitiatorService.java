package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.InitiatorVO;
import argustags.argustags_phase_ii.vo.TaskVO;

import java.util.ArrayList;

public interface InitiatorService {

    public ResultMessage register(String username, String password);

    public ResultMessage login(String username, String password);

    public ResultMessage add(InitiatorVO vo);

    public ResultMessage update(InitiatorVO vo);

    public String getTaskProgress(String taskID);

    public int getTaskNum(String initName);

    public int getTaskOngoingNum(String initName);

    public int getTaskFinishedNum(String initName);

    public ArrayList<TaskVO> getTask(String initName);

    public int getRestCredit(String initName);

    public int getSpentCredit(String initName);

    public ResultMessage updateCredit(int credit,String initName);

    public InitiatorVO getByName(String name);
}
