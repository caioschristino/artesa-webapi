package bridge;

import java.util.List;

import beans.Person;

public interface PersistenceUser {
	/**
	 * @param object
	 * @return returns objectID
	 */
	public Person save(Person person);

	/**
	 * 
	 * @param objectId
	 * @return persisted Object
	 */
	public Person findById(String id);

	/**
	 * 
	 * @param id
	 */
	public Person update(Person person);
	
	/**
	 * 
	 * @param id
	 */
	public List<Person> getAll();

	/**
	 * 
	 * @param email
	 * @param password
	 */
	public Person isValidCredentials(String email, String password);
}