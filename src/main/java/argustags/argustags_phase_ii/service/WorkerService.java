package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;

import java.util.ArrayList;
import java.util.List;

public interface WorkerService {

    public List<WorkerVO> getAllUserlist();

    public  ResultMessage register(String username, String password);

    public ResultMessage login(String username, String password);

    public ResultMessage update(WorkerVO vo);

    public ResultMessage acceptTask(int id,String workerName);

    public ArrayList<TaskVO> getTask(String workerName);

    public int getCredit(String username);

   public ResultMessage updateCredit(int credit,String username);

    public ResultMessage submitTask(int taskID);

    public WorkerVO getByName(String name);

    public ArrayList<TaskVO> getFilteredTask(String workerName);
}
