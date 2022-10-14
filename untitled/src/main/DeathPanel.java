package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;

public class DeathPanel extends JPanel {
    public GamePanel gp;
    public JTextArea deathTitle;
    public JButton newGame;
    public JButton restoreGameFromFile;
    public JButton quitGame;
    public Dimension buttonDimension;
    public GridBagConstraints gc;

    public DeathPanel(GamePanel gp) {
        this.gp = gp;
        this.setLayout(new GridBagLayout());
        this.gc = new GridBagConstraints();
        this.buttonDimension = new Dimension(100,100);

        initDeathTitle();
        initNewGame();
        initRestoreGameFromFile();
        initQuitGame();

        this.setVisible(true);
    }

    private void initDeathTitle() {
        this.deathTitle = new JTextArea("You Died");
        this.deathTitle.setMinimumSize(buttonDimension);
        this.deathTitle.setEditable(false);
        this.deathTitle.setBackground(this.getBackground());
        this.gc.fill = GridBagConstraints.HORIZONTAL;
        this.gc.weightx = 0.5;
        this.gc.gridx = 1;
        this.gc.gridy = 0;
        this.add(new JPanel().add(deathTitle), gc);
    }

    private void initNewGame() {
        this.newGame = new JButton("New Game");
        this.newGame.setPreferredSize(buttonDimension);
        this.newGame.addActionListener((a) -> {
            hideDeathPanel();
            gp.newGame();
            gp.resumeThread();
        });
        this.gc.anchor = GridBagConstraints.PAGE_END;
        this.gc.insets = new Insets(100,10,100,10);
        this.gc.gridx = 0;
        this.gc.gridy = 1;
        this.add(newGame, gc);
    }

    private void initRestoreGameFromFile() {
        this.restoreGameFromFile = new JButton("Restore from Save");
        this.restoreGameFromFile.setPreferredSize(buttonDimension);
        this.restoreGameFromFile.addActionListener((a) -> {
            hideDeathPanel();
            gp.saveData.restoreSave();
            gp.resumeThread();
        });
        this.gc.gridx = 1;
        this.gc.gridy = 1;
        this.add(restoreGameFromFile, gc);
    }

    private void initQuitGame() {
        this.quitGame = new JButton("Quit Game");
        this.quitGame.setPreferredSize(buttonDimension);
        this.quitGame.addActionListener((a) -> {
            System.exit(0);
        });
        this.gc.gridx = 2;
        this.gc.gridy = 1;
        this.add(quitGame, gc);
    }

    public void showDeathPanel() {
        Main.view.getWindow().remove(Main.view.getGamePanel());
        Main.view.getWindow().add(this);
        Main.view.getWindow().setVisible(false);
        Main.view.getWindow().setVisible(true);
        Audio.stopMusic();
        Audio.stopWalking();
    }

    public void hideDeathPanel() {
        if (!isVisible())   return;

        Main.view.getWindow().setVisible(false);
        Main.view.getWindow().remove(this);
        Main.view.getWindow().add(gp);
        Main.view.getWindow().toFront();
        //Main.view.getWindow().repaint();
        //Main.view.getWindow().validate();
        Main.view.getWindow().setAutoRequestFocus(true);
        Main.view.getWindow().setVisible(true);
        Main.view.getWindow().requestFocusInWindow();
    }

}
