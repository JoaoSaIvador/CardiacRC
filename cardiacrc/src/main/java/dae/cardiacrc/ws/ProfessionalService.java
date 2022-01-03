package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PatientDTO;
import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.dtos.ProfessionalDTO;
import dae.cardiacrc.dtos.ProgramDTO;
import dae.cardiacrc.ejbs.ProfessionalBean;
import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.entities.Program;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
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

@Path("professionals") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProfessionalService {
    @EJB
    private ProfessionalBean professionalBean;
    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/professionals/”
    @RolesAllowed("Administrator")
    public List<ProfessionalDTO> getAllProfessionalsWS() {
        return toDTOsSimple(professionalBean.getAllProfessionals());
    }

    // Converts an entity Professional to a DTO Professional class
    private ProfessionalDTO toDTOSimple(Professional professional) {
        return new ProfessionalDTO(
                professional.getUsername(),
                professional.getName(),
                professional.getEmail(),
                professional.getLicenseNumber(),
                professional.getType().getId(),
                professional.getType().getName(),
                professional.isDeleted()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<ProfessionalDTO> toDTOsSimple(List<Professional> professionals) {
        return professionals.stream().map(this::toDTOSimple).collect(Collectors.toList());
    }

    private ProfessionalDTO toDTO(Professional professional) {
        ProfessionalDTO professionalDTO = new ProfessionalDTO(
                professional.getUsername(),
                professional.getName(),
                professional.getEmail(),
                professional.getLicenseNumber(),
                professional.getType().getId(),
                professional.getType().getName(),
                professional.isDeleted()
        );

        List<PatientDTO> patientDTOS = patientsToDTOs(professional.getPatients());
        patientDTOS.removeIf(PatientDTO::isDeleted);
        professionalDTO.setPatientDTOs(patientDTOS);

        List<ProgramDTO> programDTOS = programsToDTOs(professional.getPrograms());
        professionalDTO.setProgramsDTOs(programDTOS);

        return professionalDTO;
    }

    @POST
    @Path("/")
    @RolesAllowed("Administrator")
    public Response createNewProfessional (ProfessionalDTO professionalDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        professionalBean.create(
                professionalDTO.getUsername(),
                professionalDTO.getLicenseNumber(),
                professionalDTO.getName(),
                professionalDTO.getPassword(),
                professionalDTO.getEmail(),
                professionalDTO.getType());
        return Response.status(Response.Status.CREATED).entity("Professional " + professionalDTO.getName() + " created!").build();
    }

    @GET
    @Path("{username}")
    public Response getProfessionalDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional") && professional.getUsername().equals(principal.getName()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(toDTO(professional)).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed("Professional")
    public Response count() throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        List total = professionalBean.counts(principal.getName());
        return Response.ok(total).build();
    }

    @PUT
    @Path("{username}")
    public Response updateProfessional (@PathParam("username") String username, ProfessionalDTO professionalDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Professional professional = professionalBean.findProfessional(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional") && professional.getUsername().equals(principal.getName()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        professionalBean.update(
                    principal.getName(),
                    username,
                    professionalDTO.getPasswordConfirmation(),
                    professionalDTO.getLicenseNumber(),
                    professionalDTO.getName(),
                    professionalDTO.getPassword(),
                    professionalDTO.getEmail());
        return Response.ok("Professional updated!").build();
    }

    @DELETE
    @Path("{username}")
    @RolesAllowed("Administrator")
    public Response deleteProfessional (@PathParam("username") String username) throws MyEntityNotFoundException {
        professionalBean.delete(username);
        return Response.ok("Professional deleted!").build();
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

    @GET
    @Path("{username}/prescriptions")
    public Response getProfessionalPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") && professional.getUsername().equals(principal.getName()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<PrescriptionDTO> dtos = prescriptionsToDTOs(professional.getPrescriptions());
        return Response.ok(dtos).build();
    }

    private PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber(),
                patient.isDeleted()
        );
    }

    private List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        return patients.stream().map(this::patientToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/patients")
    public Response getProfessionalPatients(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") && professional.getUsername().equals(principal.getName()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<PatientDTO> dtos = patientsToDTOs(professional.getPatients());
        return Response.ok(dtos).build();
    }

    private ProgramDTO programToDTO(Program program) {
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

    private List<ProgramDTO> programsToDTOs(List<Program> programs) {
        return programs.stream().map(this::programToDTO).collect(Collectors.toList());
    }
}
