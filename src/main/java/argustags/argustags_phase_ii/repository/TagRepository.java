package argustags.argustags_phase_ii.repository;

import argustags.argustags_phase_ii.vo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}
