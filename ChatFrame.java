package Chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatFrame extends JFrame{
    private DataManager dataManager;
    private String currentUser;
    private String selectedUser;
    private JList<String> userList;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatFrame(DataManager dataManager, String currentUser) {
        this.dataManager = dataManager;
        this.currentUser = currentUser;
        initComponents();
    }

    private void initComponents() {
        setTitle("Chat - " + currentUser);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(200, 500));
        
        JLabel userLabel = new JLabel("Users", JLabel.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        leftPanel.add(userLabel, BorderLayout.NORTH);
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<String> allUsers = dataManager.getAllUsers();
        for (String user : allUsers) {
            if (!user.equals(currentUser)) {
                listModel.addElement(user);
            }
        }
        
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    loadConversation();
                }
            }
        });
        
        JScrollPane userScrollPane = new JScrollPane(userList);
        leftPanel.add(userScrollPane, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        leftPanel.add(logoutButton, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        centerPanel.add(chatScrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        messageField = new JTextField();
        messageField.addActionListener(e -> sendMessage());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton, BorderLayout.EAST);
        
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
        
        chatArea.setText("Select a user to start chatting");
    }
    
    private void loadConversation() {
        if (selectedUser == null) return;
        
        chatArea.setText("");
        List<Message> conversation = dataManager.getConversation(currentUser, selectedUser);
        
        if (conversation.isEmpty()) {
            chatArea.setText("No messages yet. Start the conversation!");
        } else {
            for (Message msg : conversation) {
                String sender = msg.getSender().equals(currentUser) ? "You" : msg.getSender();
                chatArea.append(sender + " [" + msg.getFormattedTime() + "]: " + msg.getContent() + "\n");
            }
        }
    }
    
    private void sendMessage() {
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Please select a user first");
            return;
        }
        
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        
        dataManager.sendMessage(currentUser, selectedUser, message);
        messageField.setText("");
        loadConversation();
    }
    
    private void logout() {
        LoginFrame loginFrame = new LoginFrame(dataManager);
        loginFrame.setVisible(true);
        dispose();
    }
}
    
    

