package aoopproject1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegisterDialog extends JDialog {
    public RegisterDialog(JFrame parent, List<User> users) {
        super(parent, "📝 Sign Up", true); // Create modal dialog
        setSize(400, 300);
        setLocationRelativeTo(parent); // Center on parent
        setLayout(new BorderLayout(10, 10));

        Color background = new Color(239, 250, 231);
        Color textColor = new Color(40, 40, 40);

        JPanel form = new JPanel(new GridBagLayout()); // Form panel
        form.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:"); // Username label
        usernameLabel.setForeground(textColor);
        JTextField usernameField = new JTextField(); // Username input

        JLabel passwordLabel = new JLabel("Password:"); // Password label
        passwordLabel.setForeground(textColor);
        JPasswordField passwordField = new JPasswordField(); // Password input

        JLabel roleLabel = new JLabel("Role:"); // Role label
        roleLabel.setForeground(textColor);
        String[] roles = {"user", "admin"}; // Available roles
        JComboBox<String> roleBox = new JComboBox<>(roles); // Role selector

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(usernameLabel, gbc);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(passwordLabel, gbc);
        gbc.gridx = 1;
        form.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(roleLabel, gbc);
        gbc.gridx = 1;
        form.add(roleBox, gbc);

        add(form, BorderLayout.CENTER);

        JButton signUpBtn = new JButton("Sign Up"); // Register button
        signUpBtn.setBackground(new Color(107, 163, 104));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpBtn.setFocusPainted(false);
        signUpBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        signUpBtn.addActionListener(e -> {
            String name = usernameField.getText().trim(); // Get username
            String password = new String(passwordField.getPassword()).trim(); // Get password
            String role = roleBox.getSelectedItem().toString(); // Get selected role

            if (name.isEmpty() || password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Please enter a valid username and at least a 6-character password.");
                return;
            }

            if (role.equals("admin")) {
                String code = JOptionPane.showInputDialog(this, "🔐 Enter admin registration code:"); // Prompt for admin code
                if (code == null || !code.equals("secret123")) {
                    JOptionPane.showMessageDialog(this, "Invalid admin code!"); // Reject invalid code
                    return;
                }
            }

            users.add(UserFactory.create(role, name, password)); // Add user to list
            JOptionPane.showMessageDialog(this, "✅ Registration successful!");
            dispose(); // Close dialog
        });

        JPanel bottom = new JPanel(); // Bottom panel with button
        bottom.setBackground(background);
        bottom.add(signUpBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}
