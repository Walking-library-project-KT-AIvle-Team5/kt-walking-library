package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PlatformManagementHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<PlatformManagement>> {

    @Override
    public EntityModel<PlatformManagement> process(
        EntityModel<PlatformManagement> model
    ) {
        return model;
    }
}
