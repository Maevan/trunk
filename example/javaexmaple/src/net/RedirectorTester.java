package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 重定向使用样例
 * 
 * @author Lv9
 */
public class RedirectorTester {
    public static void main(String[] args) {
        String tosite = "http://hi.baidu.com/1d7500";
        int port = 8080;

        Redirector redirector = new Redirector(tosite, port);
        new Thread(redirector).start();
    }
}

class Redirector implements Runnable {
    private String tosite;
    private int port;

    public Redirector(String tosite, int port) {
        this.tosite = tosite;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Redirecting connections on port " + server.getLocalPort() + " to " + tosite);
            while (true) {
                Socket s = server.accept();
                Thread t = new RedirectThread(s);
                t.start();
            }
        } catch (IOException e) {}
    }

    class RedirectThread extends Thread {
        private Socket connection;

        public RedirectThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            Writer out = null;
            Reader in = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder request = new StringBuilder(80);
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) {
                        break;
                    }
                    request.append((char) c);
                }

                String get = request.toString();

                int firstSpace = get.indexOf(' ');
                int secondSpace = get.indexOf(' ', firstSpace + 1);

                String theFile = get.substring(firstSpace + 1, secondSpace);

                if (get.indexOf("HTTP") != -1) {
                    out.write("HTTP/1.0 302 FOUND\r\n");

                    out.write("Date: " + new Date() + "\r\n");
                    out.write("Server: Redirector 1.0\r\n");
                    out.write("Location: " + tosite + theFile + "\r\n");
                    out.write("ContentType: text/html\r\n\r\n");
                    out.flush();
                } else {
                    out.write("<HTML><HEAD><TITLE>Document moved</TITLE><HEAD>\r\n");
                    out.write("<BODY><HI>Document moved</HI>/r/n");
                    out.write("The document " + theFile + " has moved to\r\n <A HREF=\"" + tosite + theFile + "\">" + tosite + theFile + "</A>.\r\n Please update your bookmarkers</BODY></HTML>\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}