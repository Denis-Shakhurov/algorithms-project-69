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

    private static Map<String, String> find(Map<String, String> doc, String text) {
        Map<String, String> result = new HashMap<>();
        String[] words = doc.get("text").split(" ");
        String[] wordsInText = text.split(" ");
        int count = 0;

        for (String wordText : wordsInText) {
            String actual = normalizeWord(wordText);
            for (String word : words) {
                String excepted = normalizeWord(word);
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

    private static String normalizeWord(String word) {
        return Pattern.compile("\\w+")
                .matcher(word)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }
}
