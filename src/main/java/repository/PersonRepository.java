package repository;

import beans.Person;

public class PersonRepository extends Repository<Person> {
	private static final PersonRepository INSTANCE = new PersonRepository();

	public static PersonRepository getInstance() {
		return INSTANCE;
	}

	private PersonRepository() {
	}
}