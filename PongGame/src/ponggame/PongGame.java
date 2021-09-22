//A simple Pong game
//Use W/S and the UP/DOWN arrows to play
package ponggame;

import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class PongGame extends JFrame{
    
    public PongGame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.white);
        add(new PanelAndMovement());
        setTitle("Pong");
        setResizable(false);
        pack();
        setVisible(true); 
    }

    public static void main(String[] args) {
        
        new PongGame();
        
    }
}
