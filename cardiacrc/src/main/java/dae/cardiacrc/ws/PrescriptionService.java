package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.ejbs.PrescriptionBean;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescriptions")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PrescriptionService {
    @EJB
    private PrescriptionBean prescriptionBean;

    @GET
    @Path("/")
    public List<PrescriptionDTO> getAllPrescriptionsWS(){
        return toDTOs(prescriptionBean.getAllPrescriptions());
    }

    private List<PrescriptionDTO> toDTOs(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PrescriptionDTO toDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(
                prescription.getId(),
                prescription.getProfessional().getUsername(),
                prescription.getProfessional().getName(),
                prescription.getPatient().getUsername(),
                prescription.getPatient().getName(),
                prescription.getDescription(),
                prescription.getName(),
                prescription.getDuration(),
                prescription.isState()
        );
        return prescriptionDTO;
    }

    @GET
    @Path("{id}")
    public Response getPrescriptionDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(id);
        return Response.ok(toDTO(prescription)).build();
    }

    @POST
    @Path("/")
    public Response createNewPrescription(PrescriptionDTO prescriptionDTO) throws MyConstraintViolationException, MyEntityNotFoundException, MyEntityExistsException {
        prescriptionBean.create(prescriptionDTO.getProfessionalUsername(),
                prescriptionDTO.getPatientUsername(),
                prescriptionDTO.getDescription(),
                prescriptionDTO.getName(),
                prescriptionDTO.getDuration());

        return Response.status(Response.Status.CREATED).entity("Precription " + prescriptionDTO.getName() + " created!").build();
    }

    @PUT
    @Path("{id}")
    public Response updatePrescription(@PathParam("id") int id, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        prescriptionBean.updatePrescription(id,
                prescriptionDTO.getProfessionalUsername(),
                prescriptionDTO.getPatientUsername(),
                prescriptionDTO.getDescription(),
                prescriptionDTO.getName(),
                prescriptionDTO.getDuration());

        return Response.ok("Prescription updated!").build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePrecription(@PathParam("id") int id) throws MyEntityNotFoundException {
        prescriptionBean.deletePresciption(id);
        return Response.ok("Precription deleted!").build();
    }

    @PATCH
    @Path("{id}")
    public Response toggleState(@PathParam("id") int id) throws MyEntityNotFoundException {
        prescriptionBean.toggleState(id);
        return Response.ok("Prescription state changed!").build();
    }
}
