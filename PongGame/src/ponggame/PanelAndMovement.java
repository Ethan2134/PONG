
package ponggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
//initialze the game and images
public class PanelAndMovement extends JPanel implements ActionListener{
    int width = 800, height = 600;
    int x = 50, y = 0, initVelX = 3, initVelY = 3, paddleV = 30, velX, velY;
    int paddleWidth = 20, paddleHeight = 90;
    int paddle1X = 20, paddle1Y = 300, paddle2X = width - 20 - paddleWidth, paddle2Y = 300;
    int score1 = 0, score2 = 0;
    Font scoreFont = new Font("Comic Sans MS", Font.BOLD, 35);
    boolean running = false, scored;
    int ballSize = 25;
    char direction1 = 'a', direction2 = 'a';
    ImageIcon ball = new ImageIcon(this.getClass().getResource("/images/pongball.png"));
    
    
    Timer t;
    //constructor for the panel and movement
    public PanelAndMovement(){
        t = new Timer(5, this);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        running = true;
        velX = initVelX;
        velY = initVelY;
        Image img = ball.getImage();
        Image imgScale = img.getScaledInstance(ballSize, ballSize, Image.SCALE_SMOOTH);
        ball = new ImageIcon(imgScale);
        t.start();
    }
    @Override
    public void paintComponent(Graphics g){
        
            super.paintComponent(g);
            draw(g);
    }
    //while running paint the components on screen
    public void draw(Graphics g){
        if(running){
            //g.fillOval(x, y, ballSize, ballSize);
            ball.paintIcon(this, g, x, y);
            g.fillRect(paddle1X, paddle1Y, paddleWidth, paddleHeight);
            g.fillRect(paddle2X, paddle2Y, paddleWidth, paddleHeight);
            g.setFont(scoreFont);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE", (width - metrics.stringWidth("SCORE")) / 2, g.getFont().getSize());
            g.drawString(score2+"", paddle1X, g.getFont().getSize());
            g.drawString(score1+"", paddle2X, g.getFont().getSize());
            repaint();
        }
    }
    //controls all paddle movement
    public void movePaddles(){
        switch(direction1){
            case 'U': 
                if(paddle1Y > 0){
                    paddle1Y -= paddleV; 
                    direction1 = 'a'; 
                }
                else{
                    paddle1Y = 0;
                }
                break;
            case 'D':
                if(paddle1Y < height-paddleHeight){
                    paddle1Y += paddleV;
                    direction1 = 'a';
                }
                else{
                    paddle1Y = height-paddleHeight;
                }
        }
        switch(direction2){
            case 'U': 
                if(paddle2Y > 0){
                    paddle2Y -= paddleV; 
                    direction2 = 'a'; 
                }
                else{
                    paddle2Y = 0;
                }
                break;
            case 'D':
                if(paddle2Y < height-paddleHeight){
                    paddle2Y += paddleV;
                    direction2 = 'a';
                }
                else{
                    paddle2Y = height-paddleHeight;
                }
        } 
    }
    //move the ball around the screen
    public void moveBall(){
        if(x < 0 || x > width-ballSize){
            score();
        }
        if(y < 0 || y > height-ballSize)
            velY = -velY;
        x += velX;
        y += velY;
    }
    //someone has scored a point, reset the ball and add the score
    public void score(){
        if(x <= 0){
            resetBall(1);
            score1++;
            scored = true;
        }
        if(x >= width-ballSize-1){
            resetBall(2);
            score2++;
            scored = true;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelAndMovement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //sets the ball in motion again and places in correct spot
    public void resetBall(int n){
        if(n == 1){
            x = paddle1X+paddleWidth+1;
            y = new Random().nextInt(height-ballSize);
            velX = initVelX;
            velY = initVelY;
        }
        else{
            x = paddle2X - 1;
            y = new Random().nextInt(height-1);
            velX = -initVelY;
            velY= initVelY;
        }
    }
    //the ball has hit a paddle, move the ball in the opposite direction
    public void hitPaddle(){
        if(y > paddle1Y && y < paddle1Y+paddleHeight && x <= paddle1X+paddleWidth){
            x = paddle1X+paddleWidth+1;
            velX = -velX;
        }
        if(y > paddle2Y && y < paddle2Y+paddleHeight && x+ballSize >= paddle2X){
            x = paddle2X-ballSize-1;
            velX = -velX;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            movePaddles();
            moveBall();
            hitPaddle();
        }
    }
    //check for key press
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W:
                    direction1 = 'U';
                    break;
                case KeyEvent.VK_S:
                    direction1 = 'D';
                    break;
                case KeyEvent.VK_UP:
                    direction2 = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    direction2 = 'D';
                    break;
            } 
        }
        
        
    }
    
    
    
}
