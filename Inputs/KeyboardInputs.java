package Inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import MainFiles.Panel;
import Utils.Constants.*;
import Utils.Constants.Directions.*;
import gamestates.Gamestate;

public class KeyboardInputs implements KeyListener {
    
    private Panel panel;
    public KeyboardInputs(Panel panel){
        this.panel = panel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(Gamestate.state){
            case MENU:
                panel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                panel.getGame().getPlaying().keyPressed(e);

                break;
            default:
                break;

        }

       
    }

    @Override
    public void keyReleased(KeyEvent e) {
            switch(Gamestate.state){
                case MENU:
                    panel.getGame().getMenu().keyReleased(e);
                    break;
                case PLAYING:
                    panel.getGame().getPlaying().keyReleased(e);

                    break;
                default:
                    break;

            }
    }
}
