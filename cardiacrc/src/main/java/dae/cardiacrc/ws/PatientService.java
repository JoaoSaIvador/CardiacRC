package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.*;
import dae.cardiacrc.ejbs.PatientBean;
import dae.cardiacrc.entities.*;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    // Converts an entity Patient to a DTO Patient class
    private PatientDTO toDTOSimple(Patient patient) {
        return new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber(),
                patient.isDeleted()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<PatientDTO> toDTOsSimple(List<Patient> patients) {
        return patients.stream().map(this::toDTOSimple).collect(Collectors.toList());
    }

    // Converts an entity Patient to a DTO Patient class
    private PatientDTO toDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getHealthNumber(),
                patient.isDeleted()
        );

        patientDTO.setProfessionalDTOs(professionalToDTOs(patient.getProfessionals()));
        patientDTO.setObservationDTOS(patientDataToDTOs(patient.getPatientObservations()));
        patientDTO.setProgramDTOS(programsToDTOs(patient.getPrograms()));

        return patientDTO;
    }

    // converts an entire list of entities into a list of DTOs
    private List<PatientDTO> toDTOs(List<Patient> patients) {
        return patients.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProfessionalDTO professionalToDTO(Professional professional) {
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

    private ObservationDTO patientDataToDTO(Observation observation) {
        return  new ObservationDTO(
                observation.getId(),
                observation.getObserver(),
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

    private boolean checkProfessional(Principal principal, Patient patient){
        if (securityContext.isUserInRole("Professional")){
            for (Professional professional : patient.getProfessionals()) {
                if (professional.getUsername().equals(principal.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/patients/”
    @RolesAllowed({"Administrator","Professional"})
    public List<PatientDTO> getAllPatientsWS() {
        if (securityContext.isUserInRole("Administrator")){
            return toDTOsSimple(patientBean.getAllPatients("full"));
        }
        else {
            return toDTOsSimple(patientBean.getAllPatients("normal"));
        }
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator","Professional"})
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
        if (!(securityContext.isUserInRole("Administrator") ||
                checkProfessional(principal,patient) ||
                (securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername())))){

            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(toDTO(patient)).build();
    }

    @PUT
    @Path("{username}")
    public Response updatePatient (@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if (!(securityContext.isUserInRole("Administrator") ||
                checkProfessional(principal,patient) ||
                (securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername())))){

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
    public Response removeProfessional (@PathParam("username") String username, ProfessionalDTO professionalDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if (!checkProfessional(principal,patient)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        patientBean.removeProfessional(username, professionalDTO.getUsername());
        return Response.ok("Professional added!").build();
    }

    @DELETE
    @Path("{username}")
    public Response deletePatient (@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if (!(securityContext.isUserInRole("Administrator") ||
                checkProfessional(principal,patient))){

            return Response.status(Response.Status.FORBIDDEN).build();
        }

        patientBean.delete(username);
        return Response.ok("Patient deleted!").build();
    }

    @GET
    @Path("{username}/programs")
    public Response getPatientPrograms(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if (!(checkProfessional(principal,patient) ||
                (securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername())))){

            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ProgramDTO> dtos = programsToDTOs(patient.getPrograms());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{username}/observations")
    public Response getPatientObservations(@PathParam("username") String username) throws MyEntityNotFoundException {
        Patient patient = patientBean.findPatient(username);
        Principal principal = securityContext.getUserPrincipal();
        if (!(checkProfessional(principal,patient) ||
                (securityContext.isUserInRole("Patient") && principal.getName().equals(patient.getUsername())))){

            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<ObservationDTO> dtos = patientDataToDTOs(patient.getPatientObservations());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("/export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("Administrator")
    public Response export() throws IOException {
        String filename = "patients.xlsx";
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Patients");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(4, 10000);
        sheet.setColumnWidth(5, 10000);
        sheet.setColumnWidth(6, 10000);
        sheet.setColumnWidth(7, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Username");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Email");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("HealthNumber");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Nº of Professionals");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Nº of Programs");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Nº of Observations");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Is deleted");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        List<Patient> patients = patientBean.getAllPatients("full");
        for (int i = 0; i < patients.size(); i++) {
            Row row = sheet.createRow(i+1);
            Cell cell = row.createCell(0);
            cell.setCellValue(patients.get(i).getUsername());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(patients.get(i).getName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(patients.get(i).getEmail());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(patients.get(i).getHealthNumber());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(patients.get(i).getProfessionals().size());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(patients.get(i).getPrograms().size());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(patients.get(i).getPatientObservations().size());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(patients.get(i).isDeleted());
            cell.setCellStyle(style);
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + filename;

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

        File file = new File(fileLocation);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=" +
                filename);

        return response.build();
    }
}

