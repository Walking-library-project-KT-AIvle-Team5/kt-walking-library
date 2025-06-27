package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuthorRegistrationHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AuthorRegistration>> {

    @Override
    public EntityModel<AuthorRegistration> process(
        EntityModel<AuthorRegistration> model
    ) {
        return model;
    }
}
