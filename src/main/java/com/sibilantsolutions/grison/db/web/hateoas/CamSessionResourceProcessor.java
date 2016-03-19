package com.sibilantsolutions.grison.db.web.hateoas;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import com.sibilantsolutions.grison.db.web.controller.CamSessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamSessionResourceProcessor implements ResourceProcessor<Resource<CamSession>> {

    private static final Logger LOG = LoggerFactory.getLogger(CamSessionResourceProcessor.class);

    CamSessionResourceProcessor() {
        LOG.info("\n\n\n Constructed {}.\n\n\n", getClass());
    }

    @Override
    public Resource<CamSession> process(Resource<CamSession> resource) {
        // As an example, add a custom link to every other session.
        if (resource.getContent().getId() % 2 == 0) {
            Link link1 = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CamSessionController.class)
                    .customControllerMethod(resource.getContent().getId())).withRel("customControllerMethod");
            resource.add(link1);
        }
        return resource;
    }

}
