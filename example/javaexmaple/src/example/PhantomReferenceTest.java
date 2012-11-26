package example;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;

public class PhantomReferenceTest {

	public static void main(String[] args) {
		ReferenceQueue referenceQueue = new ReferenceQueue();
		Object object = new Object() {
			public String toString() {
				return "Referenced Object";
			}
		};

		Object data = new Object() {
			public String toString() {
				return "Data";
			}
		};
		
		HashMap map = new HashMap();
		Reference reference = null;
		System.out.println("Testing PhantomReference.");
		reference = new PhantomReference(object, referenceQueue);

		map.put(reference, data);

		System.out.println(reference.get()); // null
		System.out.println(map.get(reference)); // Data
		System.out.println(reference.isEnqueued()); // false

		System.gc();
		System.out.println(reference.get()); // null
		System.out.println(map.get(reference)); // Data
		System.out.println(reference.isEnqueued()); // false

		object = null;
		data = null;

		System.gc();
		System.out.println(reference.get()); // null
		System.out.println(map.get(reference)); // Data
		System.out.println(reference.isEnqueued()); // true, because object has
													// been reclaimed.
	}

}