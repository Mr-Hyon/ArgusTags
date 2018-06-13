package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;

import java.util.ArrayList;

public interface AdminService {

    public ResultMessage login(String adminname, String password);

    public int getInitiatorNum();

    public int getWorkerNum();

    public int getTaskNum();

    public ArrayList<TaskVO> getTask();

    public int getTaskOngoingNum();

    public int getTaskFinishedNum();
}