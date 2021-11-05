package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.PatientDTO;
import dae.cardiacrc.dtos.PrescriptionDTO;
import dae.cardiacrc.dtos.ProfessionalDTO;
import dae.cardiacrc.ejbs.ProfessionalBean;
import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("professionals") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProfessionalService {
    @EJB
    private ProfessionalBean professionalBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/professionals/”
    public List<ProfessionalDTO> getAllProfessionalsWS() {
        return toDTOsSimple(professionalBean.getAllProfessionals());
    }

    // Converts an entity Student to a DTO Professional class
    private ProfessionalDTO toDTOSimple(Professional professional) {
        return new ProfessionalDTO(
                professional.getUsername(),
                professional.getPassword(),
                professional.getName(),
                professional.getEmail(),
                professional.getLicenseNumber()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<ProfessionalDTO> toDTOsSimple(List<Professional> professionals) {
        return professionals.stream().map(this::toDTOSimple).collect(Collectors.toList());
    }

    // Converts an entity Student to a DTO Student class
    private ProfessionalDTO toDTO(Professional professional) {
        ProfessionalDTO professionalDTO = new ProfessionalDTO(
                professional.getUsername(),
                professional.getPassword(),
                professional.getName(),
                professional.getEmail(),
                professional.getLicenseNumber()
        );

        List<PatientDTO> patientDTOS = patientToDTOs(professional.getPatients());
        professionalDTO.setPatientDTOs(patientDTOS);

        List<PrescriptionDTO> prescriptionDTOS = prescriptionsToDTOs(professional.getPrescriptions());
        professionalDTO.setPrescriptionDTOs(prescriptionDTOS);

        return professionalDTO;
    }

    @POST
    @Path("/")
    public Response createNewProfessional (ProfessionalDTO professionalDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        professionalBean.create(
                professionalDTO.getUsername(),
                professionalDTO.getLicenseNumber(),
                professionalDTO.getName(),
                professionalDTO.getPassword(),
                professionalDTO.getEmail());
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{username}")
    public Response getProfessionalDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        return Response.ok(toDTO(professional)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateProfessional (@PathParam("username") String username, ProfessionalDTO professionalDTO) throws MyEntityNotFoundException {
        professionalBean.updateProfessional(
                professionalDTO.getUsername(),
                professionalDTO.getLicenseNumber(),
                professionalDTO.getName(),
                professionalDTO.getPassword(),
                professionalDTO.getEmail());
        return Response.ok().build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteProfessional (@PathParam("username") String username) throws MyEntityNotFoundException {
        professionalBean.deleteProfessional(username);
        return Response.ok().build();
    }


    private PrescriptionDTO prescriptionToDTO(Prescription prescription) {
        return  new PrescriptionDTO(
                prescription.getId(),
                prescription.getProfessional().getUsername(),
                prescription.getProfessional().getName(),
                prescription.getPatient().getUsername(),
                prescription.getPatient().getName(),
                prescription.getDescription()
        );
    }

    private List<PrescriptionDTO> prescriptionsToDTOs(List<Prescription> prescriptions) {
        return  prescriptions.stream().map(this::prescriptionToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/prescriptions")
    public Response getProfessionalPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        List<PrescriptionDTO> dtos = prescriptionsToDTOs(professional.getPrescriptions());
        return Response.ok(dtos).build();
    }

    private PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getUsername(),
                patient.getPassword(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber(),
                patient.getProfessional().getUsername(),
                patient.getProfessional().getName()
        );
    }

    private List<PatientDTO> patientToDTOs(List<Patient> patients) {
        return patients.stream().map(this::patientToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/patients")
    public Response getProfessionalPatients(@PathParam("username") String username) throws MyEntityNotFoundException {
        Professional professional = professionalBean.findProfessional(username);
        List<PatientDTO> dtos = patientToDTOs(professional.getPatients());
        return Response.ok(dtos).build();
    }
}
