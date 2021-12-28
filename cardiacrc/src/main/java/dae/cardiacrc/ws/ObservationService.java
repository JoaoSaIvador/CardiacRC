package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.ObservationDTO;
import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.dtos.ProgramDTO;
import dae.cardiacrc.ejbs.ObservationBean;
import dae.cardiacrc.entities.Observation;
import dae.cardiacrc.entities.Program;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("observations") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ObservationService {
    @EJB
    private ObservationBean observationBean;

    // Converts an entity Program to a DTO Program class
    private ObservationDTO toDTO(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getPatient().getUsername(),
                observation.getPatient().getName(),
                observation.getValue(),
                observation.getDataType().getId(),
                observation.getDataType().getName(),
                observation.getQualitativeDataType(),
                observation.getDate()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<ObservationDTO> toDTOs(List<Observation> observations) {
        return observations.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewObservation(ObservationDTO observationDTO) throws MyConstraintViolationException, MyEntityNotFoundException, MyEntityExistsException, MyIllegalArgumentException {
        observationBean.create(observationDTO.getPatientUsername(), observationDTO.getValue(), observationDTO.getDataTypeId());
        return Response.status(Response.Status.CREATED).entity("Observation created!").build();
    }

    @GET
    @Path("{observation}")
    public Response getObservationDetails(@PathParam("observation") int id) throws MyEntityNotFoundException {
        Observation observation = observationBean.findObservation(id);
        return Response.ok(toDTO(observation)).build();
    }

    @DELETE
    @Path("{observation}")
    public Response removeObservation(@PathParam("observation") int id) throws MyEntityNotFoundException {
        observationBean.delete(id);
        return Response.ok("Observation deleted!").build();
    }
}
