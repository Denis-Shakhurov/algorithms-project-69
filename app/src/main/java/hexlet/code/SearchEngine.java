package hexlet.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> docs, String text) {
        List<String> result = new ArrayList<>();

        if (docs.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, String>> list = new ArrayList<>();
        for (Map<String, String> map : docs) {
            if (!find(map, text).isEmpty()) {
                list.add(find(map, text));
            }
        }

        list.stream()
                .sorted(((o1, o2) -> o2.get("count").compareTo(o1.get("count"))))
                .forEach(map -> result.add(map.get("name")));

        return result;
    }

    public static Map<String, List<String>> index(List<Map<String, String>> list) {
        Map<String, List<String>> result = new HashMap<>();
        Set<String> set = new HashSet<>();
        for (Map<String, String> map : list) {
            String[] words = map.get("text").split(" ");
            set.addAll(Arrays.stream(words).toList());
        }
        for (String word : set) {
            for (Map<String, String> map : list) {
                String[] words = map.get("text").split(" ");
                if (Utils.binarySearch(words, word)) {
                    result.put(word, search(list, word));
                }
            }
        }
        return result;
    }

    private static Map<String, String> find(Map<String, String> doc, String text) {
        Map<String, String> result = new HashMap<>();
        String[] words = doc.get("text").split(" ");
        String[] wordsInText = text.split(" ");
        int count = 0;

        for (String wordText : wordsInText) {
            String actual = Utils.normalizeWord(wordText);
            for (String word : words) {
                String excepted = Utils.normalizeWord(word);
                if (excepted.equals(actual)) {
                    count++;
                }
            }
        }

        if (count != 0) {
            result.put("count", String.valueOf(count));
            result.put("name", doc.get("id"));
        }
        return result;
    }
}
