package aoopproject1;

import java.util.ArrayList;
import java.util.List;

public class Trip extends Observable {
    private String id, departure, arrival, date, type; // Trip details
    private List<Boolean> seats; // true = reserved, false = available
    private List<String> reservationOwners; // Username per seat
    private boolean isOpen; // Is trip open for reservation
    private String vehicleType; // Bus or plane

    public Trip(String id, String departure, String arrival, String date, int seatCount, String type) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.type = type;
        this.seats = new ArrayList<>();
        this.reservationOwners = new ArrayList<>();
        this.vehicleType = type;
        this.isOpen = true; // By default, trip is open

        for (int i = 0; i < seatCount; i++) {
            seats.add(false); // All seats initially empty
            reservationOwners.add(""); // No owners at start
        }
    }

    public boolean reserveSeat(int number, String username) {
        if (!isOpen) return false; // Cannot reserve if trip is closed
        if (!seats.get(number)) {
            seats.set(number, true); // Mark seat as taken
            reservationOwners.set(number, username); // Set owner
            notifyObservers(); // Update UI
            return true;
        }
        return false;
    }

    public boolean cancelSeat(int number, String username) {
        if (!isOpen) return false; // Cannot cancel if trip is closed
        if (seats.get(number) && reservationOwners.get(number).equals(username)) {
            seats.set(number, false); // Mark seat as available
            reservationOwners.set(number, null); // Clear owner
            notifyObservers(); // Update UI
            return true;
        }
        return false;
    }

    public String getReservationOwner(int number) {
        return reservationOwners.get(number); // Get username for a seat
    }

    public boolean isOpen() {
        return isOpen; // Check if trip is open
    }

    public void setOpen(boolean open) {
        this.isOpen = open; // Set trip status
    }

    public String getId() { return id; } // Trip ID
    public String getDeparture() { return departure; } // Departure location
    public String getArrival() { return arrival; } // Arrival location
    public String getDate() { return date; } // Date of trip
    public String getType() { return type; } // "bus" or "plane"
    public List<Boolean> getSeats() { return seats; } // Seat status list

    public String getVehicleType() {
        return vehicleType; // Get vehicle type
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType; // Set vehicle type
    }
}
