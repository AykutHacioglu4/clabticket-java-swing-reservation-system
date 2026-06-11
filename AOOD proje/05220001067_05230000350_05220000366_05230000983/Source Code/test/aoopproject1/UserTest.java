package aoopproject1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("testuser", "1234");
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void testLoginSuccess() {
        boolean result = user.login("testuser", "1234");
        assertTrue(result); 
    }

    @Test
    public void testLoginFailWrongPassword() {
        boolean result = user.login("testuser", "wrongpass");
        assertFalse(result); 
    }

    @Test
    public void testLoginFailWrongUsername() {
        boolean result = user.login("wronguser", "1234");
        assertFalse(result); 
    }

    @Test
    public void testGetUsername() {
        assertEquals("testuser", user.getUsername());
    }

    @Test
    public void testGetFeatures() {
        assertEquals("Standard User", user.getFeatures()); 
    }

}
