package aoopproject1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SeatReserveCommandTest {

    private Trip trip;
    private SeatReserveCommand command;

    @Before
    public void setUp() {
        // Create a sample trip with 5 available seats
        trip = new Trip("TR001", "Istanbul", "Ankara", "2025-06-20", 5, "Bus");
        // Create a SeatReserveCommand for seat index 2 and user "john"
        command = new SeatReserveCommand(trip, 2, "john");
    }

    @After
    public void tearDown() {
        // Cleanup after each test
        trip = null;
        command = null;
    }

    /**
     * Test of execute method in SeatReserveCommand class.
     * It should reserve the specified seat for the given user.
     */
    @Test
    public void testExecute() {
        // Execute the seat reservation command
        command.execute();

        // Assert that the seat was reserved by the correct user
        assertEquals("john", trip.getReservationOwner(2));
        assertTrue(trip.getSeats().get(2));
    }
}
