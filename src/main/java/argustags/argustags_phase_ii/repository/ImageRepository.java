package argustags.argustags_phase_ii.repository;

import argustags.argustags_phase_ii.vo.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
}
