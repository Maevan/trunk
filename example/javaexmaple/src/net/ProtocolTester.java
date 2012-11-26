package net;

import java.net.MalformedURLException;
import java.net.URL;

public class ProtocolTester {
    public static void main(String[] args) {
        testProtocol("http://www.adc.org");// 超文本传输协议http
        testProtocol("https://www.amazon.com/exec/obidos/order2/"); // 安全http
        testProtocol("ftp://metalab.unc.edu/pub/lanaguages/java/javafag/");// 文件传输协议
        testProtocol("mailto:elharo@metalab.unc.edu");// 简单邮件传输协议
        testProtocol("telnet://dibner.poly.edu");// telnet
        testProtocol("file://c:/");// 本地文件访问
        testProtocol("gopher://gopher.anc.org.za/");// gopher
        testProtocol("ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");// 轻量级目录访问
        testProtocol("jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!" + "/com/macfaq/io/StreamCopier.class");// JAR
        testProtocol("nfs://utopia.poly.edu/usr/tmp");// NFS,网络文件系统
        testProtocol("jdbc:mysql://luna.metalab.unc.edu:3306/NEWS");// JDBC 定制协议
        testProtocol("rmi://metalab.unc.edu/RenderEngine"); // rmi 远程方法调用定制协议

        // HotJava定制协议
        testProtocol("doc:/UsersGuide/release.html");
        testProtocol("netdoc:/UsersGuide/release.html");
        testProtocol("systemresource://www.adc.org/+/index.html");
    }

    public static void testProtocol(String url) {
        try {
            URL u = new URL(url);
            System.out.println(u.getProtocol() + " is supported.");
        } catch (MalformedURLException e) {
            String protocol = url.substring(0, url.indexOf(":"));
            System.err.println(protocol + " is not supported.");
        }
    }
}
