package json;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class MapCovertJsonTester {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("①", 1);
        map.put("②", 2);
        map.put("③", 3);
        map.put("④", 4);
        map.put("⑤", 5);
        JSONObject object = new JSONObject();
        object.putAll(map);

        JSONObject object2 = JSONObject.fromObject(object.toString());

        map = (Map<String, Integer>) object2;

        System.err.println(map.get("④"));

        System.err.println(JSONObject.fromObject(map).toString());
    }
}
