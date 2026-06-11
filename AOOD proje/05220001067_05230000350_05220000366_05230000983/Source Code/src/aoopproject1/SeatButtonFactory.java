package aoopproject1;

import javax.swing.*;
import java.awt.*;

public class SeatButtonFactory {

    public static JButton create(Trip trip, int index, String username, JDialog dialog) {
        ImageIcon emptyIcon = new ImageIcon( // Icon for empty seat
                new ImageIcon(SeatButtonFactory.class.getResource("/aoopproject1/bos_koltuk.png"))
                        .getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)
        );
        ImageIcon fullIcon = new ImageIcon( // Icon for occupied seat
                new ImageIcon(SeatButtonFactory.class.getResource("/aoopproject1/dolu_koltuk.png"))
                        .getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)
        );

        JButton btn = new JButton(String.valueOf(index + 1)); // Seat number label
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setPreferredSize(new Dimension(52, 60));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        if (trip.getSeats().get(index)) {
            btn.setIcon(fullIcon); // Set full seat icon
            btn.setEnabled(false); // Disable button if occupied
        } else {
            btn.setIcon(emptyIcon); // Set empty seat icon
            btn.setToolTipText("Empty seat " + (index + 1));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(dialog,
                        "Do you want to reserve seat number " + (index + 1) + "?",
                        "Reservation Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Command command = new SeatReserveCommand(trip, index, username); // Create reserve command
                    command.execute(); // Execute reservation

                    if (trip.getSeats().get(index)) {
                        JOptionPane.showMessageDialog(dialog,
                                "✅ Seat successfully reserved!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose(); // Close seat selection dialog
                    } else {
                        JOptionPane.showMessageDialog(dialog,
                                "❌ Seat is already taken.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        return btn; // Return configured seat button
    }
}
