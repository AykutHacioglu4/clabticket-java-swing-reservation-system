package aoopproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TripListDialog extends JDialog {
    private final JTable table; // Table to display trip data
    private final DefaultTableModel model; // Table model
    private final TripManager tripManager; // Reference to trip manager

    public TripListDialog(JFrame parent, TripManager tripManager) {
        super(parent, "📋 Trip List", true); // Modal dialog
        this.tripManager = tripManager;

        setSize(800, 500);
        setLocationRelativeTo(parent); // Center on parent
        setLayout(new BorderLayout());

        Color background = new Color(239, 250, 231);
        Color green = new Color(107, 163, 104);
        Color greenHover = new Color(87, 143, 84);
        getContentPane().setBackground(background);

        JLabel title = new JLabel("📋 All Trips", JLabel.CENTER); // Title label
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 40));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize table and model
        model = new DefaultTableModel(new String[]{"ID", "Departure", "Arrival", "Date", "Type", "Available", "Occupied"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.setFillsViewportHeight(true);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(green);
        table.getTableHeader().setForeground(Color.WHITE);

        // Row color alternation
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final Color even = new Color(255, 255, 255);
            final Color odd = new Color(230, 255, 230);
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean isSel, boolean hasFoc, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, isSel, hasFoc, row, col);
                c.setBackground(row % 2 == 0 ? even : odd); // Alternate row colors
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table); // Scroll for table
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteBtn = new JButton("❌ Delete Trip"); // Delete button
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBtn.setBackground(green);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setOpaque(true);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.setPreferredSize(new Dimension(150, 35));

        // Hover effect for delete button
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteBtn.setBackground(greenHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteBtn.setBackground(green);
            }
        });

        // Delete button logic
        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow(); // Get selected row
            if (selected >= 0) {
                String id = (String) model.getValueAt(selected, 0); // Get trip ID
                int confirm = JOptionPane.showConfirmDialog(this, "Delete trip '" + id + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (tripManager.deleteTrip(id)) {
                        JOptionPane.showMessageDialog(this, "✅ Trip deleted.");
                        refreshTable(); // Refresh list
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Trip not found.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a trip to delete."); // No selection warning
            }
        });

        JPanel bottomPanel = new JPanel(); // Panel for delete button
        bottomPanel.setBackground(background);
        bottomPanel.add(deleteBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable(); // Load data initially
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear table
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

        for (Trip t : sorted) {
            int total = t.getSeats().size(); // Total seats
            long occupied = t.getSeats().stream().filter(k -> k).count(); // Count occupied
            long available = total - occupied;

            model.addRow(new Object[]{
                    t.getId(), t.getDeparture(), t.getArrival(),
                    t.getDate(), t.getType(), available, occupied
            });
        }
    }
}
