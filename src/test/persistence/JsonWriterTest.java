package persistence;

import com.sun.org.apache.xpath.internal.operations.Or;
import model.Load;
import model.Orderbook;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    // Modeled off of JsonSerialization


    @Test
    void testWriterInvalidFile() {
        try {
            Orderbook or = new Orderbook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }



    @Test
    void testWriterEmptyWorkroom() {
        try {
            Orderbook or = new Orderbook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyOrderbook.json");
            writer.open();
            writer.write(or);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyOrderbook.json");
            or = reader.read();
            assertEquals(0, or.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



    @Test
    void testWriterGeneralOrderbook() {
        try {
            Orderbook or = new Orderbook();
            or.addLoad();
            Load l1 = or.findID(1);
            l1.setGoods("apples");
            l1.setRate(200);
            l1.setPickUpDate("10.10.21");
            l1.setPickUpLocation("A1");
            l1.setDropOffDate("10.10.21");
            l1.setDropOffLocation("B1");
            l1.setDriverName("Jaga");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralOrderbook.json");
            writer.open();
            writer.write(or);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralOrderbook.json");
            or = reader.read();
            Load l3 = or.findID(1);

            assertEquals(1, or.size());
            assertEquals("apples",l3.getGoods());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralOrderbookReEdit() {
        try {
            Orderbook or = new Orderbook();
            or.addLoad();
            Load l1 = or.findID(1);
            l1.setGoods("apples");
            l1.setRate(200);
            l1.setPickUpDate("10.10.21");
            l1.setPickUpLocation("A1");
            l1.setDropOffDate("10.10.21");
            l1.setDropOffLocation("B1");
            l1.setDriverName("Jaga");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralOrderbook.json");
            writer.open();
            writer.write(or);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralOrderbook.json");
            or = reader.read();
            Load l3 = or.findID(1);

            l3.setRate(300);

            writer.open();
            writer.write(or);
            writer.close();

            or = reader.read();
            l3 = or.findID(1);

            assertEquals(300, l3.getRate());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
