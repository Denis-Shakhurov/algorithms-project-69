package hexlet.code;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    public static String normalizeWord(String word) {
        return Pattern.compile("\\w+")
                .matcher(word)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.joining());
    }

    public static boolean binarySearch(String[] words, String word) {
        Arrays.stream(words).sorted();
        int first = 0;
        int last = words.length - 1;
        while (first <= last) {
            int middle = (first + last) / 2;

            if (normalizeWord(word).equals(normalizeWord(words[middle]))) {
                return true;
            }

            if (normalizeWord(word).compareTo(normalizeWord(words[middle])) < 0) {
                last = middle - 1;
            } else {
                first = middle + 1;
            }
        }
        return false;
    }
}
