package example;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutputStreamExample {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		// dout.writeChar('c');2
		// dout.writeInt(1); 4

		System.err.println(bout.toByteArray().length);
	}
}
