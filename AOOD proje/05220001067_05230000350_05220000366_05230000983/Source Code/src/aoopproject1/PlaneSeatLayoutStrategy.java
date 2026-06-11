package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaneSeatLayoutStrategy implements SeatArrangementStrategy {

    @Override
    public JPanel createSeatPanel(Trip trip, String username, JDialog parent) {
        JPanel panel = new JPanel(); // Main panel for seat layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Arrange rows vertically
        panel.setBackground(new Color(239, 250, 231)); // Set background color

        List<Boolean> seats = trip.getSeats(); // Get seat availability list
        int seatIndex = 0; // Index for seat position
        int rowNumber = 1; // Row counter

        while (seatIndex < seats.size()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5)); // Row panel with spacing
            row.setBackground(panel.getBackground());

            JLabel rowLabelStart = new JLabel(String.valueOf(rowNumber)); // Row number label (start)
            rowLabelStart.setPreferredSize(new Dimension(30, 48));
            rowLabelStart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row.add(rowLabelStart);

            for (int i = 0; i < 4 && seatIndex < seats.size(); i++, seatIndex++) {
                row.add(SeatButtonFactory.create(trip, seatIndex, username, parent)); // Left side seats
            }

            row.add(Box.createRigidArea(new Dimension(40, 0))); // Aisle space

            for (int i = 0; i < 4 && seatIndex < seats.size(); i++, seatIndex++) {
                row.add(SeatButtonFactory.create(trip, seatIndex, username, parent)); // Right side seats
            }

            JLabel rowLabelEnd = new JLabel(String.valueOf(rowNumber)); // Row number label (end)
            rowLabelEnd.setPreferredSize(new Dimension(30, 48));
            rowLabelEnd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row.add(rowLabelEnd);

            panel.add(row); // Add row to the panel
            rowNumber++;
        }

        return panel; // Return complete seat layout
    }
}
