package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> docs, String text) {
        List<String> result = new ArrayList<>();

        if (docs.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, String> sorted = new HashMap<>();
        for (Map<String, String> map : docs) {
            if (!find(map, text).isEmpty()) {
                sorted.putAll(find(map, text));
            }
        }
        sorted.keySet()
                .stream()
                .sorted(((o1, o2) -> o2.compareTo(o1)))
                .forEach(k -> result.add(sorted.get(k)));

        return result;
    }

    private static Map<Integer, String> find(Map<String, String> doc, String text) {
        Map<Integer, String> result = new HashMap<>();
        String[] words = doc.get("text").split(" ");
        int count = 0;

        String actual = normalizeWord(text);
        for (String word : words) {
            String excepted = normalizeWord(word);
            if (excepted.equals(actual)) {
                count++;
            }
        }
        if (count != 0) {
            result.put(count, doc.get("id"));
        }
        return result;
    }

    private static String normalizeWord(String word) {
        return Pattern.compile("\\w+")
                .matcher(word)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }
}
