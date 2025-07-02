package miniprojectver.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long>{
    Optional<Point> findByUserId(String userId);
}
