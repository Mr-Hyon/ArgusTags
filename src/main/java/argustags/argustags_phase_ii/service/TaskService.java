package argustags.argustags_phase_ii.service;

import argustags.argustags_phase_ii.util.ResultMessage;
import argustags.argustags_phase_ii.vo.TaskVO;

public interface TaskService {

    public ResultMessage addTask(TaskVO vo);

    public ResultMessage delTask(String id);

    public ResultMessage updateTask(TaskVO vo);

    public TaskVO getByID(int taskID);
    
}
