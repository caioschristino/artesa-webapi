package strategy;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import util.JsonUtils;

import org.bson.types.ObjectId;
import org.eclipse.jetty.util.log.Log;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoStrategy<T extends beans.Entity> implements RepositoryStrategy<T> {
	private final Class<T> type;

	private final String collection;
	private DB db;

	public MongoStrategy(Class<T> type) {
		this.type = type;
		this.collection = type.getSimpleName();
		try {
			this.db = getDB(collection);
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DBCollection getCollection() {
		return this.db.getCollection(this.collection);
	}

	@Override
	public T save(T entity) {
		BasicDBObject doc = (BasicDBObject) JSON.parse(new Gson().toJson(entity));
		getCollection().insert(doc);
		entity.setId(doc.getObjectId("_id"));

		return entity;
	}

	@Override
	public List<T> list() {
		List<T> entities = new ArrayList<T>();
		DBCursor cursor = getCollection().find();
		try {
			while (cursor.hasNext()) {
				BasicDBObject doc = (BasicDBObject) cursor.next();
				T entity = new Gson().fromJson(doc.toString(), type);
				
				
				entities.add(entity);
			}
		} finally {
			cursor.close();
		}
		return entities;
	}

	@Override
	public T get(String id) {
		T entity = null;
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBCursor cursor = getCollection().find(query);
		try {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			if (doc != null) {
				entity = JsonUtils.deserialize(doc.toString(), type);
				entity.setId(doc.getObjectId("_id"));
			}
		} finally {
			cursor.close();
		}
		return entity;
	}

	@Override
	public T update(T entity) {
		BasicDBObject query = new BasicDBObject("_id", entity.getId());
		BasicDBObject doc = (BasicDBObject) JSON.parse(new Gson().toJson(entity));
		getCollection().update(query, doc);
		entity.setId(doc.getObjectId("_id"));

		return entity;
	}

	@Override
	public boolean remove(String id) {
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		WriteResult result = getCollection().remove(query);

		return result.getN() != 0;
	}

	@Override
	public DB getDB(String collection) throws MongoException, UnknownHostException {
		String uri = (System.getenv("MONGOHQ_URL") != null && !System.getenv("MONGOHQ_URL").isEmpty())
				? System.getenv("MONGOHQ_URL") : "mongodb://heroku:iCLguEwWkb5ohXKm2oLU3pvZFvSHdK0B70JKQMJD7Q_cmOtfI98q-ERAdvaiuFGcdjVUIaNPRAycofxKS3DV7g@candidate.45.mongolayer.com:11129,candidate.14.mongolayer.com:11391/app60492730";

		MongoURI mongoURI = new MongoURI(uri);
		DB db = mongoURI.connectDB();
		if (mongoURI.getUsername() != null && mongoURI.getPassword() != null) {
			db.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
		}
		return db;
	}

	@Override
	public T get(BasicDBObject query) {
		List<T> entities = new ArrayList<T>();
		DBCursor cursor = getCollection().find(query);
		try {
			while (cursor.hasNext()) {
				BasicDBObject doc = (BasicDBObject) cursor.next();

				try {
					T entity = new Gson().fromJson(doc.toString(), type);
					entities.add(entity);
				} catch (Exception e) {
					Log.setLogToParent(e.getMessage());
				}
			}
		} finally {
			cursor.close();
		}
		return entities.get(0);
	}
}
