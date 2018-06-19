package argustags.argustags_phase_ii.repository;

import argustags.argustags_phase_ii.vo.WorkerVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkerRepository extends JpaRepository<WorkerVO,Integer> {
      List<WorkerVO>findByUsername(String username);
}
