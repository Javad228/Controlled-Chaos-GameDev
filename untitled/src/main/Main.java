package main;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static View view;
	public static JFrame window;

	public static void main(String[] args) {

		view = new View();
		window = view.getWindow();
    
		GamePanel gamePanel = view.getGamePanel();

		gamePanel.startGameThread();
	}
}
