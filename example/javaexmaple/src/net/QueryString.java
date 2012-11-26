package net;

import java.net.URLEncoder;

public class QueryString {

    public static void main(String[] args) throws Exception {
        encoder("你好");
        byte b = -28;
        Integer i = b << 8;
        i = i | (-67 & 0xff);
        System.err.println((char) i.intValue());
    }

    public static String encoder(String test) throws Exception {
        char[] c = test.toCharArray();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < c.length; i++) {
            byte[] b = String.valueOf(c[i]).getBytes("UTF-8");
            if (b.length >= 2) {
                result.append("\\u").append(Integer.toHexString(c[i]).toUpperCase());
            } else {
                result.append(c[i]);
            }
        }
        return result.toString();
    }

    private StringBuilder query = new StringBuilder();

    public QueryString(String name, String value) {
        encode(name, value);
    }

    public void add(String name, String value) {
        query.append("&");
        encode(name, value);
    }

    private void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append("=");
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getQuery() {
        return query.toString();
    }

    public String toString() {
        return getQuery();
    }
}
