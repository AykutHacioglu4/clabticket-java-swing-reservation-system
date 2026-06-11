package aoopproject1;

public class SeatReserveCommand implements Command {
    private Trip trip; // Trip where the seat is being reserved
    private int seatNumber; // Seat number to reserve
    private String username; // User making the reservation

    public SeatReserveCommand(Trip trip, int seatNumber, String username) {
        this.trip = trip; // Assign trip
        this.seatNumber = seatNumber; // Assign seat number
        this.username = username; // Assign username
    }

    @Override
    public void execute() {
        boolean success = trip.reserveSeat(seatNumber, username); // Try to reserve the seat
        if (success) {
            System.out.println("Seat successfully reserved: " + seatNumber); // Reservation success
        } else {
            System.out.println("Seat could not be reserved: " + seatNumber); // Reservation failed
        }
    }
}
