package main;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static JFrame window;

	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Controlled Chaos");

		GamePanel gamePanel = new GamePanel();
		window.setLayout(new BorderLayout());
		window.add(gamePanel, BorderLayout.CENTER);
		window.add(gamePanel.saveData.saveGameButton, BorderLayout.NORTH);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.startGameThread();
	}
}
