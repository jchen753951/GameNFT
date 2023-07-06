package Inputs;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import MainFiles.Panel;
import PlayerEntities.Player;
import gamestates.Gamestate;

public class MouseInputs implements MouseInputListener {
    private Panel panel;
    public MouseInputs(Panel panel){
        this.panel = panel;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
            switch(Gamestate.state){
                case PLAYING:
                    panel.getGame().getPlaying().mouseClicked(e);
                    break;
                default:
                    break;

            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(Gamestate.state){
            case MENU:
                panel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                panel.getGame().getPlaying().mousePressed(e);
                break;
            default:
                break;

        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(Gamestate.state){
            case MENU:
                panel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                panel.getGame().getPlaying().mouseReleased(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch(Gamestate.state){
            case PLAYING:
                panel.getGame().getPlaying().mouseDragged(e);
                break;
            default:
                break;

        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Gamestate.state){
            case MENU:
                panel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                panel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;

        }
        
    }
    
}
