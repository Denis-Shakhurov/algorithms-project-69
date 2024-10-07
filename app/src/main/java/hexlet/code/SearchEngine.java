package hexlet.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> docs, String text) {
        List<String> result = new ArrayList<>();

        if (docs.isEmpty()) {
            return new ArrayList<>();
        }

        docs.stream()
                .filter(map -> find(map, text))
                .forEach(map -> result.add(map.get("id")));
        return result;
    }

    public static boolean find(Map<String, String> doc, String text) {
        boolean result = false;
        String[] words = doc.get("text").split(" ");

        Arrays.asList(words).stream()
                .filter(w -> w.endsWith("[.,;:!?-]"))
                .forEach(w -> w.substring(w.length()));

        for (String word : words) {
            if (word.equals(text)) {
                result = true;
            }
        }
        return result;
    }
}
