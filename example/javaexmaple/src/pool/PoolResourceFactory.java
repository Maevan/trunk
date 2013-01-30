package pool;

public interface PoolResourceFactory<T extends PoolResource> {
	public T generateResource();
}
