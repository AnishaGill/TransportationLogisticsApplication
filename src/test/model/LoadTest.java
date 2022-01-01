package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Load class Tests
public class LoadTest {
    private Load load1;
    private Load load2;
    private Load load3;


    @BeforeEach
    public void setUp() {
        load1 = new Load();
        load2 = new Load();
        load3 = new Load();

    }


    @Test
    public void testSetCompleted() {
        assertFalse(load1.status());
        load1.setCompleted();
        assertTrue(load1.status());
    }

    @Test
    public void testSetRateAndGetRate() {
        load1.setRate(200);
        load2.setRate(0);
        load3.setRate(1000);
        assertEquals(200, load1.getRate());
        assertEquals(0, load2.getRate());
        assertEquals(1000, load3.getRate());

    }

    @Test
    public void testSetPickUpLocationAndGetPickUpLocation() {
        load1.setPickUpLocation("The Oppy Group");
        assertEquals("The Oppy Group", load1.getPickUpLocation());
    }


    @Test
    public void testSetPickUpDAndGetPickUpDate() {
        load1.setPickUpDate("01.01.21");
        assertEquals("01.01.21", load1.getPickUpDate());
    }

    @Test
    public void testSetDropOffLocationAndGetDropOffLocation() {
        load1.setDropOffLocation("The Oppy Group");
        assertEquals("The Oppy Group", load1.getDropOffLocation());


    }

    @Test
    public void testSetDropOffDAndGetDropOffDate() {
        load1.setDropOffDate("10.15.21");
        assertEquals("10.15.21", load1.getDropOffDate());
    }

    @Test
    public void testSetGoodsAndGetGoods() {
        load1.setGoods("Apples");
        assertEquals("Apples", load1.getGoods());
    }

    @Test
    public void testSetDriverNameAndGetDriverName() {
        load1.setDriverName("Jatinder Gill");
        assertEquals("Jatinder Gill", load1.getDriverName());
    }

    @Test
    public void testSetStatus() {
        assertFalse(load1.status());
        load1.setStatus(true);
        assertTrue(load1.status());
        }


    }


