package dae.cardiacrc.ws;

import dae.cardiacrc.dtos.AdministratorDTO;
import dae.cardiacrc.dtos.TypeDTO;
import dae.cardiacrc.ejbs.TypeBean;
import dae.cardiacrc.entities.Administrator;
import dae.cardiacrc.entities.Type;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("types") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TypeService {
    @EJB
    private TypeBean typeBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/administrators/”
    public List<TypeDTO> getAllAdministratorsWS() {
        return toDTOs(typeBean.getAllTypes());
    }

    // Converts an entity Type to a DTO Type class
    private TypeDTO toDTO(Type type) {
        return new TypeDTO(
                type.getId(),
                type.getName()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<TypeDTO> toDTOs(List<Type> types) {
        return types.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewAdministrator (TypeDTO typeDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        typeBean.create(typeDTO.getName());

        return Response.status(Response.Status.CREATED).entity("Type " + typeDTO.getName() + " created!").build();
    }
}
