package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String args[]){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(80 * 4 * 3, 60 * 4 * 3);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel, BorderLayout.CENTER);


        window.setLocationRelativeTo(null);

        window.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
