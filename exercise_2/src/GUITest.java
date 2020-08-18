/**
 * Created by Shinelon on 2016/9/27.
 */
import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class GUITest extends Applet {
    public void init() {
        this.setSize(250, 250);
    }

    public void paint(Graphics g)
    {
        Color color ;
        for (int i=0; i<12; i++){
            color = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
            g.setColor(color);
            g.drawOval(50+5*i, 50+5*i, 120-10*i, 120-10*i);
        }
    }

    public static void main(String[] args){
        new GUITest();
    }
}
