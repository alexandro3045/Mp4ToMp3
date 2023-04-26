package ufla.projects.mp4tomp3.application;

import ufla.projects.mp4tomp3.dto.PayloadDTO;
import ufla.projects.mp4tomp3.domain.Mp4ToMp3Body;
import ufla.projects.mp4tomp3.domain.Payload;
import ufla.projects.mp4tomp3.domain.Status;
import ufla.projects.mp4tomp3.services.MessageService;
import ufla.projects.mp4tomp3.services.PayloadService;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api/v1/convert")
public class Mp4ToMp3Controller {
    @Inject
    Logger logger;
    @Inject
    PayloadService payloadService;
    @Inject
    MessageService messageService;

    @POST
    @Transactional
    @RolesAllowed({"manager"})
    public void convertMp4ToMp3(@Valid @MultipartForm Mp4ToMp3Body data) throws Exception {
        logger.info("Starting converting mp4 to mp3 - " + data);
        var payloadToPersist = Payload.multiPartToPayload(data);

        payloadToPersist.setStatus(Status.converting);
        PayloadDTO payloadDTO = payloadService.sendObjectToStorage(payloadToPersist, data.getFile());
        messageService.send(payloadDTO);
        payloadToPersist.persist();
        logger.info("Payload created " + payloadToPersist);
    }

    @GET
    public Response getAllPayload() {
        logger.info("Getting all payload ");
        return Response.ok(Payload.listAll()).build();
    }

}
