package beans;

import org.bson.types.ObjectId;

public abstract class Entity {
	public abstract String getNameDocument();
	
	public ObjectId _id;
	
	public ObjectId getId() {
		return _id;
	}
	
	public void setId(ObjectId id) {
		this._id = id;
	}
}