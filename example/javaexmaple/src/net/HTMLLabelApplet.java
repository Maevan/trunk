package net;

import javax.swing.*;

@SuppressWarnings("serial")
public class HTMLLabelApplet extends JApplet {

	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new HTMLLabelApplet());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void init() {
		JLabel theText = new JLabel("<html>Hello! this is a " + "mult" +
				"iline label with <b>bold</b> " + "and <i>italic</i> text. <p>"
				+ "It can use paragraphs,horizontal lines,<hr> " + "and most of the other basic features of HTML 3.2</html>");
		this.getContentPane().add(theText);
	}
}
