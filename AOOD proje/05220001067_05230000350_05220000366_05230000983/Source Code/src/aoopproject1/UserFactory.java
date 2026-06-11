package aoopproject1;

public class UserFactory {
    public static User create(String type, String name, String password) {
        if (type.equalsIgnoreCase("admin")) {
            return new Admin(name, password); // Create Admin user
        } else {
            return new User(name, password); // Create standard User
        }
    }
}
