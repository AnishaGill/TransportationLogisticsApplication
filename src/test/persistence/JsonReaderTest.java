package persistence;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.Load;
import model.Orderbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    // Modeled off of JsonSerialization

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Orderbook or = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrderBook.json");
        try {
            Orderbook or = reader.read();
            assertEquals(0, or.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOrderbook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrderbook.json");
        try {
            Orderbook or = reader.read();
            assertEquals(2, or.size());
            Load l1 = or.findID(1);
            assertEquals(200,l1.getRate());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
