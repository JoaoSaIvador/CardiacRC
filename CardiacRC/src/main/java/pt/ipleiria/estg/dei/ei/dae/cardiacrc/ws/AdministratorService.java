package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ws;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {
    @EJB
    private AdministratorBean administratorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/administrators/”
    public List<AdministratorDTO> getAllAdministratorsWS() {
        return toDTOs(administratorBean.getAllAdministrators());
    }

    // Converts an entity Student to a DTO Administrator class
    private AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                administrator.getPassword(),
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
    public Response createNewAdministrator (AdministratorDTO administratorDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        administratorBean.create(
                administratorDTO.getUsername(),
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.ok(toDTO(administrator)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateAdministrator (@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException {
        administratorBean.updateAdministrator(
                administratorDTO.getUsername(),
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        return Response.ok().build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteAdministrator (@PathParam("username") String username) throws MyEntityNotFoundException {
        administratorBean.deleteAdministrator(username);
        return Response.ok().build();
    }
}
