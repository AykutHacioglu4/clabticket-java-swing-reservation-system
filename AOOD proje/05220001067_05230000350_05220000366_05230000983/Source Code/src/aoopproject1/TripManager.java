package aoopproject1;

import java.util.ArrayList;
import java.util.List;

public class TripManager extends Observable {
    private List<Trip> tripList = new ArrayList<>(); // List of all trips

    public void addTrip(Trip trip) {
        tripList.add(trip); // Add new trip to the list
        notifyObservers(); // Notify observers (e.g., UI)
    }

    public boolean deleteTrip(String id) {
        Trip target = null;
        for (Trip t : tripList) {
            if (t.getId().equalsIgnoreCase(id)) {
                target = t; // Find trip by ID (case-insensitive)
                break;
            }
        }
        if (target != null) {
            tripList.remove(target); // Remove trip
            notifyObservers(); // Notify UI
            return true;
        }
        return false; // Trip not found
    }

    public Trip findTrip(String id) {
        for (Trip t : tripList) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t; // Return trip if found
            }
        }
        return null; // Trip not found
    }

    public List<Trip> getAllTrips() {
        return new ArrayList<>(tripList); // Return copy of trip list
    }
}
