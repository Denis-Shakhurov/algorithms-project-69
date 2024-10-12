package hexlet.code;

import java.util.List;
import java.util.Map;

public class SearchEngine {

    public static List<String> search(List<Map<String, String>> docs, String text) {
        Map<String, Long> index = Index.getIndex(docs);
        List<String> list = Search.scoreDQ(docs, index, text);
        List<String> result = TFIDF.getSortedSearch(docs, index, text);
        return result;
    }
}
