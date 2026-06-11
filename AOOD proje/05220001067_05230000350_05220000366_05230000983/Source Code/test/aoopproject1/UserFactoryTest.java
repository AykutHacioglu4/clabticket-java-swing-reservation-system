package aoopproject1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserFactoryTest {

    @Before
    public void setUp() {
        // Setup logic before each test if needed
    }

    @After
    public void tearDown() {
        // Cleanup logic after each test if needed
    }

    /**
     * Test of create method, of class UserFactory.
     */
    @Test
    public void testCreateStandardUser() {
        // Given a type "standard", it should return a User instance
        String type = "standard";
        String name = "john";
        String password = "1234";

        User result = UserFactory.create(type, name, password);

        assertNotNull(result); // It should not return null
        assertEquals("john", result.getUsername()); // Username should match
        assertTrue(result.login("john", "1234")); // Login should succeed
        assertEquals("Standard User", result.getFeatures()); // Feature check
    }

    @Test
    public void testCreateUnknownType() {
        User result = UserFactory.create("unknown", "jane", "pass");
        assertNotNull(result); // ✔️ It should still return a standard User
        assertTrue(result instanceof User); // ✔️ Should be a User
        assertFalse(result instanceof Admin); // ✔️ Should not be Admin
    }

}
