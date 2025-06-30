package miniprojectver.infra;

import miniprojectver.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookPurchaseManagementHateoasProcessor
    implements
        RepresentationModelProcessor<EntityModel<BookPurchaseManagement>> {

    @Override
    public EntityModel<BookPurchaseManagement> process(
        EntityModel<BookPurchaseManagement> model
    ) {
        return model;
    }
}
