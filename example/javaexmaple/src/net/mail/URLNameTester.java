package net.mail;

import javax.mail.URLName;

public class URLNameTester {
    public static void main(String[] args) {
        URLName urlName = new URLName("pop3", "pop3.knet.cn", 80, "INBOX", "os_zhaojianpeng@knet.cn", "sd7032999");
        
        System.err.println(urlName.toString());
    }
}
