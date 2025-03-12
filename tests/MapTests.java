import core.GUI.GamePanel;

import javax.swing.*;

public class MapTests {
    public static void main(String args[]){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("My 2Dgame");

        GamePanel gamePanel = new GamePanel(50, 50, 1000);
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
