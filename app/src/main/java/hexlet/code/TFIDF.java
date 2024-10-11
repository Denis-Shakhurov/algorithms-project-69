package hexlet.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TFIDF {

    public static List<String> getSortedSearch(List<Map<String, String>> docs,
                                               Map<String, List<String>> index,
                                               String text) {

        List<Map<String, Double>> search = new ArrayList<>();
        String[] words = text.split(" ");
        for (String word : words) {
            for (Map<String, String> doc : docs) {
                String[] wordsInDoc = doc.get("text").split(" ");
                if (Utils.binarySearch(wordsInDoc, word)) {
                    String docName = doc.get("id");
                    double tfidf = getMultiWordTFIDF(docs, doc, index, text);
                    search.add(Map.of(docName, tfidf));
                }
            }
        }

        Comparator<Map<String, Double>> doubleByCount;
        doubleByCount = Comparator.comparingDouble(m -> m.entrySet().iterator().next().getValue());
        return search.stream()
                .sorted(doubleByCount.reversed())
                .map(m -> m.keySet().iterator().next())
                .toList();
    }

    public static double getMultiWordTFIDF(List<Map<String, String>> docs,
                                           Map<String, String> doc,
                                           Map<String, List<String>> index,
                                           String text) {
        String[] words = Utils.docToArray(doc);

        double result = 0;
        for (String word : words) {
            result += getWordTFIDF(docs, doc, index, word);
        }
        return result;
    }

    public static double getWordTFIDF(List<Map<String, String>> docs,
                                      Map<String, String> doc,
                                      Map<String, List<String>> index,
                                      String word) {
        double tf = getTF(doc, word);
        double idf = getIDF(docs, index, word);
        return tf * idf;
    }

    public static double getTF(Map<String, String> doc, String word) {
        String[] words = Utils.docToArray(doc);

        int count = (int) Arrays.stream(words)
                .filter(w -> w.equals(word))
                .count();
        return (double) count / words.length;
    }

    public static double getIDF(List<Map<String, String>> docs, Map<String, List<String>> index, String word) {
        int docsCount = docs.size();
        int termCount = index.get(word).size();
        return Math.log(1 + (docsCount - termCount + 1) / (termCount + 0.5));
    }
}
