package net.urlc.cache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCacheResponse extends CacheResponse {
	private Map<String, List<String>> headers;
	private SimpleCacheRequest request;
	private Date expires;

	public SimpleCacheResponse(SimpleCacheRequest request, URLConnection uc) {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		String value = "";

		for (int i = 0;; i++) {
			String name = uc.getHeaderFieldKey(i);
			value = uc.getHeaderField(name);
			if (value == null || name == null) {
				break;
			}
			
			List<String> values = headers.get(name);

			if (values == null) {
				values = new ArrayList<String>();
				headers.put(name, values);
			}
			values.add(value);
		}

		long expiration = uc.getExpiration();// 获得网页建议的过期时间

		if (expiration != 0) {
			this.expires = new Date(expiration);
		}
		this.headers = Collections.unmodifiableMap(headers);
		this.request = request;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(request.getData());
	}

	@Override
	public Map<String, List<String>> getHeaders() throws IOException {
		return headers;
	}

	public boolean isExpired() {
		if (expires == null) {
			return false;
		} else {
			return expires.before(new Date());
		}
	}

}
