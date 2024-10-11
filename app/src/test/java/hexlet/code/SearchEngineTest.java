package hexlet.code;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEngineTest {

    @Test
    public void testSearch() {
        String doc1 = "I can't shoot straight unless I've had a pint!";
        String doc2 = "Don't shoot shoot shoot that thing at me.";
        String doc3 = "I'm your shooter.";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );

        List<String> expected = List.of("doc2", "doc1");
        List<String> actual = SearchEngine.getSearch(docs, "shoot");

        assertEquals(expected, actual);
        List<String> actualNull = SearchEngine.getSearch(new ArrayList<Map<String, String>>(), "ol");
        assertEquals(new ArrayList<>(), actualNull);
    }

    @Test
    public void testFindDoc() {
        String doc1 = "Don't shoot shoot shoot that thing at me.";
        Map<String, String> map = Map.of("id", "doc1", "text", doc1);

        Map<String, String> result = SearchEngine.findDoc(map, "shoot");
        int expected = 3;
        int actual = Integer.parseInt(result.get("count"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetIndex() {
        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", "some text"),
                Map.of("id", "doc2", "text", "some text too")
        );

        Map<String, List<String>> expected = Map.of(
                "some", List.of("doc1", "doc2"),
                "text", List.of("doc1", "doc2"),
                "too", List.of("doc2")
        );

        Map<String, List<String>> actual = SearchEngine.getIndex(docs);

        assertEquals(expected, actual);
    }
}
