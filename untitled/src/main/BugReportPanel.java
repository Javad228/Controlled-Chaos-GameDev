package main;

import character.PlayerCharacter;
import loot.Boot;
import loot.RapidFire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;
import java.util.*;

import java.time.LocalDate;
public class BugReportPanel extends JPanel {
    JTextArea area;
    GamePanel gp;
    public BugReportPanel(GamePanel gp) {
        this.gp = gp;

        add(Box.createVerticalStrut(25));
        addTitle();

        add(Box.createVerticalStrut(15));
        addBugText();

        add(Box.createVerticalStrut(10));
        addSubmitButton();

        add(Box.createVerticalStrut(50));
        addBackButton();


        setName("Pause Menu");
        setBackground(Color.BLACK);
        setVisible(true);

    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Bug Report");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
    }

    private void addBackButton() {
        JButton settingsButton = new JButton("Back");
        settingsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.view.showPausePanel();

            }
        });
        add(settingsButton);
    }


    private void addSubmitButton() {
        JButton settingsButton = new JButton("Send Bug Report");
        settingsButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    LocalDate myObj1 = LocalDate.now();
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);
                    FileWriter fw = new FileWriter("src/logs/"+myObj1, true);
                    BufferedWriter myWriter = new BufferedWriter(fw);
                    myWriter.write(formattedDate);
                    myWriter.write("\n");
                    myWriter.write(area.getText());
                    myWriter.write("\n");
                    myWriter.write("Player Coordinates: (" + gp.getPlayer().xCoord + ", " + gp.getPlayer().yCoord + ")\n");
                    myWriter.write("Player Direction: " + gp.getPlayer().direction + "\n");
                    myWriter.write("Current Room Number: "  +gp.getCurrentRoomNum() + "\n");
                    myWriter.write("Player Inventory: ");
                    for(int i = 0; i<gp.getPlayer().getInventory().getListOfItems().size(); i++){
                        if(i==gp.getPlayer().getInventory().getListOfItems().size()-1){
                            myWriter.write(gp.getPlayer().getInventory().getListOfItems().get(i).getName());
                        }else{
                            myWriter.write(gp.getPlayer().getInventory().getListOfItems().get(i).getName() + ", ");
                        }
                    }
                    myWriter.write("\n");
                    myWriter.write("\n");

                    myWriter.close();
                } catch (IOException ez) {
                    ez.printStackTrace();
                }
                Main.view.showPausePanel();
            }
        });
        add(settingsButton);
    }

    private void addBugText() {
        area=new JTextArea(20,60);
        area.setLineWrap(true);
        area.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(area);
    }

}
