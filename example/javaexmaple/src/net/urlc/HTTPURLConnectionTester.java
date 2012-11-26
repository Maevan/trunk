package net.urlc;

import java.io.IOException;
import java.net.URL;

public class HTTPURLConnectionTester {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.baidu.com/img/slogo_kongzi_20110928_c24ba1eb5dbbc105a8ae1b6c9992d399.gif");
        System.err.println(url.openConnection().getContentLength());
    }
}
