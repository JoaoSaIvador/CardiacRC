package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.dtos.ProgramDTO;
import dae.cardiacrc.ejbs.ProgramBean;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Program;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

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

@Path("programs") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProgramService {
    @EJB
    private ProgramBean programBean;
    @Context
    private SecurityContext securityContext;

    // Converts an entity Program to a DTO Program class
    private ProgramDTO toDTO(Program program) {
        ProgramDTO programDTO =  new ProgramDTO(
                program.getId(),
                program.getProfessional().getUsername(),
                program.getProfessional().getName(),
                program.getPatient().getUsername(),
                program.getPatient().getName(),
                program.getStartDate(),
                program.getDuration()
        );

        List<PrescriptionDTO> prescriptionDTOS = prescriptionsToDTOs(program.getPrescriptions());
        programDTO.setPrescriptions(prescriptionDTOS);

        return programDTO;
    }

    private ProgramDTO toDTOsimple(Program program) {
        return new ProgramDTO(
                program.getId(),
                program.getProfessional().getUsername(),
                program.getProfessional().getName(),
                program.getPatient().getUsername(),
                program.getPatient().getName(),
                program.getStartDate(),
                program.getDuration()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<ProgramDTO> toDTOs(List<Program> programs) {
        return programs.stream().map(this::toDTOsimple).collect(Collectors.toList());
    }

    private PrescriptionDTO prescriptionToDTO(Prescription prescription) {
        return  new PrescriptionDTO(
                prescription.getId(),
                prescription.getProfessional().getUsername(),
                prescription.getProfessional().getName(),
                prescription.getDescription(),
                prescription.getName(),
                prescription.getType().getId(),
                prescription.getType().getName(),
                prescription.getProgram().getId(),
                prescription.getFrequency()
        );
    }

    private List<PrescriptionDTO> prescriptionsToDTOs(List<Prescription> prescriptions) {
        return  prescriptions.stream().map(this::prescriptionToDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewProgram(ProgramDTO programDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        programBean.create(programDTO.getDuration(), programDTO.getPatientUsername(), programDTO.getProfessionalUsername(), programDTO.getPrescriptionIds());
        return Response.status(Response.Status.CREATED).entity("Program created!").build();
    }

    @GET
    @Path("/")
    @RolesAllowed({"Professional", "Patient"})
    public List<ProgramDTO> getAllPrograms(){
        Principal principal = securityContext.getUserPrincipal();
        return toDTOs(programBean.getAllPrograms(principal.getName()));
    }

    @GET
    @Path("{program}")
    public Response getProgramDetails(@PathParam("program") int programId) throws MyEntityNotFoundException {
        Program program = programBean.findProgram(programId);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") && program.getProfessional().getUsername().equals(principal.getName()) || securityContext.isUserInRole("Patient") && program.getPatient().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(toDTO(program)).build();
    }

    @PATCH
    @Path("{program}")
    public Response updateProgram(@PathParam("program") int programId, ProgramDTO programDTO) throws MyEntityNotFoundException {
        Program program = programBean.findProgram(programId);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") && program.getProfessional().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        programBean.update(programId, programDTO.getDuration());
        return Response.ok("Program updated!").build();
    }

    @DELETE
    @Path("{program}")
    public Response deleteProgram(@PathParam("program") int programId) throws MyEntityNotFoundException {
        Program program = programBean.findProgram(programId);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") && program.getProfessional().getUsername().equals(principal.getName()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        programBean.delete(programId);
        return Response.ok("Program deleted!").build();
    }

    @PATCH
    @Path("{program}/addPrescription")
    @RolesAllowed("Professional")
    public Response addPrescription (@PathParam("program") int programId, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        programBean.addPrescription(programId, prescriptionDTO.getId());
        return Response.ok("Prescription added!").build();
    }

    @PATCH
    @Path("{program}/removePrescription")
    @RolesAllowed("Professional")
    public Response removePrescription (@PathParam("program") int programId, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        programBean.removePrescription(programId, prescriptionDTO.getId());
        return Response.ok("Prescription added!").build();
    }
}
