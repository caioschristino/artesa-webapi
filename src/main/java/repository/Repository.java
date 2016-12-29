package repository;

import strategy.RepositoryStrategy;

import java.util.List;

import com.mongodb.BasicDBObject;

public abstract class Repository<T extends beans.Entity> {

	private RepositoryStrategy<T> strategy;

	public void init(RepositoryStrategy<T> repositoryStrategy) {
		this.strategy = repositoryStrategy;
	}

	public T save(T entity) {
		return strategy.save(entity);
	}

	public List<T> list() {
		return strategy.list();
	}

	public T get(String id) {
		return strategy.get(id);
	}
	
	public T get(BasicDBObject query ) {
		return strategy.get(query);
	}

	public T update(T entity) {
		return strategy.update(entity);
	}

	public boolean remove(String id) {
		return strategy.remove(id);
	}
}