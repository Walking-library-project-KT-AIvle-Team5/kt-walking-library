package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ReadingManagementHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<ReadingManagement>> {

    @Override
    public EntityModel<ReadingManagement> process(
        EntityModel<ReadingManagement> model
    ) {
        return model;
    }
}
