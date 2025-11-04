package Chapter13;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManager dataManager = new DataManager();
            LoginFrame loginFrame = new LoginFrame(dataManager);
            loginFrame.setVisible(true);
        });
    }
    
}
