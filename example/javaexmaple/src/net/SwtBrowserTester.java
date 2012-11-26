package net;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SwtBrowserTester {
    public static void main(String[] args) {
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        try {
            jep.setPage("http://www.oreilly.com");
        } catch (IOException e) {
            jep.setContentType("text/html");
            jep.setText("<html>Could not load http://www.oreilly.com </html>");
        }

        JScrollPane scrollPane = new JScrollPane(jep);
        JFrame f = new JFrame("O'Reilly & Associates");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(scrollPane);
        f.setSize(800, 600);
        f.setVisible(true);
    }
}
