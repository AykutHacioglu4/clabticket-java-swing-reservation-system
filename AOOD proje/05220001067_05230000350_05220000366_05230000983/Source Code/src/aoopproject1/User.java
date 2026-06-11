package aoopproject1;

public class User {
    protected String username; // Username of the user
    protected String password; // Password of the user

    public User(String username, String password) {
        this.username = username; // Set username
        this.password = password; // Set password
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password); // Check credentials
    }

    public String getUsername() {
        return username; // Return username
    }

    public String getFeatures() {
        return "Standard User"; // Default user role
    }
}
