package main;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        // create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        ArrayList<int[][]> jackOLanternLocations = gp.tileM.getJackOLanternLocations();

        for (int i = 0; i < jackOLanternLocations.size(); i++) {

            int centerX = jackOLanternLocations.get(i)[0][0] * gp.tileSize;
            int centerY =jackOLanternLocations.get(i)[0][1] * gp.tileSize;
            double x = centerX - circleSize / 2 + gp.tileSize / 2;
            double y = centerY - circleSize / 2 + gp.tileSize / 2;

            Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
            Area lightArea = new Area(circleShape);

            screenArea.subtract(lightArea);
        }

        g2.setColor(new Color(0,0,0,0.7f));

        g2.fill(screenArea);

        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
