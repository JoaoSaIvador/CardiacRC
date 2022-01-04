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

    @GET
    @Path("/export")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("Administrator")
    public Response export() throws IOException {
        String filename = "professionals.xlsx";
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Professionals");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 6500);
        sheet.setColumnWidth(5, 10000);
        sheet.setColumnWidth(6, 10000);
        sheet.setColumnWidth(7, 10000);
        sheet.setColumnWidth(8, 4000);

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
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("LicenceNumber");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Nº of Patients");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Nº of Prescriptions");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Nº of Programs");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Is deleted");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        List<Professional> professionals = professionalBean.getAllProfessionals();
        for (int i = 0; i < professionals.size(); i++) {
            Row row = sheet.createRow(i+1);
            Cell cell = row.createCell(0);
            cell.setCellValue(professionals.get(i).getUsername());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(professionals.get(i).getName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(professionals.get(i).getEmail());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(professionals.get(i).getType().getName());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(professionals.get(i).getLicenseNumber());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(professionals.get(i).getPatients().size());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(professionals.get(i).getPrescriptions().size());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(professionals.get(i).getPrograms().size());
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(professionals.get(i).isDeleted());
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
