package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PatientDTO;
import dae.cardiacrc.dtos.ObservationDTO;
import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.dtos.ProfessionalDTO;
import dae.cardiacrc.ejbs.PatientBean;
import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Observation;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

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
        return Response.ok(toDTO(patient)).build();
    }

    @GET
    @Path("/count")
    public Response count() throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        List total = patientBean.counts(principal.getName());
        return Response.ok(total).build();
    }

    @PUT
    @Path("{username}")
    public Response updatePatient (@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Principal principal = securityContext.getUserPrincipal();
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
    public Response addProfessional (@PathParam("username") String username, String professionalUsername) throws MyEntityNotFoundException {
        patientBean.addProfessional(username, professionalUsername);
        return Response.ok("Professional added!").build();
    }

    @PATCH
    @Path("{username}/removeProfessional")
    public Response removeProfessional (@PathParam("username") String username, String professionalUsername) throws MyEntityNotFoundException, MyIllegalArgumentException {
        patientBean.removeProfessional(username, professionalUsername);
        return Response.ok("Professional added!").build();
    }

    @DELETE
    @Path("{username}")
    public Response deletePatient (@PathParam("username") String username) throws MyEntityNotFoundException {
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

    private PrescriptionDTO prescriptionToDTO(Prescription prescription) {
        return  new PrescriptionDTO(
                prescription.getId(),
                prescription.getProfessional().getUsername(),
                prescription.getProfessional().getName(),
                prescription.getDescription(),
                prescription.getName()
        );
    }

    private List<PrescriptionDTO> prescriptionsToDTOs(List<Prescription> prescriptions) {
        return  prescriptions.stream().map(this::prescriptionToDTO).collect(Collectors.toList());
    }

//    @GET
//    @Path("{username}/prescriptions")
//    public Response getPatientPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
//        Patient patient = patientBean.findPatient(username);
//        List<PrescriptionDTO> dtos = prescriptionsToDTOs(patient.getPrescriptions());
//        return Response.ok(dtos).build();
//    }

    private ObservationDTO patientDataToDTO(Observation observation) {
        return  new ObservationDTO(
                observation.getId(),
                observation.getPatient().getUsername(),
                observation.getPatient().getName(),
                observation.getValue(),
                observation.getDataType().getId(),
                observation.getDataType().getName(),
                observation.getDate()
        );
    }

    private List<ObservationDTO> patientDataToDTOs(List<Observation> patientData) {
        return  patientData.stream().map(this::patientDataToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/patientData")
    public Response getPatientData(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        List<ObservationDTO> dtos = patientDataToDTOs(patient.getPatientObservations());
        return Response.ok(dtos).build();
    }
}

