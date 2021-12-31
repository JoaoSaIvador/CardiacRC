package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.ejbs.PrescriptionBean;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescriptions")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PrescriptionService {
    @EJB
    private PrescriptionBean prescriptionBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed("Administrator")
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
                prescription.getDescription(),
                prescription.getName(),
                prescription.getType().getId(),
                prescription.getType().getName(),
                prescription.getProgram().getId()
        );
        return prescriptionDTO;
    }

    @GET
    @Path("{id}")
    public Response getPrescriptionDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(id);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional")  && prescription.getProfessional().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(toDTO(prescription)).build();
    }

    @POST
    @Path("/")
    @RolesAllowed("Professional")
    public Response createNewPrescription(PrescriptionDTO prescriptionDTO) throws MyConstraintViolationException, MyEntityNotFoundException, MyEntityExistsException {
        Principal principal = securityContext.getUserPrincipal();
        prescriptionBean.create(principal.getName(),
                prescriptionDTO.getDescription(),
                prescriptionDTO.getName(),
                prescriptionDTO.getProgramId());

        return Response.status(Response.Status.CREATED).entity("Precription " + prescriptionDTO.getName() + " created!").build();
    }

    @PUT
    @Path("{id}")
    public Response updatePrescription(@PathParam("id") int id, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(id);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional")  && prescription.getProfessional().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        prescriptionBean.update(id,
                prescriptionDTO.getProfessionalUsername(),
                prescriptionDTO.getDescription(),
                prescriptionDTO.getName());

        return Response.ok("Prescription updated!").build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePrecription(@PathParam("id") int id) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(id);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional")  && prescription.getProfessional().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        prescriptionBean.delete(id);
        return Response.ok("Precription deleted!").build();
    }
}
