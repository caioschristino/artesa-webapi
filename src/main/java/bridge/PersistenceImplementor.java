package bridge;

import strategy.RepositoryStrategy;

public interface PersistenceImplementor<T extends beans.Entity> {
	RepositoryStrategy<T> getStrategy();
}