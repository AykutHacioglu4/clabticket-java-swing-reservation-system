package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BusSeatLayoutStrategy implements SeatArrangementStrategy {

    @Override
    public JPanel createSeatPanel(Trip trip, String username, JDialog parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout for rows
        panel.setBackground(new Color(239, 250, 231)); // Set background color

        List<Boolean> seats = trip.getSeats(); // Get seat occupancy list
        int seatIndex = 0; // Current seat index
        int rowNumber = 1; // Row counter

        while (seatIndex < seats.size()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Row with spacing
            row.setBackground(panel.getBackground());

            JLabel rowLabelStart = new JLabel(String.valueOf(rowNumber)); // Row number on left
            rowLabelStart.setPreferredSize(new Dimension(30, 48));
            rowLabelStart.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row.add(rowLabelStart);

            for (int i = 0; i < 2 && seatIndex < seats.size(); i++, seatIndex++) {
                row.add(SeatButtonFactory.create(trip, seatIndex, username, parent)); // Left side seats
            }

            row.add(Box.createRigidArea(new Dimension(40, 0))); // Aisle space

            for (int i = 0; i < 2 && seatIndex < seats.size(); i++, seatIndex++) {
                row.add(SeatButtonFactory.create(trip, seatIndex, username, parent)); // Right side seats
            }

            JLabel rowLabelEnd = new JLabel(String.valueOf(rowNumber)); // Row number on right
            rowLabelEnd.setPreferredSize(new Dimension(30, 48));
            rowLabelEnd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            row.add(rowLabelEnd);

            panel.add(row); // Add row to panel
            rowNumber++;
        }

        return panel; // Return the full seat panel
    }
}
