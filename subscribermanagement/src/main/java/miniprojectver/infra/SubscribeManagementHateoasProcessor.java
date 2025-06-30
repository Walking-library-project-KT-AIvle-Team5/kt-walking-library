package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscribeManagementHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<SubscribeManagement>> {

    @Override
    public EntityModel<SubscribeManagement> process(
        EntityModel<SubscribeManagement> model
    ) {
        return model;
    }
}
