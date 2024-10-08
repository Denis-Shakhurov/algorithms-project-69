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
        List<String> actual = SearchEngine.search(docs, "shoot");

        assertEquals(expected, actual);
        List<String> actualNull = SearchEngine.search(new ArrayList<Map<String, String>>(), "ol");
        assertEquals(new ArrayList<>(), actualNull);
    }

    @Test
    public void testMultiWordSearch() {
        var doc1 = "I can't shoot straight unless I've had a pint!";
        var doc2 = "Don't shoot shoot shoot that thing at me.";
        var doc3 = "I'm your shooter.";

        List<Map<String, String>> docs = List.of(
                Map.of("id", "doc1", "text", doc1),
                Map.of("id", "doc2", "text", doc2),
                Map.of("id", "doc3", "text", doc3)
        );

        List<String> expected = List.of("doc2", "doc1");
        List<String> actual = SearchEngine.search(docs, "shoot at me");

        assertEquals(expected, actual);
    }
}
