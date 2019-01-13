import javax.swing.*;
import java.awt.*;

public class Panelshake {
    public static void panelshake(JFrame frame){
        int num = 15;
        int i;
        Point point =frame.getLocationOnScreen();
        for (i = 10; i > 0; i--) {
            for (int j = num; j > 0; j--) {
                point.y += i;
                frame.setLocation(point);
                point.x += i;
                frame.setLocation(point);
                point.y -= i;
                frame.setLocation(point);
                point.x -= i;
                frame.setLocation(point);
            }
        }
    }
}
