package etc;

import character.Enemy;
import main.*;
import save.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static main.Main.*;

/**
 * test - A class which tests the implementation of the implemented
 * game time using an expected time.
 */
public class TestGameClock {

    static CountDownLatch latch;
    static long expectedRunTime;
    static long expectedRunTimeNS;

    public static void main(String[] args) {
        latch = new CountDownLatch(1);                              // Variable assignments
        expectedRunTime = 5 * 1000;
        expectedRunTimeNS = expectedRunTime * 1000 * 1000;

        TestHelper helper = new TestHelper(
                latch,
                expectedRunTime,
                args);// Start thread to countdown time

        try {
            latch.await();                                          // Wait for thread in other class to wait for expected timed

            System.out.println("-=-=-\n\n\nThread Paused!\n\n\n-=-=-");

            GamePanel gp = helper.gp;                       // Get gamepanel

            view.getWindow().dispose();                     // Close game window

            Time timeElapsed = gp.getCurrentRunTime();      // Access time elapsed

            GameSaveState saveState = new GameSaveState(    // Initialize new savestate to access time elapsed in seconds
                    new SimpleCharacter(gp.player),
                    timeElapsed,
                    timeElapsed,
                    gp.getRooms(),
                    gp.getCurrentRoomNum());

            System.out.printf("Expected\t%s\nActual\t\t%s\n", new GameSaveState(null, new Time(expectedRunTimeNS), new Time(0), new ArrayList<>(), 0).currentRunTimeS, saveState.currentRunTimeS);  // Print time elapsed
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        finally {
            if (Main.view.getGamePanel() != null)   Main.view.getGamePanel().terminateGameThread();
        }
    }


}

class TestHelper implements Runnable {
    CountDownLatch latch;
    long expectedRunTime;
    Thread t;
    GamePanel gp;
    String[] args;

    public TestHelper(CountDownLatch latch, long expectedRunTime, String[] args) {
        this.latch = latch;
        this.expectedRunTime = expectedRunTime;
        this.args = args;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ArrayList<Enemy> e = new ArrayList<>();

            Main.main(args);                    // Launch new instance of game

            view.getGamePanel().getRooms().get(view.getGamePanel().getCurrentRoomNum()).setEnemies(e);
                                                // Set enemies in the room to have no enemies
            view.showGamePanel();
            view.getGamePanel().startGameThread();
            Thread.sleep(expectedRunTime);      // Sleep for specified period of time

            view.getGamePanel().pauseThread();  // Pause game (stop game clock)

            this.gp = view.getGamePanel();      // Set GamePanel as variable for access

            latch.countDown();                  // Notify main thread that this thread has finished execution
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
