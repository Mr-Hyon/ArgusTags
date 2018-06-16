package argustags.argustags_phase_ii.repository;

import argustags.argustags_phase_ii.vo.TaskVO;
import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskVO,Integer> {

}
