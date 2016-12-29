package bridge.imp;

import bridge.PersistenceImplementor;
import strategy.MongoStrategy;
import strategy.RepositoryStrategy;

public class DabatasePersistenceImplementor<T extends beans.Entity> implements PersistenceImplementor<T> {
	private RepositoryStrategy<T> strategy;

	public DabatasePersistenceImplementor(Class<T> type) {
		this.strategy = new MongoStrategy<T>(type);
	}

	@Override
	public RepositoryStrategy<T> getStrategy() {
		// TODO Auto-generated method stub
		return this.strategy;
	}
}