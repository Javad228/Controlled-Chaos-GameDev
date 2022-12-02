package main;

import character.PlayerCharacter;
import etc.TimeFormat;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;
import static main.Main.view;


public class LevelCompleteDialog extends JDialog {

    private static LevelCompleteDialog dialog;

    private static final int TIME1 = 0;
    private static final int TIME2 = 1;
    private static final int TIME3 = 2;

    private final int WIDTH = 400;
    private final int HEIGHT = 500;

    boolean isAlive;

    Font bigFont;
    Font smallFont;
    Color backgroundColor;
    Color neutralFC;
    Color passedFC;
    Color failedFC;

    GamePanel gp;
    CountDownLatch latch;


    public static void initializeLevelCompleteDialog(GamePanel gp, int c) {
        dialog = new LevelCompleteDialog(gp, c);
    }

    public static LevelCompleteDialog getLevelCompleteDialog(GamePanel gp, int c) {
        //TODO: In the future, check if level num changed, then initialize new dialog
        //if (dialog == null)  initializeLevelCompleteDialog(gp, c);
        //return dialog;

        initializeLevelCompleteDialog(gp, c);
        return dialog;
    }

    public void showLevelCompleteDialog() {
        dialog.setVisible(true);
        dialog.revalidate();
        dialog.repaint();
    }

    private LevelCompleteDialog(GamePanel gp, int count) {

        super(Main.view.getWindow(), "Level Complete", ModalityType.DOCUMENT_MODAL);

        bigFont = new Font("Monospaced", Font.BOLD, 25);
        smallFont = new Font("Monospaced", Font.BOLD, 15);
        backgroundColor = Color.BLACK;
        neutralFC = Color.WHITE;
        passedFC = Color.GREEN;
        failedFC = Color.RED;

        this.gp = gp;
        this.latch = null;
        this.isAlive = false;

        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(Main.view.getWindow());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setUndecorated(true);      // Hides the top bar

        Container c = this.getContentPane();

        c.setBackground(backgroundColor);
        c.setForeground(backgroundColor);
        c.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        c.add(Box.createVerticalStrut(25));
        c.add(Box.createHorizontalStrut(25));
        c.add(initializeTitleText());
        c.add(Box.createVerticalStrut(25));
        c.add(initializePlayerTimePanel());
        c.add(Box.createVerticalStrut(10));
        c.add(initializeTimePanels(TIME3));
        c.add(initializeTimePanels(TIME2));
        c.add(initializeTimePanels(TIME1));
        c.add(Box.createVerticalStrut(20));
        c.add(initializeDifficultyDetailPanel());
        c.add(initializeRewardDetailPanel(count));
        c.add(Box.createVerticalStrut(10));
        c.add(initializeContinueButton());
        c.add(Box.createVerticalStrut(25));
        this.revalidate();
        this.repaint();
    }

    private JPanel initializeTitleText() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        p.setBackground(backgroundColor);
        p.setForeground(neutralFC);


        JTextArea area = new JTextArea("Level Completed");
        area.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        area.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
        area.setBackground(backgroundColor);
        area.setForeground(neutralFC);
        area.setFont(bigFont);
        area.setEditable(false);
        area.revalidate();

