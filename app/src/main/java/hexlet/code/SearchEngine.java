package hexlet.code;

import java.util.ArrayList;
import java.util.Arrays;
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

        docs.stream()
                .filter(map -> find(map, text))
                .forEach(map -> result.add(map.get("id")));
        return result;
    }

    private static boolean find(Map<String, String> doc, String text) {
        boolean result = false;
        String[] words = doc.get("text").split(" ");

        String actual = normalizeWord(text);
        for (String word : words) {
            String excepted = normalizeWord(word);
            if (excepted.equals(actual)) {
                result = true;
            }
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
