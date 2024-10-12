package hexlet.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TFIDF {

    public static List<String> getSortedSearch(List<Map<String, String>> docs,
                                                Map<String, Long> index,
                                                String sentence) {
        if (docs.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Double>> docScores = new ArrayList<>();
            for (Map<String, String> doc : docs) {
                String docId = doc.get("id");
                double score = getSentenceTFIDF(docs, doc, index, sentence);
                docScores.add(Map.of(docId, score));
            }

        Comparator<Map<String, Double>> byDoubleCount;
        byDoubleCount = Comparator.comparingDouble(m -> m.entrySet().iterator().next().getValue());
        return docScores.stream()
                .sorted(byDoubleCount.reversed())
                .map(x -> x.keySet().iterator().next())
                .collect(Collectors.toList());
    }



    public static double getSentenceTFIDF(List<Map<String, String>> docs,
                                          Map<String, String> doc,
                                          Map<String, Long> index,
                                          String sentence) {
        List<String> words = Utils.getSplittedText(sentence);
        double result = 0;

        for (String word : words) {
            result += getWordTFIDF(docs, doc, index, word);
        }

        return result;
    }

    public static double getWordTFIDF(List<Map<String, String>> docs,
                                      Map<String, String> doc,
                                      Map<String, Long> index,
                                      String word) {

        double tf = getTF(doc, word);
        double idf = getIDF(docs, index, word);
        return tf * idf;
    }

    public static double getIDF(List<Map<String, String>> docs,
                                Map<String, Long> index,
                                String word) {
        double docsCount = docs.size();
        long termCount = index.get(word);
        return Math.log(1 + (docsCount - termCount + 1) / (termCount + 0.5));
        //return Math.log10((double) docs.size() / index.get(word));

    }

    public static double getTF(Map<String, String> doc, String word) {
        long numberOfTheCertainWordInDocument = getNumberOfWordsInDocument(doc, word);
        long numberOfWordsInDocument = getNumberOfWordsInDocument(doc);
        return (double) numberOfTheCertainWordInDocument / numberOfWordsInDocument;
    }

    public static long getNumberOfWordsInDocument(Map<String, String> doc) {
        String text = doc.get("text");
        List<String> words = Utils.getSplittedText(text);
        return words.size();
    }

    public static long getNumberOfWordsInDocument(Map<String, String> doc, String word) {
        String text = doc.get("text");
        String term = Utils.normalizeWord(word);
        List<String> words = Utils.getSplittedText(text);
        return words.stream()
                .filter(x -> x.equals(term))
                .count();

    }

    public static List<String> sortIndex(List<Map<String, Double>> index) {

        Comparator<Map<String, Double>> byDoubleCount;
        byDoubleCount = Comparator.comparingDouble(m -> m.entrySet().iterator().next().getValue());
        return index.stream()
                .sorted(byDoubleCount.reversed())
                .map(x -> x.keySet().iterator().next())
                .collect(Collectors.toList());
    }
}
