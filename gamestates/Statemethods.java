package gamestates;
import java.awt.*;
import java.awt.event.*;


public interface Statemethods {
// Every class that implements the Statemethods interface will have to create these classes:
// One change here applies to every class that implements it
    public void update();

    public void draw(Graphics p);

    public void mouseClicked(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);
   
}