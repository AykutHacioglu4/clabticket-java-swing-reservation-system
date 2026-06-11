package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class LoginFrame extends JFrame {
    private final List<User> users; // List of registered users
    private final TripManager tripManager; // Manages available trips

    private JTextField usernameField; // Input for username
    private JPasswordField passwordField; // Input for password
    private JCheckBox showPasswordCheckbox; // Toggle to show/hide password

    public LoginFrame(List<User> users, TripManager tripManager) {
        this.users = users;
        this.tripManager = tripManager;

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // Set look and feel
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("CLABTicket🍏");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout(10, 10));

        Color background = new Color(239, 250, 231); // UI colors
        Color green = new Color(107, 163, 104);
        Color greenHover = new Color(87, 143, 84);
        Color textColor = new Color(40, 40, 40);

        ImageIcon icon = new ImageIcon(getClass().getResource("/aoopproject1/logo.png")); // Load logo
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage), JLabel.CENTER); // Logo label
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Login", JLabel.CENTER); // Title label
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textColor);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel(); // Top panel with logo and title
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(background);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        topPanel.add(iconLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(title);

        add(topPanel, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout()); // Form panel
        form.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        form.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("username:"); // Username label
        emailLabel.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(emailLabel, gbc);

        usernameField = new JTextField(); // Username input
        usernameField.setForeground(Color.BLACK);
        usernameField.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 0;
        form.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("password:"); // Password label
        passwordLabel.setForeground(textColor);
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(passwordLabel, gbc);

        passwordField = new JPasswordField(); // Password input
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1;
        form.add(passwordField, gbc);

        showPasswordCheckbox = new JCheckBox("Show password"); // Checkbox to toggle password visibility
        showPasswordCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckbox.setBackground(background);
        showPasswordCheckbox.setForeground(textColor);
        showPasswordCheckbox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '*');
        });
        gbc.gridx = 1; gbc.gridy = 2;
        form.add(showPasswordCheckbox, gbc);

        add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Login and register buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        buttonPanel.setBackground(background);

        JButton loginBtn = new JButton("Login"); // Login button
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(green);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> login()); // Trigger login
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginBtn.setBackground(greenHover); }
            public void mouseExited(MouseEvent e) { loginBtn.setBackground(green); }
        });
        buttonPanel.add(loginBtn);

        JButton registerBtn = new JButton("Don't have an account? Sign up"); // Register button
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerBtn.setForeground(textColor);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(e -> new RegisterDialog(this, users).setVisible(true)); // Open register dialog

        buttonPanel.add(registerBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(background);
        setVisible(true); // Show frame
    }

    private void login() {
        String username = usernameField.getText().trim(); // Get entered username
        String password = new String(passwordField.getPassword()).trim(); // Get entered password

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User u : users) {
            if (u.login(username, password)) { // Check credentials
                JOptionPane.showMessageDialog(this, "✅ Login successful: " + username);
                dispose(); // Close login window

                if (u instanceof Admin) {
                    new AdminFrame(tripManager, users); // Open admin panel
                } else {
                    String selectedType = showTypeSelectionDialog(); // Ask for transportation type
                    if (selectedType != null) {
                        new UserFrame(tripManager, users, selectedType, username); // Open user panel
                    }
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "❌ Login failed. Please check your credentials.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String showTypeSelectionDialog() {
        JDialog dialog = new JDialog(this, "Select Transportation Type", true); // Modal dialog
        dialog.setSize(320, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        Color background = new Color(239, 250, 231);
        Color pastelYellow = new Color(255, 236, 179);
        Color pastelBlue = new Color(179, 229, 252);

        dialog.getContentPane().setBackground(background);

        JLabel label = new JLabel("Please select the transportation type:", JLabel.CENTER); // Instruction
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(40, 40, 40));
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        label.setBackground(background);
        label.setOpaque(true);
        dialog.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 10)); // Bus/Plane buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        buttonPanel.setBackground(background);

        JButton busBtn = new JButton("🚌 Bus"); // Bus option
        busBtn.setBackground(pastelYellow);
        busBtn.setForeground(Color.BLACK);
        busBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        busBtn.setFocusPainted(false);

        JButton planeBtn = new JButton("🛩️ Plane"); // Plane option
        planeBtn.setBackground(pastelBlue);
        planeBtn.setForeground(Color.BLACK);
        planeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        planeBtn.setFocusPainted(false);

        final String[] selected = {null}; // Selected type container

        busBtn.addActionListener(e -> {
            selected[0] = "bus";
            dialog.dispose(); // Close dialog
        });

        planeBtn.addActionListener(e -> {
            selected[0] = "plane";
            dialog.dispose(); // Close dialog
        });

        buttonPanel.add(busBtn);
        buttonPanel.add(planeBtn);
        dialog.add(buttonPanel, BorderLayout.CENTER);

        dialog.setVisible(true); // Show dialog
        return selected[0]; // Return selected type
    }
}
