package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookPublicationHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<BookPublication>> {

    @Override
    public EntityModel<BookPublication> process(
        EntityModel<BookPublication> model
    ) {
        return model;
    }
}
