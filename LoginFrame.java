package Chapter13;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;

import java.awt.*;

public class LoginFrame extends JFrame {
    private DataManager dataManager;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(DataManager dataManager) {
        this.dataManager = dataManager;
        initComponents();
    }
    private void initComponents() {
        setTitle("Chat Application - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Chat Application", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel);

        JPanel usernamePanel = new JPanel(new BorderLayout(10, 0));
        usernamePanel.add(new JLabel("Username:"), BorderLayout.WEST);
        usernameField = new JTextField();
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        mainPanel.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new BorderLayout(10, 0));
        passwordPanel.add(new JLabel("Password:"), BorderLayout.WEST);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        mainPanel.add(passwordPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton loginButton= new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> handleSignup());

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }

        if (dataManager.loginUser(username, password)) {
            openChatWindow(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
           
        }
    }

    private void handleSignup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields");
            return;
        }

        if (dataManager.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Sign up successfully! Please login.");
            usernameField.setText("");
            passwordField.setText("");

        } else {
            JOptionPane.showMessageDialog(this,"Username already exists");

        }
    }

    private void openChatWindow(String username) {
        ChatFrame chatFrame = new ChatFrame(dataManager, username);
        chatFrame.setVisible(true);
        dispose();
    }


    
}
