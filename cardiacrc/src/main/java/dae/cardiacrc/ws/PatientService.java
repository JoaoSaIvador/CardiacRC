package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.*;
import dae.cardiacrc.ejbs.PatientBean;
import dae.cardiacrc.entities.*;
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

@Path("patients") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PatientService {
    @EJB
    private PatientBean patientBean;
    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/patients/”
    @RolesAllowed({"Administrator","Professional"})
    public List<PatientDTO> getAllPatientsWS() {
        return toDTOsSimple(patientBean.getAllPatients());
    }

    // Converts an entity Patient to a DTO Patient class
    private PatientDTO toDTOSimple(Patient patient) {
        return new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<PatientDTO> toDTOsSimple(List<Patient> patients) {
        return patients.stream().map(this::toDTOSimple).collect(Collectors.toList());
    }

    // Converts an entity Student to a DTO Student class
    private PatientDTO toDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber()
        );

        patientDTO.setProfessionalDTOs(professionalToDTOs(patient.getProfessionals()));
        patientDTO.setPatientDataDTOS(patientDataToDTOs(patient.getPatientObservations()));

        return patientDTO;
    }

    // converts an entire list of entities into a list of DTOs
    private List<PatientDTO> toDTOs(List<Patient> patients) {
        return patients.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    @RolesAllowed("Professional")
    public Response createNewPatient (PatientDTO patientDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        patientBean.create(
                patientDTO.getUsername(),
                patientDTO.getHealthNumber(),
                patientDTO.getName(),
                patientDTO.getPassword(),
                patientDTO.getEmail());
        return Response.status(Response.Status.CREATED).entity("Patient " + patientDTO.getName() + " created!").build();
    }

    @GET
    @Path("{username}")
    public Response getPatientDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional") ||
                securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok(toDTO(patient)).build();
    }

    @PUT
    @Path("{username}")
    public Response updatePatient (@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional") ||
                securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.update(
                principal.getName(),
                username,
                patientDTO.getPasswordConfirmation(),
                patientDTO.getHealthNumber(),
                patientDTO.getName(),
                patientDTO.getPassword(),
                patientDTO.getEmail());
        return Response.ok("Patient updated!").build();
    }

    @PATCH
    @Path("{username}/addProfessional")
    @RolesAllowed("Professional")
    public Response addProfessional (@PathParam("username") String username, ProfessionalDTO professionalDTO) throws MyEntityNotFoundException {
        patientBean.addProfessional(username, professionalDTO.getUsername());
        return Response.ok("Professional added!").build();
    }

    @PATCH
    @Path("{username}/removeProfessional")
    @RolesAllowed("Professional")
    public Response removeProfessional (@PathParam("username") String username, ProfessionalDTO professionalDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        patientBean.removeProfessional(username, professionalDTO.getUsername());
        return Response.ok("Professional added!").build();
    }

    @DELETE
    @Path("{username}")
    public Response deletePatient (@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.delete(username);
        return Response.ok("Patient deleted!").build();
    }

    private ProfessionalDTO professionalToDTO(Professional professional) {
        return  new ProfessionalDTO(
                professional.getUsername(),
                professional.getName(),
                professional.getEmail(),
                professional.getLicenseNumber(),
                professional.getType().getId(),
                professional.getType().getName()
        );
    }

    private List<ProfessionalDTO> professionalToDTOs(List<Professional> professionals) {
        return  professionals.stream().map(this::professionalToDTO).collect(Collectors.toList());
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

    @GET
    @Path("{username}/programs")
    public Response getPatientPrograms(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Professional") ||
                securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername()))) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ProgramDTO> dtos = programsToDTOs(patient.getPrograms());
        return Response.ok(dtos).build();
    }

    private ObservationDTO patientDataToDTO(Observation observation) {
        return  new ObservationDTO(
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

    private List<ObservationDTO> patientDataToDTOs(List<Observation> patientData) {
        return  patientData.stream().map(this::patientDataToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/observations")
    public Response getPatientData(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Administrator") ||
                securityContext.isUserInRole("Professional") ||
                securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername()))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ObservationDTO> dtos = patientDataToDTOs(patient.getPatientObservations());
        return Response.ok(dtos).build();
    }
}

