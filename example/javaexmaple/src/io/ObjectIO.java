package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ObjectIO {
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
        String key = "sdggsgsdgsdgsdgwww";

        Map<String, String> sourcemap = new HashMap<String, String>();

        sourcemap.put(key, key);
        writeObject(sourcemap);
        Map<String, String> map = (Map<String, String>) readObject();

        System.err.println(map.containsKey(key));
    }

    public static Object readObject() throws IOException, ClassNotFoundException {
        ObjectInputStream objin = new ObjectInputStream(new FileInputStream("D:/obj"));

        try {
            return objin.readObject();
        } finally {
            objin.close();
        }
    }

    public static void writeObject(Object obj) throws IOException {
        ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream("D:/obj"));

        objout.writeObject(obj);
        objout.flush();
        objout.close();
    }
}