        p.add(Box.createHorizontalStrut((int)(WIDTH/4.25)));
        p.add(area);
        return p;
    }

    private JPanel initializePlayerTimePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBackground(backgroundColor);
        p.setForeground(neutralFC);

        JTextArea title = new JTextArea("Your Time: ");
        title.setFont(bigFont);
        title.setBackground(backgroundColor);
        title.setForeground(neutralFC);
        title.setEditable(false);
        title.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

        JTextArea time = new JTextArea(TimeFormat.formatTimeM(gp.getCurrentLevelTime().getTime()));
        time.setFont(smallFont);
        time.setBackground(backgroundColor);
        time.setForeground(neutralFC);
        time.setEditable(false);
        time.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

        p.add(Box.createHorizontalStrut(40));
        p.add(title);
        p.add(time);
        p.add(Box.createHorizontalStrut(40));

        return p;
    }

    private JPanel initializeTimePanels(int TIME_NO) {
        JPanel p;
        String timeTier;
        long t;
        Color c;

        p = new JPanel();

        p.setBackground(backgroundColor);
        p.setForeground(neutralFC);

        // Get text according to the time tier inputted
        // Note: all text has the same length to prevent graphical
        // issues
        switch (TIME_NO) {
            case TIME1  -> {
                t = gp.getTime1().getTime();
                timeTier = "Fastest: ";
            }
            case TIME2  -> {
                t = gp.getTime2().getTime();
                timeTier = "Fast:    ";
            }
            case TIME3  -> {
                t = gp.getTime3().getTime();
                timeTier = "Average: ";
            }
            default     -> {
                t = 0L;
                timeTier = "?";
            }
        }

        // Set text color depending on if the player passed or failed
        // the time requirements of each time tier
        if (gp.getCurrentLevelTime().getTime() <= t)    c = passedFC;
        else  c = failedFC;


        JTextArea area1 = new JTextArea(timeTier);
        area1.setFont(smallFont);
        area1.setEditable(false);
        area1.setBackground(backgroundColor);
        area1.setForeground(c);

        JTextArea area2 = new JTextArea(TimeFormat.formatTimeM(t));
        area2.setFont(smallFont);
        area2.setEditable(false);
        area2.setBackground(backgroundColor);
        area2.setForeground(c);


        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        p.add(Box.createHorizontalStrut(20));
        p.add(area1);
        p.add(Box.createHorizontalStrut(5));
        p.add(area2);
        p.add(new Star(gp.getCurrentLevelTime().getTime() < t));
        p.add(Box.createHorizontalStrut(20));

        return p;
    }

    private JPanel initializeDifficultyDetailPanel() {
        JPanel p = new JPanel();
        p.setBackground(backgroundColor);
        p.setForeground(neutralFC);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        int playerDifficulty = gp.player.getGameDifficulty();
        String difficulty = PlayerCharacter.difficultyNamTab[playerDifficulty];
        Color difficultyColor = new Color((int)(playerDifficulty/9.*255), 255 - (int)((playerDifficulty/9.*255)), 0);

        JTextArea area1 = new JTextArea("Player Difficulty: ");
        area1.setFont(smallFont);
        area1.setBackground(backgroundColor);
        area1.setForeground(neutralFC);
        area1.setEditable(false);

        JTextArea area2 = new JTextArea(difficulty);
        area2.setFont(smallFont);
        area2.setBackground(backgroundColor);
        area2.setForeground(difficultyColor);
        area2.setEditable(false);

        p.add(Box.createHorizontalStrut(20));
        p.add(area1);
        p.add(area2);
        p.add(Box.createHorizontalStrut(20));

        return p;
    }

    private JPanel initializeRewardDetailPanel(int count) {
        JPanel p = new JPanel();
        p.setBackground(backgroundColor);
        p.setForeground(neutralFC);

        JTextArea detail = new JTextArea("You received " + count + " awards!");
        detail.setFont(smallFont);
        detail.setBackground(backgroundColor);
        detail.setForeground(neutralFC);
        detail.setEditable(false);

        p.add(detail);

        return p;
    }

    public JPanel initializeContinueButton() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBackground(backgroundColor);
        p.setForeground(getForeground());


        JButton b = new JButton("Continue");
        b.setFont(new Font(bigFont.getName(), bigFont.getStyle(), 15));
        b.setBackground(backgroundColor);
        b.setForeground(neutralFC);
        b.addActionListener((l) -> {
            setVisible(false);
            dispose();
            gp.hideLevelComplete();
        });

        p.add(b);

        return p;
    }
}

class Star extends JPanel {

    public final int X;
    public final int Y;
    public final int W = 60;
    public final int H = 19*3 - 3;
    public final boolean IS_FILLED;
    public final Color GOLD = new Color(255, 215, 0);

    public Star(boolean isFilled) {
        X = 0;
        Y = 0;
        IS_FILLED = isFilled;
        this.setBackground(null);
        this.setPreferredSize(new Dimension(W, H));
        this.setSize(W, H);
    }

