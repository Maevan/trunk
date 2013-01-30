package pool.exceptions;

@SuppressWarnings("serial")
public class ClosedException extends RuntimeException {
	public ClosedException() {
		super();
	}

	public ClosedException(String message) {
		super(message);
	}
}
