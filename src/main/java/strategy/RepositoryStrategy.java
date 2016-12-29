package strategy;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoException;

import beans.Entity;

public interface RepositoryStrategy<T extends Entity> {
	T save(T entity);

	List<T> list();

	T get(String id);

	T get(BasicDBObject query);

	T update(T entity);

	boolean remove(String id);

	DB getDB(String collection) throws MongoException, UnknownHostException;
}
