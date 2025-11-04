package Chapter13;

import java.io.*;
import java.util.*;

public class DataManager {
    private static final String USERS_FILE = "user.dat";
    private static final String MESSAGE_FILE = "messages.dat";
    private Map<String, User> users;
    private List<Message> messages;

    public DataManager() {
        users = new HashMap<>();
        messages = new ArrayList<>();
        loadUsers();
        loadMessages();
    }
    
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        saveUsers();
        return true;
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void sendMessage(String sender, String receiver, String content) {
        Message msg = new Message(sender, receiver, content);
        messages.add(msg);
        saveMessages();
    }

    public List<Message> getConversation(String user1, String user2) {
        List<Message> conversation = new ArrayList<>();
        for (Message msg : messages) {
            if ((msg.getSender().equals(user1) && msg.getReceiver().equals(user2)) ||
                (msg.getSender().equals(user2) && msg.getReceiver().equals(user1))) {
                conversation.add(msg);
                }
        }
        return conversation;
    }

    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }
    
    private void saveUsers()  {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        File file = new File(USERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private void saveMessages() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MESSAGE_FILE))) {
            oos.writeObject(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        private void loadMessages() {
            File file = new File(MESSAGE_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                   messages = (List<Message>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();

                }
            }
        }
    


    
}
