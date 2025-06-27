package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PointRequestManagementHateoasProcessor
    implements
        RepresentationModelProcessor<EntityModel<PointRequestManagement>> {

    @Override
    public EntityModel<PointRequestManagement> process(
        EntityModel<PointRequestManagement> model
    ) {
        return model;
    }
}
