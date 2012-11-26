package example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Example {
	public static void main(String[] args) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("sha-1");
		String s1 = new String(digest.digest("YiQunSongShu4845849".getBytes()), "utf-8");
		digest.reset();
		byte[] d = digest.digest(("0e6faaf4ab42dc3d8534d595f7634688fb73c18d" + s1).getBytes());

		System.err.println(new String(d));
	}
}
