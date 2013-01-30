package pool;

public interface PoolResource {
	public void close();

	public boolean check();
}
