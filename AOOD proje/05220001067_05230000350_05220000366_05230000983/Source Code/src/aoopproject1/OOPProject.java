package aoopproject1;

import java.util.*;

public class OOPProject {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>(); // List to store users
        users.add(UserFactory.create("admin", "admin", "1234")); // Create admin user
        users.add(UserFactory.create("user", "user", "pass")); // Create normal user

        TripManager tripManager = new TripManager(); // Create trip manager

        // Add sample trips (bus and plane)
        tripManager.addTrip(new Trip("B1", "Istanbul", "Ankara", "2025-06-01", 24, "bus"));
        tripManager.addTrip(new Trip("B2", "Izmir", "Bursa", "2025-06-05", 16, "bus"));
        tripManager.addTrip(new Trip("U1", "Istanbul", "Berlin", "2025-06-15", 41, "plane"));
        tripManager.addTrip(new Trip("U2", "Antalya", "London", "2025-07-01", 32, "plane"));
        tripManager.addTrip(new Trip("B3", "Dinar", "Sandikli", "2025-12-01", 20, "bus"));
        tripManager.addTrip(new Trip("B4", "Urla", "Menemen", "2026-03-23", 32, "bus"));
        tripManager.addTrip(new Trip("U3", "Paris", "London", "2025-06-23", 48, "plane"));
        tripManager.addTrip(new Trip("U4", "Moscow", "Antalya", "2026-02-01", 56, "plane"));
        tripManager.addTrip(new Trip("U5", "Moscow", "Antalya", "2026-02-01", 24, "plane"));

        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame(users, tripManager); // Launch login screen
        });
    }
}
