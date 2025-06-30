package miniprojectver.infra;

import lombok.RequiredArgsConstructor;
import miniprojectver.domain.Point;
import miniprojectver.domain.PointRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointRepository repo;

    /* 현재 잔여 포인트 조회 */
    @GetMapping("/{userId}")
    public Integer currentPoint(@PathVariable String userId) {
        return repo.findByUserId(userId)
                   .map(Point::getCurrentPoint)
                   .orElse(0);
    }
}
