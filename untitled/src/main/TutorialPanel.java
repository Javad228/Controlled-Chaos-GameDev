package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TutorialPanel extends JPanel {
    GamePanel gp;
    JScrollPane scrollPane = new JScrollPane(this);
    JPanel basicDetailsPanel = new JPanel();

    public TutorialPanel(GamePanel gp)  {
        this.gp = gp;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scrollPane.setLayout(new ScrollPaneLayout());
        basicDetailsPanel.setLayout(new GridLayout(4, 1)); // number of rows depends on number of fields you'd like to add
        basicDetailsPanel.setBackground(Color.BLACK);
        addTitleLabel();
        addContentLabels();
        addBasicDetailsLabel();
        add(basicDetailsPanel);

        addSaveButton();
        scrollPane.setBackground(Color.BLACK);

        setName("Tutorial");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addBasicDetailsLabel() {
        JLabel titleLabel = new JLabel("Basic Movement:");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addContentLabels() {
        JLabel nameCategoryLabel = new JLabel("  Movement:  Up: W  Down: S  Left: A  Right: D");
        nameCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        nameCategoryLabel.setForeground(Color.WHITE);

        JLabel attackLabel = new JLabel("  Attack: Arrow Key   Talk to NPC: E   Check Inventory: C");
        attackLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        attackLabel.setForeground(Color.WHITE);

        JLabel settingLabel = new JLabel("  Check MiniMap: M        Setting: ESC   ");
        settingLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingLabel.setForeground(Color.WHITE);

        JLabel nextroomLabel = new JLabel("       Try these move in the tutorial room");
        nextroomLabel.setFont(new Font("Monospaced", Font.PLAIN, 25));
        nextroomLabel.setForeground(Color.WHITE);

        basicDetailsPanel.add(nameCategoryLabel);
        basicDetailsPanel.add(attackLabel);
        basicDetailsPanel.add(settingLabel);
        basicDetailsPanel.add(nextroomLabel);
    }

    private void addTitleLabel() {
        JLabel titleLabel = new JLabel("Tutorial");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    public void addSaveButton() {
        JButton saveButton = new JButton("Exit");
        saveButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showMainMenuPanel();
            }
        });
        add(saveButton);
    }
}

