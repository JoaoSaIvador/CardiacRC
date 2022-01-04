package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.AdministratorDTO;
import dae.cardiacrc.ejbs.AdministratorBean;
import dae.cardiacrc.entities.Administrator;
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

@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {
    @EJB
    private AdministratorBean administratorBean;
    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/administrators/”
    @RolesAllowed("Administrator")
    public List<AdministratorDTO> getAllAdministratorsWS() {
        return toDTOs(administratorBean.getAllAdministrators());
    }

    // Converts an entity Administrator to a DTO Administrator class
    private AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                administrator.getName(),
                administrator.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    @RolesAllowed("Administrator")
    public Response createNewAdministrator (AdministratorDTO administratorDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        administratorBean.create(
                administratorDTO.getUsername(),
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        return Response.status(Response.Status.CREATED).entity("Administrator " + administratorDTO.getName() + " created!").build();
    }

    @GET
    @Path("{username}")
    @RolesAllowed("Administrator")
    public Response getAdministratorDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.ok(toDTO(administrator)).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed("Administrator")
    public Response count(){
        List total = administratorBean.counts();
        return Response.ok(total).build();
    }

    @PUT
    @Path("{username}")
    public Response updateAdministrator (@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Principal principal = securityContext.getUserPrincipal();
        Administrator administrator = administratorBean.findAdministrator(username);
        if (!(securityContext.isUserInRole("Administrator") && administrator.getUsername().equals(principal.getName()))){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        administratorBean.update(
                username,
                administratorDTO.getPasswordConfirmation(),
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        return Response.ok("Administrator updated!").build();
    }

    @DELETE
    @Path("{username}")
    @RolesAllowed("Administrator")
    public Response deleteAdministrator (@PathParam("username") String username) throws MyEntityNotFoundException {
        administratorBean.delete(username);
        return Response.ok("Administrator deleted!").build();
    }

    @GET
    @Path("/export")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("Administrator")
    public Response export() throws IOException {
        String filename = "administrators.xlsx";
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Administrators");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 7000);

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

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        List<Administrator> administrators = administratorBean.getAllAdministrators();
        for (int i = 0; i < administrators.size(); i++) {
            Row row = sheet.createRow(i+1);
            Cell cell = row.createCell(0);
            cell.setCellValue(administrators.get(i).getUsername());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(administrators.get(i).getName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(administrators.get(i).getEmail());
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