    public Star(int x, int y, boolean isFilled) {
        X = x;
        Y = y;
        IS_FILLED = isFilled;
        this.setBackground(null);
        //this.setSize(W, H);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // Points
        Point2D.Double pt1  = new Point2D.Double(X  +   10. *3 ,  Y   +   1.  *3  );
        Point2D.Double pt2  = new Point2D.Double(X  +   12. *3 ,  Y   +   7.  *3  );
        Point2D.Double pt3  = new Point2D.Double(X  +   20. *3 ,  Y   +   8.  *3  );
        Point2D.Double pt4  = new Point2D.Double(X  +   14. *3 ,  Y   +   13. *3  );
        Point2D.Double pt5  = new Point2D.Double(X  +   16. *3 ,  Y   +   19. *3  );
        Point2D.Double pt6  = new Point2D.Double(X  +   10. *3 ,  Y   +   15. *3  );
        Point2D.Double pt7  = new Point2D.Double(X  +   4.0 *3 ,  Y   +   19. *3  );
        Point2D.Double pt8  = new Point2D.Double(X  +   5.5 *3 ,  Y   +   13. *3  );
        Point2D.Double pt9  = new Point2D.Double(X  +   0.0 *3 ,  Y   +   8.  *3  );
        Point2D.Double pt10 = new Point2D.Double(X  +   7.5 *3 ,  Y   +   7.  *3  );

        // Lines
        Line2D.Double ln1   = new Line2D.Double(pt1, pt2);
        Line2D.Double ln2   = new Line2D.Double(pt2, pt3);
        Line2D.Double ln3   = new Line2D.Double(pt3, pt4);
        Line2D.Double ln4   = new Line2D.Double(pt4, pt5);
        Line2D.Double ln5   = new Line2D.Double(pt5, pt6);
        Line2D.Double ln6   = new Line2D.Double(pt6, pt7);
        Line2D.Double ln7   = new Line2D.Double(pt7, pt8);
        Line2D.Double ln8   = new Line2D.Double(pt8, pt9);
        Line2D.Double ln9   = new Line2D.Double(pt9, pt10);
        Line2D.Double ln10  = new Line2D.Double(pt10, pt1);

        // Color lines
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0));

        // Draw lines
        g2.draw(ln1);
        g2.draw(ln2);
        g2.draw(ln3);
        g2.draw(ln4);
        g2.draw(ln5);
        g2.draw(ln6);
        g2.draw(ln7);
        g2.draw(ln8);
        g2.draw(ln9);
        g2.draw(ln10);

        // Fill star to a gold color given constructor parameter inputted
        if (IS_FILLED) {
            g2.setColor(GOLD);

            g2.fillPolygon(new Polygon(
                    new int[]{(int) pt1.x, (int) pt2.x, (int) pt3.x, (int) pt4.x,
                            (int) pt5.x, (int) pt6.x, (int) pt7.x, (int) pt8.x,
                            (int) pt9.x, (int) pt10.x},
                    new int[]{(int) pt1.y, (int) pt2.y, (int) pt3.y, (int) pt4.y,
                            (int) pt5.y, (int) pt6.y, (int) pt7.y, (int) pt8.y,
                            (int) pt9.y, (int) pt10.y},
                    10
            ));
        }
    }
}

class TestLevelCompletePanel {
    public static void main(String[] args) {
        int roomNo = 5;
        long timeToWaitMS = 1_000L * 1;

        // Launch game
        Main.main(args);

        // Set room to last room and start game
        view.showGamePanel();
        view.getGamePanel().setCurrentRoomNum(roomNo);
        view.getGamePanel().player.setGameDifficulty(1);
        view.getGamePanel().startGameThread();

        // Create local class to test level complete dialogs
        class test {
            GamePanel gp;

            public test(GamePanel gp) {
                this.gp = gp;
            }
            public void run() throws InterruptedException {
                //for (int i = PlayerCharacter.LOWEST_DIFF; i < PlayerCharacter.HIGHEST_DIFF; i++) {
                //    gp.showCompleteLevel();
                //    gp.player.incrementDifficulty();
                //    sleep(1000);
                //}
                gp.showCompleteLevel();
            }
        }

        try {
            new test(view.getGamePanel()).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            sleep(timeToWaitMS);
            System.out.println("Sleep Success");
        } catch (InterruptedException e) {
            System.out.println("Sleep Failed");
        }

        // Get the boss enemy and perform death animation
        if (view.getGamePanel().getRooms().get(roomNo).getEnemies() != null) {
            view.getGamePanel().getRooms().get(roomNo).getEnemies().get(0).kill();
        }
    }
}

class TestStarClass {

    public static void main(String[] args) {

        Star star1 = new Star(true);
        Star star2 = new Star(false);
        Star star3 = new Star(true);
        Star star4 = new Star(false);

        JFrame frame = new JFrame();
        frame.setBackground(Color.BLACK);
        frame.setSize(400, 400);

        Container c = frame.getContentPane();
        c.setBackground(new Color(100, 100, 100));
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        c.add(Box.createVerticalStrut(10));
        c.add(star1);
        c.add(Box.createVerticalStrut(10));
        c.add(star2);
        c.add(star3);
        c.add(star4);
        JButton b = new JButton("Close");
        b.addActionListener((l) -> frame.dispose());
        c.add(b);


        frame.setTitle("My Star");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}