package bridge.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.Gson;

import beans.Person;
import bridge.PersistenceUser;
import bridge.imp.PersistenceUserImp;
import repository.PersonRepository;
import strategy.RepositoryStrategy;

@Path("user")
public class UserEndPoint {
	private Gson gson = new Gson();
	private PersistenceUser persistence;

	@Path("add")
	@POST
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)
	public Response add(String jsonString) throws JSONException {
		Person person;
		if (jsonString == null) {
			return Response.status(200).entity("").build();
		}

		person = gson.fromJson(jsonString, Person.class);
		if (persistence == null) {
			persistence = new PersistenceUserImp<Person>(Person.class);
			persistence.save(new Person("caio", "caioschristino@gmail.com", "1234", "(11) 97190-0161", 20));
		}
		return Response.status(200).entity(persistence.save(person)).build();
	}

	@Path("update")
	@POST
	@Produces("application/json")
	public Response update(String jsonString) throws JSONException {
		Person person;
		if (jsonString == null) {
			return Response.status(200).entity("").build();
		}

		person = gson.fromJson(jsonString, Person.class);
		if (persistence == null) {
			persistence = new PersistenceUserImp<Person>(Person.class);
		}
		return Response.status(200).entity(persistence.update(person)).build();
	}

//	@Path("inative/{id}")
//	@POST
//	@Produces("application/json")
//	public Response invative(@PathParam("id") String id) throws JSONException {
//		
//		
//		if (persistence == null) {
//			persistence = new PersistenceUserImp<Person>(Person.class);
//		}
//		return Response.status(200).entity(persistence.findById(id)).build();
//	}

	private static PersonRepository initRepository(RepositoryStrategy<Person> strategy) {
		PersonRepository repo = PersonRepository.getInstance();
		repo.init(strategy);
		return repo;
	}
}