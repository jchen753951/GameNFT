//Craig Benjamin
package MainFiles;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/* 
Unused imports: 
import javax.swing.plaf.synth.SynthSplitPaneUI;
import java.util.*;
*/

public class Window {
    private JFrame frame;
    public Window(Panel panel){
       
        frame = new JFrame();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
    
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(Color.black);
        //frame.setFocusable(true);
        frame.addWindowFocusListener(new WindowFocusListener(){

            @Override
            public void windowGainedFocus(WindowEvent e) {
              
                
            }

            @Override
            public void windowLostFocus(WindowEvent e) {  
        
                panel.getGame().windowFocusLost();
                //points to the windowFocusLost() method in Game
                
                /*
                player.resetBooleans; <-- cant do this here because player obj is not in this class
                //I have to use a method that refers to the class. I cannot direcly call it like I tried below:
                Player().resetBooleans();
                */
            }

        });
    }
    
}