package aoopproject1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CancelReservationCommandTest {
    
    public CancelReservationCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testExecute() {
        Trip trip = new Trip("T01", "Istanbul", "Ankara", "2025-06-20", 5, "Bus");
        trip.reserveSeat(2, "user1");

        CancelReservationCommand command = new CancelReservationCommand(trip, 2, "user1");
        command.execute();

        assertNull(trip.getReservationOwner(2)); 
    }

    
}
