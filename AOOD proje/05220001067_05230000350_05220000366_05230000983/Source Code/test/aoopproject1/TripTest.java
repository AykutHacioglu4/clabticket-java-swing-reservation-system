package aoopproject1;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TripTest {

    private Trip instance;

    @Before
    public void setUp() {
        instance = new Trip("TR001", "Istanbul", "Ankara", "2025-06-20", 5, "Bus");
    }

    @After
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testReserveSeat() {
        boolean result = instance.reserveSeat(0, "user1");
        assertTrue(result); 
    }

    @Test
    public void testCancelSeat() {
        instance.reserveSeat(1, "user2");
        boolean result = instance.cancelSeat(1, "user2");
        assertTrue(result); 
    }

    @Test
    public void testGetReservationOwner() {
        instance.reserveSeat(2, "user3");
        String result = instance.getReservationOwner(2);
        assertEquals("user3", result);
    }

    @Test
    public void testIsOpen() {
        assertTrue(instance.isOpen()); 
    }

    @Test
    public void testSetOpen() {
        instance.setOpen(false);
        assertFalse(instance.isOpen()); 
    }

    @Test
    public void testGetId() {
        assertEquals("TR001", instance.getId());
    }

    @Test
    public void testGetDeparture() {
        assertEquals("Istanbul", instance.getDeparture());
    }

    @Test
    public void testGetArrival() {
        assertEquals("Ankara", instance.getArrival());
    }

    @Test
    public void testGetDate() {
        assertEquals("2025-06-20", instance.getDate());
    }

    @Test
    public void testGetType() {
        assertEquals("Bus", instance.getType());
    }

    @Test
    public void testGetSeats() {
        List<Boolean> seats = instance.getSeats();
        assertEquals(5, seats.size()); // 5 koltuk olmalı
    }

    @Test
    public void testGetVehicleType() {
        assertEquals("Bus", instance.getVehicleType());
    }

    @Test
    public void testSetVehicleType() {
        instance.setVehicleType("Plane");
        assertEquals("Plane", instance.getVehicleType());
    }
}
