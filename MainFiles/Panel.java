//Craig Benjamin
package MainFiles;

import static MainFiles.Game.GAME_HEIGHT;
import static MainFiles.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

public class Panel extends JPanel  {
    
    private MouseInputs mouseInputs;
    private Game game;
    
    public Panel(Game game){
        this.setBackground(Color.white);  
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //keyboard inputs
        addMouseListener(mouseInputs); //mouse imputs 
        addMouseMotionListener(mouseInputs);
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("Size: " + GAME_WIDTH + ": " + GAME_HEIGHT);
    
    }
   
    public void updateGame() {
    
    }
    
    public void paintComponent(Graphics p){
        super.paintComponent(p);
        game.render(p);
    }

    public Game getGame(){
        return game;
    }
    
}
