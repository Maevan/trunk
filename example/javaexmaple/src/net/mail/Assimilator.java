package net.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.mail.authenticator.MailAuthenticator;

public class Assimilator {
    static Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("net/mail/smtp.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Session connection = Session.getDefaultInstance(properties, new MailAuthenticator());
            Message message = new MimeMessage(connection);

            Address bill = new InternetAddress("god@microsoft.com(Bill)");
            Address to = new InternetAddress("os_zhaojianpeng@knet.cn");

            message.setContent("测试邮件", "text/plain;charset=utf-8");
            message.setFrom(bill);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject("测试JAVAMAIL");

            String str = InternetAddress.toString(new Address[] {
                            bill, to
            });
            System.out.println(str);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
