package net.urlc.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryResponseCache extends ResponseCache {
	private Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<URI, SimpleCacheResponse>();
	private int maxEntries;

	public MemoryResponseCache() {
		this(100);
	}

	public MemoryResponseCache(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	public CacheResponse get(URI uri, String method, Map<String, List<String>> headers) throws IOException {
		SimpleCacheResponse response = responses.get(uri);
		
		if (response != null && response.isExpired()) {
			responses.remove(uri);
			response = null;
		}
		
		return response;
	}

	@Override
	public CacheRequest put(URI uri, URLConnection uc) throws IOException {
		String cacheControl = uc.getHeaderField("Cache-Control");
		
		if (responses.size() >= maxEntries && cacheControl != null && cacheControl.indexOf("no-cache") != -1) {
			return null;
		}
		
		SimpleCacheRequest request = new SimpleCacheRequest();
		SimpleCacheResponse response = new SimpleCacheResponse(request, uc);

		OutputStream out = request.getBody();
		InputStream in = uc.getInputStream();
		byte[] buffer = new byte[8192];
		int len = -1;
		
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		
		out.flush();
		in.close();
		
		responses.put(uri, response);
		return request;
	}
}
