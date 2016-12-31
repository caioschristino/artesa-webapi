package bridge.imp;

import bridge.PersistenceUser;
import repository.PersonRepository;
import strategy.RepositoryStrategy;

import java.util.List;

import com.mongodb.BasicDBObject;

import beans.Person;
import bridge.PersistenceImplementor;

public class PersistenceUserImp<T extends beans.Entity> implements PersistenceUser {
	private final PersistenceImplementor<T> implementor;
	private PersonRepository repo;

	public PersistenceUserImp(Class<T> type) {
		this.implementor = new DabatasePersistenceImplementor<T>(type);

		if (this.implementor != null) {
			repo = PersonRepository.getInstance();
			repo.init((RepositoryStrategy<Person>) this.implementor.getStrategy());
		}
	}

	@Override
	public Person save(Person person) {
		// TODO Auto-generated method stub
		return repo.save(person);
	}

	@Override
	public Person findById(String id) {
		// TODO Auto-generated method stub
		return repo.get(id);
	}

	@Override
	public Person update(Person person) {
		return repo.update(person);
	}

	@Override
	public Person isValidCredentials(String email, String password) {
		BasicDBObject query = new BasicDBObject();
		query.put("email", email);
		query.put("password", password);
		
		return repo.get(query);
	}
	
	@Override
	public List<Person> getAll() {
		// TODO Auto-generated method stub
		return repo.list();
	}

	@Override
	public Person isValidCredentials(String idIn) {
		BasicDBObject query = new BasicDBObject();
		query.put("idIn", idIn);
		
		return repo.get(query);
	}
}