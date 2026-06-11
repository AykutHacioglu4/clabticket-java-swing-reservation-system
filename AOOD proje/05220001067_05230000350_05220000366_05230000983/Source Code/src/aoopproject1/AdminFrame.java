package aoopproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFrame extends JFrame {
    private final TripManager tripManager; // Manages trips
    private final List<User> users; // List of all users
    private final DefaultTableModel tableModel; // Table model for trip data
    private final JTable table; // Trip table
    private final JScrollPane tableScroll; // Scroll pane for table

    public AdminFrame(TripManager tripManager, List<User> users) {
        this.tripManager = tripManager;
        this.users = users;

        setTitle("Admin Panel - Add Trip");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Colors
        Color background = new Color(239, 250, 231);
        Color green = new Color(107, 163, 104);
        Color greenHover = new Color(87, 143, 84);
        Color textColor = new Color(40, 40, 40);
        getContentPane().setBackground(background);

        // Header label
        JLabel header = new JLabel("➕ Add Trip Panel", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(textColor);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Center panel with input fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Input fields
        JTextField idField = new JTextField(15);
        JTextField departureField = new JTextField(15);
        JTextField arrivalField = new JTextField(15);
        JTextField dateField = new JTextField(15);
        JTextField seatCountField = new JTextField(15);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"bus", "plane"});

        // Labels and fields
        String[] labels = { "Trip ID:", "Departure:", "Arrival:", "Date (yyyy-MM-dd):", "Seat Count:", "Type:" };
        Component[] fields = { idField, departureField, arrivalField, dateField, seatCountField, typeCombo };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i;
            centerPanel.add(new JLabel(labels[i]), gbc); // Label
            gbc.gridy = i;
            gbc.gridx = 1;
            centerPanel.add(fields[i], gbc); // Field
            gbc.gridx = 0;
        }

        add(centerPanel, BorderLayout.CENTER);

        // Trip table
        String[] columns = { "ID", "Departure", "Arrival", "Date", "Type", "Available", "Occupied" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(780, 200));
        tableScroll.setVisible(false); // Hidden initially
        add(tableScroll, BorderLayout.EAST);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);
        JButton addBtn = new JButton("✅ Add Trip");
        JButton logoutBtn = new JButton("🔚 Logout");
        JButton listBtn = new JButton("📋 View Trips");

        JButton[] buttons = { addBtn, logoutBtn, listBtn };
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setBackground(green);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btn.setBackground(greenHover); }
                public void mouseExited(MouseEvent e) { btn.setBackground(green); }
            });
            bottomPanel.add(btn);
        }

        // Add Trip button action
        addBtn.addActionListener(e -> {
            try {
                Trip newTrip = new Trip(
                        idField.getText().trim(),
                        departureField.getText().trim(),
                        arrivalField.getText().trim(),
                        dateField.getText().trim(),
                        Integer.parseInt(seatCountField.getText().trim()),
                        (String) typeCombo.getSelectedItem()
                );
                tripManager.addTrip(newTrip); // Add trip to manager
                JOptionPane.showMessageDialog(this, "✅ Trip added.");
                // Clear fields
                idField.setText(""); departureField.setText(""); arrivalField.setText("");
                dateField.setText(""); seatCountField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        // Logout button action
        logoutBtn.addActionListener(e -> {
            dispose(); // Close current window
            new LoginFrame(users, tripManager); // Open login screen
        });

        // View trips button action
        listBtn.addActionListener(e -> {
            TripListDialog dialog = new TripListDialog(this, tripManager); // Show trip list dialog
            dialog.setVisible(true);
        });

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true); // Show frame
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Trip> sorted = tripManager.getAllTrips().stream()
                .sorted(Comparator.comparing(t -> {
                    try {
                        return sdf.parse(t.getDate()); // Sort by date
                    } catch (Exception e) {
                        return new java.util.Date();
                    }
                }))
                .collect(Collectors.toList());

        // Add rows to table
        for (Trip t : sorted) {
            int total = t.getSeats().size();
            long occupied = t.getSeats().stream().filter(k -> k).count(); // true = occupied
            long available = total - occupied;

            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getDeparture(),
                    t.getArrival(),
                    t.getDate(),
                    t.getType(),
                    available,
                    occupied
            });
        }
    }
}
