package miniprojectver.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import miniprojectver.domain.*;
import miniprojectver.infra.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final FetchSubscriberListRepository subscriberRepo;
    private final PointViewRepository pointRepo;
    private final AuthorStatisticsRepository statsRepo;

    public DashboardController(
        FetchSubscriberListRepository subscriberRepo,
        PointViewRepository pointRepo,
        AuthorStatisticsRepository statsRepo
    ) {
        this.subscriberRepo = subscriberRepo;
        this.pointRepo = pointRepo;
        this.statsRepo = statsRepo;
    }

    @GetMapping("/subscribers")
    public Iterable<FetchSubscriberList> getSubscribers() {
        return subscriberRepo.findAll();
    }

    @GetMapping("/points")
    public Iterable<PointView> getPoints() {
        return pointRepo.findAll();
    }

    @GetMapping("/statistics")
    public Iterable<AuthorStatistics> getStatistics() {
        return statsRepo.findAll();
    }
}
