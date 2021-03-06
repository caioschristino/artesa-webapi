package bridge.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import org.json.JSONException;

import com.google.gson.Gson;

import beans.AuthResponse;
import beans.Person;
import bridge.PersistenceUser;
import bridge.imp.PersistenceUserImp;

@Api
@Path("user")
public class UserEndPoint {
	private Gson gson = new Gson();
	private PersistenceUser persistence;

	@Path("add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String jsonString) throws JSONException {
		Person person = null;
		if (jsonString == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		try {
			person = gson.fromJson(jsonString, Person.class);
			if (persistence == null) {
				persistence = new PersistenceUserImp<Person>(Person.class);
			}
			AuthResponse auth = new AuthResponse(persistence.save(person));
			return Response.ok(auth.getJson()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@Path("update")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(String jsonString) throws JSONException {
		Person person;
		if (jsonString == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		try {
			person = gson.fromJson(jsonString, Person.class);
			if (persistence == null) {
				persistence = new PersistenceUserImp<Person>(Person.class);
			}
			AuthResponse auth = new AuthResponse(persistence.update(person));
			return Response.ok(auth.getJson()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}