package dae.cardiacrc.ws;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import dae.cardiacrc.dtos.AuthDTO;
import dae.cardiacrc.ejbs.AdministratorBean;
import dae.cardiacrc.ejbs.JwtBean;
import dae.cardiacrc.entities.Person;
import dae.cardiacrc.jwt.Jwt;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.logging.Logger;

@Path("/auth")
public class LoginService {
    private static final Logger log =
            Logger.getLogger(LoginService.class.getName());
    @EJB
    JwtBean jwtBean;
    @EJB
    AdministratorBean administratorBean;
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(AuthDTO authDTO) {
        try {
            Person person = administratorBean.authenticate(authDTO.getUsername(), authDTO.getPassword());
            if (person != null) {
                if (person.getUsername() != null) {
                    log.info("Generating JWT for user " + person.getUsername());
                }
                String token = jwtBean.createJwt(person.getUsername(), new
                        String[]{person.getClass().getSimpleName()});
                return Response.ok(new Jwt(token)).build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    @GET
    @Path("/user")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT j = JWTParser.parse(auth.substring(7));
                return Response.ok(j.getJWTClaimsSet().getClaims()).build();
            //Note: nimbusds converts token expiration time to milliseconds
            } catch (ParseException e) {
                log.warning(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.status(204).build(); //no jwt means no claims to extract
    }
}