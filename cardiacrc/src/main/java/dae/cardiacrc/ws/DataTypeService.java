package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.QualitativeDataTypeDTO;
import dae.cardiacrc.dtos.QuantitativeDataTypeDTO;
import dae.cardiacrc.ejbs.QualityDataTypeBean;
import dae.cardiacrc.ejbs.QuantityDataTypeBean;
import dae.cardiacrc.entities.Administrator;
import dae.cardiacrc.entities.QualitativeDataType;
import dae.cardiacrc.entities.QuantitativeDataType;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
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
import java.util.List;
import java.util.stream.Collectors;

@Path("dataTypes") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class DataTypeService {
    @EJB
    private QuantityDataTypeBean quantityDataTypeBean;

    @EJB
    private QualityDataTypeBean qualitativeDataTypeBean;

    @Context
    private SecurityContext securityContext;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/dataTypes/”
    @RolesAllowed({"Administrator", "Patient","Professional"})
    public List<QuantitativeDataTypeDTO> getAllDataTypesWS() {
        if (securityContext.isUserInRole("Administrator")) {
            return toDTOs(quantityDataTypeBean.getAllDataTypes("full"));
        }
        else {
            return toDTOs(quantityDataTypeBean.getAllDataTypes("normal"));
        }
    }

    private List<QuantitativeDataTypeDTO> toDTOs(List<QuantitativeDataType> dataTypes) {
        return dataTypes.stream().map(this::toDTOsimple).collect(Collectors.toList());
    }

    private QuantitativeDataTypeDTO toDTOsimple(QuantitativeDataType quantitativeDataType){
        return new QuantitativeDataTypeDTO(
                quantitativeDataType.getId(),
                quantitativeDataType.getName(),
                quantitativeDataType.getUnit(),
                quantitativeDataType.getMin(),
                quantitativeDataType.getMax(),
                quantitativeDataType.isDeleted()
        );
    }

    private QuantitativeDataTypeDTO toDTO(QuantitativeDataType quantitativeDataType) {
        QuantitativeDataTypeDTO dataType = new QuantitativeDataTypeDTO(
                quantitativeDataType.getId(),
                quantitativeDataType.getName(),
                quantitativeDataType.getUnit(),
                quantitativeDataType.getMin(),
                quantitativeDataType.getMax(),
                quantitativeDataType.isDeleted()
        );

        List<QualitativeDataTypeDTO> qualitativeTypes = qualitativeTypesDTOs(quantitativeDataType.getDataTypes());
        dataType.setQualitativeDataTypeDTOs(qualitativeTypes);
        return dataType;
    }

    private List<QualitativeDataTypeDTO> qualitativeTypesDTOs(List<QualitativeDataType> dataTypes) {
        return dataTypes.stream().map(this::qualitativeTypetoDTO).collect(Collectors.toList());
    }

    private QualitativeDataTypeDTO qualitativeTypetoDTO(QualitativeDataType qualitativeDataType) {
        return new QualitativeDataTypeDTO(
                qualitativeDataType.getId(),
                qualitativeDataType.getName(),
                qualitativeDataType.getMin(),
                qualitativeDataType.getMax()
        );
    }

    @POST
    @Path("/")
    @RolesAllowed("Administrator")
    public Response createNewDataType(QuantitativeDataTypeDTO quantitativeDataTypeDTO) throws MyConstraintViolationException, MyIllegalArgumentException {
        quantityDataTypeBean.create(
                quantitativeDataTypeDTO.getName(),
                quantitativeDataTypeDTO.getUnit(),
                quantitativeDataTypeDTO.getMin(),
                quantitativeDataTypeDTO.getMax());
        return Response.status(Response.Status.CREATED).entity("DataType " + quantitativeDataTypeDTO.getName() + " created!").build();
    }

    @POST
    @Path("{dataType}/qualitatives")
    @RolesAllowed("Administrator")
    public Response createNewQualitativeDataType(@PathParam("dataType") int id, QualitativeDataTypeDTO qualitativeDTO) throws MyConstraintViolationException, MyEntityNotFoundException, MyIllegalArgumentException {
        qualitativeDataTypeBean.create(
                qualitativeDTO.getName(),
                qualitativeDTO.getMin(),
                qualitativeDTO.getMax(),
                id);
        return Response.status(Response.Status.CREATED).entity("Quality DataType " + qualitativeDTO.getName() + " created!").build();
    }

    @GET
    @Path("{dataType}")
    @RolesAllowed({"Administrator", "Patient","Professional"})
    public Response getDataTypeDetails(@PathParam("dataType") int dataTypeId) throws MyEntityNotFoundException {
        QuantitativeDataType dataType = quantityDataTypeBean.findDataType(dataTypeId);
        return Response.ok(toDTO(dataType)).build();
    }

    @PUT
    @Path("{dataType}")
    @RolesAllowed("Administrator")
    public Response updateDataType(@PathParam("dataType") int dataTypeId, QuantitativeDataTypeDTO dataTypeDTO) throws MyEntityNotFoundException {
        quantityDataTypeBean.update(
                dataTypeId,
                dataTypeDTO.getName(),
                dataTypeDTO.getUnit(),
                dataTypeDTO.getMin(),
                dataTypeDTO.getMax()
        );

        return Response.ok("DataType updated!").build();
    }

    @PUT
    @Path("{dataType}/qualitatives/{qualitative}")
    @RolesAllowed("Administrator")
    public Response updateQualitativeDataType(@PathParam("dataType") int dataTypeId, @PathParam("qualitative") int qualitativeId, QualitativeDataTypeDTO qualitativeDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        qualitativeDataTypeBean.update(
                dataTypeId,
                qualitativeId,
                qualitativeDTO.getName(),
                qualitativeDTO.getMin(),
                qualitativeDTO.getMax()
        );
        return Response.ok("Qualitative DataType updated!").build();
    }

    @DELETE
    @Path("{dataType}")
    @RolesAllowed("Administrator")
    public Response deleteDataType(@PathParam("dataType") int dataTypeId) throws MyEntityNotFoundException {
        quantityDataTypeBean.delete(dataTypeId);

        return Response.ok("DataType deleted!").build();
    }

    @DELETE
    @Path("{dataType}/qualitatives/{qualitative}")
    @RolesAllowed("Administrator")
    public Response deleteQualitativeDataType(@PathParam("dataType") int dataTypeId, @PathParam("qualitative") int qualitativeId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        qualitativeDataTypeBean.delete(dataTypeId, qualitativeId);
        return Response.ok("Qualitative DataType deleted!").build();
    }

    @GET
    @Path("/export")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("Administrator")
    public Response export() throws IOException {
        String filename = "dataTypes.xlsx";
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("DataTypes");
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 1500);
        sheet.setColumnWidth(3, 1500);
        sheet.setColumnWidth(4, 10500);

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
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Min");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Max");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Nº of Qualitative DataTypes");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        List<QuantitativeDataType> dataTypes = quantityDataTypeBean.getAllDataTypes("full");
        for (int i = 0; i < dataTypes.size(); i++) {
            Row row = sheet.createRow(i+1);
            Cell cell = row.createCell(0);
            cell.setCellValue(dataTypes.get(i).getId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(dataTypes.get(i).getName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(dataTypes.get(i).getMin());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(dataTypes.get(i).getMax());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(dataTypes.get(i).getDataTypes().size());
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
