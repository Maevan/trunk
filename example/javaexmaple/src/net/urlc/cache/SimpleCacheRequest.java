package net.urlc.cache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;

public class SimpleCacheRequest extends CacheRequest {
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Override
	public void abort() {
		out = null;
	}

	@Override
	public OutputStream getBody() throws IOException {
		return out;
	}

	public byte[] getData() {
		if (out == null) {
			return null;
		} else {
			return out.toByteArray();
		}
	}
}
