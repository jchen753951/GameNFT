package gamestates;

import java.awt.event.MouseEvent;

import MainFiles.Game;
import ui.MenuButton;

public class State {
    
    protected Game game;

    public State(Game game){
            this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButton mb){ 
        return mb.getBounds().contains(e.getX(), e.getY()); //returns true if mouse is in this rect
    }

    public Game getGame(){
        return game;
    }
    
}
