package hexlet.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    public static void addIndex(Map<String, List<String>> index, String word, String docId) {
        if (!index.containsKey(word)) {
            index.put(word, new ArrayList<>(Collections.singleton(docId)));
        } else {
            List<String> list = index.get(word);
            if (!list.contains(docId)) {
                list.add(docId);
            }
        }
    }

    public static Map<String, Long> getIndex(List<Map<String, String>> docs) {
        Map<String, List<String>> index = new HashMap<>();
        Map<String, Long> result = new HashMap<>();
        for (Map<String, String> doc : docs) {
            String docId = doc.get("id");
            String text = doc.get("text");
            List<String> words = Utils.getSplittedText(text);
            for (String word : words) {
                addIndex(index, word, docId);
            }
        }
        for (Map.Entry<String, List<String>> entry : index.entrySet()) {
            String word = entry.getKey();
            long count = entry.getValue().size();
            result.put(word, count);
        }
        return result;
    }
}
