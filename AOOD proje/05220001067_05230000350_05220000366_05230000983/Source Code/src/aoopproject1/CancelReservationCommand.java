package aoopproject1;

public class CancelReservationCommand implements Command {
    private Trip trip; // The trip where the reservation exists
    private int seatNumber; // Seat number to cancel
    private String username; // Username of the person who made the reservation

    public CancelReservationCommand(Trip trip, int seatNumber, String username) {
        this.trip = trip; // Assign trip
        this.seatNumber = seatNumber; // Assign seat number
        this.username = username; // Assign username
    }

    @Override
    public void execute() {
        boolean success = trip.cancelSeat(seatNumber, username); // Attempt to cancel the seat
        if (success) {
            System.out.println("Seat cancellation successful: " + seatNumber); // Success message
        } else {
            System.out.println("Cancellation failed (either owner mismatch or already empty): " + seatNumber); // Failure message
        }
    }
}
