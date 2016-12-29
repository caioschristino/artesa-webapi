package bridge.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.AuthResponse;
import beans.Person;
import bridge.PersistenceUser;
import bridge.imp.PersistenceUserImp;

@Path("authentication")
public class AuthenticationEndpoint {
	private PersistenceUser persistence;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Response authenticateUser(@FormParam("email") String email, @FormParam("password") String password) {
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