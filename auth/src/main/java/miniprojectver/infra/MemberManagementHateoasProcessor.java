package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class MemberManagementHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<MemberManagement>> {

    @Override
    public EntityModel<MemberManagement> process(
        EntityModel<MemberManagement> model
    ) {
        return model;
    }
}
