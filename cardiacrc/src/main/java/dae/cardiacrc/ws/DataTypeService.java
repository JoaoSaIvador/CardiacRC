package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.QualitativeDataTypeDTO;
import dae.cardiacrc.dtos.QuantitativeDataTypeDTO;
import dae.cardiacrc.ejbs.DataTypeBean;
import dae.cardiacrc.entities.QualitativeDataType;
import dae.cardiacrc.entities.QuantitativeDataType;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("dataTypes") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class DataTypeService {
    @EJB
    private DataTypeBean dataTypeBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/dataTypes/”
    public List<QuantitativeDataTypeDTO> getAllProfessionalsWS() {
        return toDTOs(dataTypeBean.getAllDataTypes());
    }

    private List<QuantitativeDataTypeDTO> toDTOs(List<QuantitativeDataType> dataTypes) {
        return dataTypes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private QuantitativeDataTypeDTO toDTO(QuantitativeDataType quantitativeDataType) {
        QuantitativeDataTypeDTO dataType = new QuantitativeDataTypeDTO(
                quantitativeDataType.getId(),
                quantitativeDataType.getName(),
                quantitativeDataType.getUnit(),
                quantitativeDataType.getMin(),
                quantitativeDataType.getMax()
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


}
