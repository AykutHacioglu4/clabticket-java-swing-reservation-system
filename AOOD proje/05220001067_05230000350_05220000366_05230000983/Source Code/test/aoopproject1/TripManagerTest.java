package aoopproject1;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TripManagerTest {

    private TripManager manager;
    private Trip trip;

    @Before
    public void setUp() {
        manager = new TripManager();
        trip = new Trip("TR001", "Istanbul", "Ankara", "2025-06-20", 5, "Bus");
    }

    @After
    public void tearDown() {
        manager = null;
        trip = null;
    }

    @Test
    public void testAddTrip() {
        manager.addTrip(trip);
        Trip result = manager.findTrip("TR001");
        assertNotNull(result);
        assertEquals("TR001", result.getId());
    }

    @Test
    public void testDeleteTrip() {
        manager.addTrip(trip);
        boolean deleted = manager.deleteTrip("TR001");
        assertTrue(deleted);

        Trip deletedTrip = manager.findTrip("TR001");
        assertNull(deletedTrip);
    }

    @Test
    public void testFindTrip() {
        manager.addTrip(trip);
        Trip found = manager.findTrip("TR001");
        assertNotNull(found);
        assertEquals("Istanbul", found.getDeparture());
    }

    @Test
    public void testGetAllTrips() {
        manager.addTrip(trip);
        List<Trip> allTrips = manager.getAllTrips();
        assertEquals(1, allTrips.size());
        assertEquals("TR001", allTrips.get(0).getId());
    }
}
