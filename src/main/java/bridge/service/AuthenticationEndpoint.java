package bridge.service;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.AuthResponse;
import beans.Person;
import bridge.PersistenceUser;
import bridge.imp.PersistenceUserImp;
import io.swagger.annotations.Api;

@Api
@Path("authentication")
public class AuthenticationEndpoint {
	private PersistenceUser persistence;

	@POST
	@Path("/params")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Response login(@FormParam("email") String email, @FormParam("password") String password) {
		try {
			AuthResponse auth = authenticate(email, password);
			// Authenticate the user using the credentials provided
			if (null == auth) {
				throw new Exception();
			}

			// Return the token on the response
			return Response.ok(auth.getJson()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

    @GET
    @Path("/fake")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Response getLogin(@FormParam("fake") String fake) {
        try {
            // Return the token on the response
            return Response.ok(fake).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }
	

	@POST
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Response login(@FormParam("idIn") String idIn) {
		try {
			AuthResponse auth = authenticate(idIn);
			// Authenticate the user using the credentials provided
			if (null == auth) {
				throw new Exception();
			}

			// Return the token on the response
			return Response.ok(auth.getJson()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	private AuthResponse authenticate(String idIn) throws Exception {
		if (persistence == null) {
			persistence = new PersistenceUserImp<Person>(Person.class);
		}

		Person person = persistence.isValidCredentials(idIn);
		if (person != null) {
			return new AuthResponse(person);
		}
		return null;
	}

	private AuthResponse authenticate(String email, String password) throws Exception {
		if (persistence == null) {
			persistence = new PersistenceUserImp<Person>(Person.class);
		}

		Person person = persistence.isValidCredentials(email, password);
		if (person != null) {
			return new AuthResponse(person);
		}
		return null;
	}
}