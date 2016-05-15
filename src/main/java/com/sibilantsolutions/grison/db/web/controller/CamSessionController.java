package com.sibilantsolutions.grison.db.web.controller;

import com.sibilantsolutions.grison.db.DbLogger;
import com.sibilantsolutions.grison.db.handler.CamSessionHolder;
import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import com.sibilantsolutions.grison.db.persistence.repository.CamSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RepositoryRestController
// It's a HACK to hard-code the "/api/v1" prefix, defined in spring.data.rest.basePath, but otherwise
// ControllerLinkBuilder.linkTo doesn't include the prefix in the href, but it DOES do the mapping and the result is
// that both /api/v1/camSessions/{id}/whatEv and /camSessions/{id}/whatEv work but /camSessions/{id}/whatEv is what's
// put into the href, when we really want it to be /api/v1/camSessions/{id}/whatEv instead.
@RequestMapping("/api/v1/camSessions")
public class CamSessionController {

    private static final Logger LOG = LoggerFactory.getLogger(CamSessionController.class);

    private final DbLogger dbLogger;
    private final CamSessionRepository camSessionRepository;
    private final CamSessionHolder camSessionHolder;

    @Autowired
    CamSessionController(DbLogger dbLogger, CamSessionRepository camSessionRepository, CamSessionHolder camSessionHolder) {
        LOG.info("\n\n\n Constructed {}.\n\n\n", getClass());
        this.dbLogger = dbLogger;
        this.camSessionRepository = camSessionRepository;
        this.camSessionHolder = camSessionHolder;
    }

    @RequestMapping(path = "/{id}/custom")
    @ResponseBody
    public ResponseEntity<?> customControllerMethod(@PathVariable long id) {
        Resource<String> resource = new Resource<>("GOT HERE id=" + id);
        resource.add(new Link("http://example.com"));
        return ok(resource);
    }

    @RequestMapping("/connected")
    @ResponseBody
    public String findAllConnected() {
        List<CamSession> list = camSessionRepository.findByAllConnected();
        return list.toString();
    }
/*
    @RequestMapping(value = "/videoStart", method = RequestMethod.PATCH)
    public ResponseEntity<CamSessionDto> videoStart() {
        boolean success = dbLogger.getFoscamSession().videoStart();
        StreamStatusDto streamStatusDto = new StreamStatusDto(true, success, false);
        CamSessionDto camSessionDto = new CamSessionDto(camSessionHolder.getCamSession(), streamStatusDto);

        if (success) {
            return new ResponseEntity<>(camSessionDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(camSessionDto, HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/videoEnd", method = RequestMethod.PATCH)
    public ResponseEntity<CamSessionDto> videoEnd() {
        dbLogger.getFoscamSession().videoEnd();
        StreamStatusDto streamStatusDto = new StreamStatusDto(true, false, false);
        CamSessionDto camSessionDto = new CamSessionDto(camSessionHolder.getCamSession(), streamStatusDto);
        return new ResponseEntity<>(camSessionDto, HttpStatus.OK);
    }
*/
}
