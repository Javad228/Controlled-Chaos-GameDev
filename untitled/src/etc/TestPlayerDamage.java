package etc;

import main.Main;
import main.Room;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class TestPlayerDamage {
    public static void main(String[] args) {
        Main.main(args);
        ArrayList<Room> rooms = Main.view.getGamePanel().getRooms();

        for (Room r: rooms) {
            r.setRoomType(Room.GRASSROOM);
        }

        Main.view.getGamePanel().startGameThread();
        Main.view.showGamePanel();

        new Thread(() -> {
            //sleep(1000*10);
            boolean d = false;
            while (!d) {
                d = Main.view.getGamePanel().getPlayer().isDamaged();
                System.out.println(d);
            }
            System.exit(0);
        }).start();
    }
}
