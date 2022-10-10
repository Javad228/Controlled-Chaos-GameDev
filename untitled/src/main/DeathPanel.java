package main;

import javax.swing.*;
import java.awt.*;

public class DeathPanel extends JPanel {
    public GamePanel gp;
    public JButton newGame;
    public JButton restoreGameFromFile;
    public JButton quitGame;

    public DeathPanel(GamePanel gp) {
        this.gp = gp;
        initNewGame();
        initRestoreGameFromFile();
        initQuitGame();
        this.setLayout(new BorderLayout());
        this.add(new JTextArea("You Died"), BorderLayout.NORTH);
        this.add(newGame, BorderLayout.WEST);
        this.add(restoreGameFromFile, BorderLayout.CENTER);
        this.add(quitGame, BorderLayout.EAST);
        this.setVisible(true);
    }

    private void initNewGame() {
        this.newGame = new JButton("New Game");
        this.newGame.addActionListener((a) -> {
            Main.window.dispose();
            Main.main(Main.args);
        });
    }

    private void initRestoreGameFromFile() {
        this.restoreGameFromFile = new JButton("Restore from Save");
    }

    private void initQuitGame() {
        this.quitGame = new JButton("Quit Game");
        this.quitGame.addActionListener((a) -> {
            System.exit(0);
        });
    }

}
