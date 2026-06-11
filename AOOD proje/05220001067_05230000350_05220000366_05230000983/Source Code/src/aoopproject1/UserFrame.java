package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserFrame extends JFrame implements Observer {
    private final TripManager tripManager; // Central trip manager (Observable)
    private final List<User> users; // List of all users in the system
    private final String[] selectedType = new String[1]; // Transport type filter (bus or plane)
    private final String loggedInUsername; // Currently logged-in user

    private final JPanel tripListPanel = new JPanel(); // Container for trip cards
    private final JScrollPane scrollPane = new JScrollPane(tripListPanel); // Scrollable wrapper

    public UserFrame(TripManager tripManager, List<User> users, String initialType, String username) {
        this.tripManager = tripManager;
        this.users = users;
        this.selectedType[0] = initialType;
        this.loggedInUsername = username;

        // Register this frame as an observer to TripManager
        this.tripManager.addObserver(this);

        // Frame settings
        setTitle("User Panel - " + initialType.toUpperCase() + " Trips");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // UI colors
        Color background = new Color(239, 250, 231);
        Color green = new Color(107, 163, 104);
        Color greenHover = new Color(87, 143, 84);
        Color textColor = new Color(40, 40, 40);

        // Header title
        JLabel header = new JLabel("🚌🛩️ Available Trips", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(textColor);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        header.setBackground(background);
        header.setOpaque(true);
        add(header, BorderLayout.NORTH);

        // Main panel for trips
        tripListPanel.setLayout(new BoxLayout(tripListPanel, BoxLayout.Y_AXIS));
        tripListPanel.setBackground(background);
        tripListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Scroll wrapper
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(background);
        scrollPane.getViewport().setBackground(background);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(960, 450));
        add(scrollPane, BorderLayout.CENTER);

        // Main action buttons
        JButton reserveBtn = new JButton("Reserve / Cancel");
        JButton searchBtn = new JButton("Search Trip by ID");
        JButton changeTypeBtn = new JButton("Change Transport Type");
        JButton logoutBtn = new JButton("Logout");

        // Set visual and interaction styles for buttons
        JButton[] buttons = { reserveBtn, searchBtn, changeTypeBtn, logoutBtn };
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(green);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btn.setBackground(greenHover); }
                public void mouseExited(MouseEvent e) { btn.setBackground(green); }
            });
        }

        // Action: reservation guidance
        reserveBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Please click on a trip card."));

        // Action: trip search by ID
        searchBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Trip ID:");
            if (input != null && !input.trim().isEmpty()) {
                Trip t = tripManager.findTrip(input.trim());
                if (t != null && t.getType().equalsIgnoreCase(selectedType[0])) {
                    // Show trip details and allow seat reservation
                    JOptionPane.showMessageDialog(this,
                            "Trip Detail:\n" +
                                    "ID: " + t.getId() + "\n" +
                                    "Departure: " + t.getDeparture() + "\n" +
                                    "Arrival: " + t.getArrival() + "\n" +
                                    "Date: " + t.getDate(),
                            "Trip Info", JOptionPane.INFORMATION_MESSAGE);
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Would you like to reserve a seat for this trip?",
                            "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        new SeatSelectionDialog(this, t, loggedInUsername);
                        refreshTripList();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Trip not found or type mismatch.");
                }
            }
        });

        // Action: transport type switching
        changeTypeBtn.addActionListener(e -> {
            String newType = showTypeSelectionDialog();
            if (newType != null && !newType.equalsIgnoreCase(selectedType[0])) {
                selectedType[0] = newType;
                setTitle("User Panel - " + newType.toUpperCase() + " Trips");
                header.setText("🚌🛩️ Available " + newType.toUpperCase() + " Trips");
                refreshTripList();
            }
        });

        // Action: logout
        logoutBtn.addActionListener(e -> {
            dispose(); // Close current frame
            new LoginFrame(users, tripManager); // Return to login screen
        });

        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(background);
        buttonPanel.add(reserveBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(changeTypeBtn);
        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTripList(); // Initial UI load
        setVisible(true);

        // Remove observer on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                tripManager.removeObserver(UserFrame.this);
            }
        });
    }

    @Override
    public void update() {
        refreshTripList();
    }

    private String showTypeSelectionDialog() {
        JDialog dialog = new JDialog(this, "Select Transport Type", true);
        dialog.setSize(320, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Style and colors
        Color background = new Color(239, 250, 231);
        Color pastelYellow = new Color(255, 236, 179);
        Color pastelBlue = new Color(179, 229, 252);
        dialog.getContentPane().setBackground(background);

        // Title
        JLabel label = new JLabel("Please select transport type:", JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(40, 40, 40));
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        dialog.add(label, BorderLayout.NORTH);

        // Buttons for type selection
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        buttonPanel.setBackground(background);

        JButton busBtn = new JButton("🚌 Bus");
        JButton planeBtn = new JButton("✈️ Plane");
        busBtn.setBackground(pastelYellow);
        planeBtn.setBackground(pastelBlue);
        busBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        planeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        final String[] selected = {null};

        busBtn.addActionListener(e -> {
            selected[0] = "bus";
            dialog.dispose();
        });

        planeBtn.addActionListener(e -> {
            selected[0] = "plane";
            dialog.dispose();
        });

        buttonPanel.add(busBtn);
        buttonPanel.add(planeBtn);
        dialog.add(buttonPanel, BorderLayout.CENTER);
        dialog.setVisible(true);

        return selected[0];
    }

    private void refreshTripList() {
        tripListPanel.removeAll(); // Clear existing cards
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Filter and sort trips by date
        List<Trip> sortedTrips = tripManager.getAllTrips().stream()
                .filter(t -> t.getType().equalsIgnoreCase(selectedType[0]))
                .sorted(Comparator.comparing(t -> {
                    try {
                        return sdf.parse(t.getDate());
                    } catch (Exception e) {
                        return new java.util.Date();
                    }
                }))
                .collect(Collectors.toList());

        // Display each trip as a card
        for (Trip t : sortedTrips) {
            String emoji = t.getType().equalsIgnoreCase("plane") ? "💺" : "🚌";
            int total = t.getSeats().size();
            long occupied = t.getSeats().stream().filter(k -> k).count();
            long available = total - occupied;

            JPanel card = new JPanel(new GridLayout(2, 1));
            card.setPreferredSize(new Dimension(920, 80));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            JLabel title = new JLabel(emoji + " " + t.getDeparture() + " → " + t.getArrival() + " | " + t.getDate());
            JLabel info = new JLabel("Available: " + available + " | Occupied: " + occupied + " | ID: " + t.getId());
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            info.setForeground(new Color(70, 70, 70));

            card.add(title);
            card.add(info);
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Click to open seat selection
            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    new SeatSelectionDialog(UserFrame.this, t, loggedInUsername);
                    refreshTripList(); // Refresh UI after reservation
                }
            });

            tripListPanel.add(card);
            tripListPanel.add(Box.createRigidArea(new Dimension(0, 12))); // Space
        }

        // Section for showing user’s reserved seats
        JLabel rLabel = new JLabel("📌 Your Reserved Seats:");
        rLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        tripListPanel.add(rLabel);

        // Loop through all trips and find user’s reservations
        for (Trip t : tripManager.getAllTrips()) {
            List<Boolean> seats = t.getSeats();
            for (int i = 0; i < seats.size(); i++) {
                if (seats.get(i) && loggedInUsername.equals(t.getReservationOwner(i))) {
                    int finalI = i;
                    JPanel reservationRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    reservationRow.setBackground(new Color(250, 250, 240));

                    JLabel info = new JLabel("Trip ID: " + t.getId() + ", Date: " + t.getDate() + ", Seat No: " + (i + 1));
                    info.setFont(new Font("Segoe UI", Font.PLAIN, 13));

                    JButton cancelBtn = new JButton("Cancel");
                    cancelBtn.setBackground(new Color(204, 77, 77));
                    cancelBtn.setForeground(Color.WHITE);
                    cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                    // Cancel reservation with command
                    cancelBtn.addActionListener(e -> {
                        Command cancelCommand = new CancelReservationCommand(t, finalI, loggedInUsername);
                        cancelCommand.execute();
                        JOptionPane.showMessageDialog(this, "Reservation canceled.");
                        refreshTripList();
                    });

                    reservationRow.add(info);
                    reservationRow.add(cancelBtn);
                    tripListPanel.add(reservationRow);
                }
            }
        }

        tripListPanel.revalidate();
        tripListPanel.repaint();
    }
}
