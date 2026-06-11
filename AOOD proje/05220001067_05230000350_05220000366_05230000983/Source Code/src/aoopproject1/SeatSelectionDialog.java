package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SeatSelectionDialog extends JDialog implements Observer {
    private int emojiX = -50; // Animation position for emoji
    private Timer animationTimer; // Timer for animation
    private Trip trip; // Current trip
    private String username; // Logged-in user

    public SeatSelectionDialog(JFrame parent, Trip trip, String username) {
        super(parent, "Seat Selection - Trip ID: " + trip.getId(), true);
        this.trip = trip;
        this.username = username;
        trip.addObserver(this); // Subscribe to trip updates
        setSize(800, 600);
        setLocationRelativeTo(parent); // Center the dialog

        Color background = new Color(239, 250, 231);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (trip.getType().equalsIgnoreCase("plane")) {
                    setBackground(new Color(200, 230, 255)); // Sky color
                } else {
                    setBackground(new Color(245, 235, 200)); // Road color
                }
                g.setFont(new Font("Segoe UI", Font.PLAIN, 28));
                String emoji = trip.getType().equalsIgnoreCase("plane") ? "🛩️" : "🚌"; // Emoji based on type

                g.setColor(new Color(0, 0, 0, 40));
                g.drawString(emoji, emojiX + 2, 37);
                g.setColor(Color.BLACK);
                g.drawString(emoji, emojiX, 35);
            }
        };
        animationPanel.setPreferredSize(new Dimension(800, 50));

        animationTimer = new Timer(20, e -> {
            emojiX += 2;
            if (emojiX > getWidth()) emojiX = -50; // Reset emoji position
            animationPanel.repaint();
        });
        animationTimer.start(); // Start animation

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(background);
        northPanel.add(animationPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Please select a seat", JLabel.CENTER); // Title
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(30, 30, 30));
        northPanel.add(title, BorderLayout.SOUTH);
        panel.add(northPanel, BorderLayout.NORTH);

        // Select strategy based on trip type (only plane or bus exist)
        SeatArrangementStrategy strategy = trip.getType().equalsIgnoreCase("plane")
                ? new PlaneSeatLayoutStrategy()
                : new BusSeatLayoutStrategy();

        JPanel gridWrapper = strategy.createSeatPanel(trip, username, this); // Create seat layout

        JScrollPane scroll = new JScrollPane(gridWrapper); // Scrollable seat view
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(background);
        scroll.setBackground(background);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scroll, BorderLayout.CENTER);
        add(panel); // Add main panel to dialog

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (animationTimer != null) animationTimer.stop(); // Stop animation on close
                trip.removeObserver(SeatSelectionDialog.this); // Unsubscribe from trip
            }
        });

        setVisible(true); // Show dialog
        initUI(); // Initialize UI
    }

    @Override
    public void update() {
        getContentPane().removeAll(); // Clear UI
        initUI(); // Rebuild UI
        revalidate();
        repaint();
    }

    public void initUI() {
        getContentPane().removeAll(); // Clear previous content
        Color background = new Color(239, 250, 231);

        JPanel panel = new JPanel(new BorderLayout()); // Main container
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (trip.getType().equalsIgnoreCase("plane")) {
                    setBackground(new Color(200, 230, 255)); // Sky color
                } else {
                    setBackground(new Color(245, 235, 200)); // Road color
                }
                g.setFont(new Font("Segoe UI", Font.PLAIN, 28));
                String emoji = trip.getType().equalsIgnoreCase("plane") ? "🛩️" : "🚌"; // Emoji
                g.setColor(new Color(0, 0, 0, 40));
                g.drawString(emoji, emojiX + 2, 37);
                g.setColor(Color.BLACK);
                g.drawString(emoji, emojiX, 35);
            }
        };
        animationPanel.setPreferredSize(new Dimension(800, 50));

        animationTimer = new Timer(20, e -> {
            emojiX += 2;
            if (emojiX > getWidth()) emojiX = -50;
            animationPanel.repaint();
        });
        animationTimer.start(); // Restart animation

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(background);
        northPanel.add(animationPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Please select a seat", JLabel.CENTER); // Title label
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(30, 30, 30));
        northPanel.add(title, BorderLayout.SOUTH);
        panel.add(northPanel, BorderLayout.NORTH);

        SeatArrangementStrategy strategy = trip.getType().equalsIgnoreCase("plane")
                ? new PlaneSeatLayoutStrategy()
                : new BusSeatLayoutStrategy();

        JPanel gridWrapper = strategy.createSeatPanel(trip, username, this); // Seat layout
        JScrollPane scroll = new JScrollPane(gridWrapper); // Scroll view
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(background);
        scroll.setBackground(background);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scroll, BorderLayout.CENTER);
        setContentPane(panel); // Apply new content
        revalidate();
        repaint();
    }
}
