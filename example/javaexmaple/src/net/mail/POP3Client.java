package net.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class POP3Client {
    static Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("net/mail/pop3.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(properties);

        Store store = session.getStore("pop3");
        store.connect(properties.getProperty("mail.sender"), properties.getProperty("mail.senderpwd"));

        Folder inbox = store.getFolder("INBOX");

        if (inbox == null) {
            System.out.println("No INBOX");
            System.exit(1);
        }

        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();

        for (int i = 0; i < 20; i++) {
            System.err.println("-------------------------------------Message " + (i + 1));
            System.err.println(messages[i].getSubject());
        }

        inbox.close(false);
        store.close();
    }
}
