package aoopproject1;

import javax.swing.*;

// Strategy interface for creating seat layout for bus and plane
public interface SeatArrangementStrategy {
    public JPanel createSeatPanel(Trip trip, String username, JDialog parent); // Returns seat layout panel
}
