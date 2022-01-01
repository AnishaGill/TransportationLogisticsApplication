package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

// Orderbook class Tests
public class OrderbookTest {
    private Orderbook orderbook;


    @BeforeEach
    public void setUp() {
        orderbook = new Orderbook();
        orderbook.addLoad();
        orderbook.addLoad();
        orderbook.addLoad();
    }

    @Test
    public void testAddLoad() {
        assertEquals(3, orderbook.size());

        orderbook.addLoad(); // 1
        orderbook.addLoad(); // 2
        orderbook.addLoad(); // 3

        assertNotNull(orderbook.findID(1));
        assertEquals(6, orderbook.size());

    }


    @Test
    public void testFindID() {

        Load ld1 = orderbook.findID(2);
        assertEquals(2, (ld1.getTripNum()));

    }


    @Test
    public void testFilterUncomplete() {

        ArrayList<String> filteredList = orderbook.filterUncompleted();
        assertEquals(3, filteredList.size());

    }


    @Test
    public void testFilterUncompleteAllLoadsCompleted() {

        orderbook.findID(1);
        orderbook.markLoadsAsComplete(1);

        orderbook.findID(2);
        orderbook.markLoadsAsComplete(2);

        orderbook.findID(3);
        orderbook.markLoadsAsComplete(3);

        ArrayList<String> filteredList = orderbook.filterUncompleted();

      assertEquals(0,filteredList.size());

    }





    @Test
    public void testMarkLoadAsCompleted() {

        Load ldFirst = orderbook.findID(1);
        orderbook.markLoadsAsComplete(1);
        assertTrue(ldFirst.status());

        // know that loads are made status = false

    }


    @Test
    public void testFullOrderbook() {
        ArrayList<String> tripBook = orderbook.fullOrderbook();
        assertEquals(3, tripBook.size());

        for (int i = 1; i < 4; i++) {
            assertTrue(tripBook.contains(Integer.toString(i)));
        }
    }


    @Test
    public void testSetLoad() {
        Orderbook or1 = new Orderbook();
        Load l1 = new Load();
        or1.setLoad(l1);
        assertEquals(1,or1.size());
    }


}
















