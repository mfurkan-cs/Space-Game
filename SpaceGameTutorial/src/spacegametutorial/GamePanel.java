package spacegametutorial;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * GamePanel Class extends from JPanel
 * @author Murphy Code
 * @version 1.0
 */
public class GamePanel extends JPanel implements KeyListener {
    
    // properties
    private Timer timer;
    private int ballX;
    private int changeX;
    private int shipX;
    private int finishTime;
    private int startTime;
    private int noOfFires;
    private String message;
    private BufferedImage ship;
    private ArrayList<Rectangle> fires;
   
    // constructor
    public GamePanel() {
        
        // panel related code
        setBackground( Color.BLACK);
        setPreferredSize( new Dimension( 1200, 700) );
        addKeyListener( this);
        setFocusable( true);
        setFocusTraversalKeysEnabled( false);
        
        // initializing
        timer = new Timer( 10, new MyActionListener() );
        ballX = 0;
        changeX = 10;
        shipX = 0;
        message = "";
        noOfFires = 0;
        fires = new ArrayList<Rectangle>();
        try {
            ship = ImageIO.read( new File( "spaceShip.png") );
        } catch ( IOException ex) {
            System.out.println( "IO exception...");
        }
        
        timer.start();
        startTime = (int) System.currentTimeMillis();
        
    }


    
    // named inner class
    public class MyActionListener implements ActionListener {
        
        @Override
        public void actionPerformed( ActionEvent e) {
            
            // moving the ball
            if ( ballX < 0 || ballX > 1175 )
                changeX = -changeX;
            
            ballX += changeX;
            
            // moving fires
            for ( int i = 0; i < fires.size(); i++ ) {
                
                if ( fires.get( i).y > 0 )
                    fires.get( i).y -= 20;
                
                else
                    fires.remove( i);
             }
            
            // checking is the game over
            if ( isGameOver() ) {
                
                timer.stop();
                finishTime = (int) System.currentTimeMillis();
                message = "Congratulations...\n"
                        + "You spent " + ( finishTime - startTime) / 1000 + " seconds\n"
                        + "You fired " + noOfFires + " times";
                JOptionPane.showMessageDialog( new GamePanel(), message, "Game is over", 1);
                System.exit( 0);
            }
            
            
     
            repaint();
        }
        
    }
    
    // methods
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        // moving the ship
        int k = e.getKeyCode();
        
        if ( k == KeyEvent.VK_LEFT && shipX > 0 )
            shipX -= 10;
        
        if ( k == KeyEvent.VK_RIGHT && shipX < 1135 )
            shipX += 10;
        
        // creating fires
        if ( k == KeyEvent.VK_SPACE ) {
            fires.add( new Rectangle( shipX + 25, 545, 15, 25) );
            noOfFires++;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
    @Override
    public void paint( Graphics g) {
        super.paint(g);
        
        // drawing red ball
        g.setColor( Color.red);
        g.fillOval( ballX, 0, 25, 25);
        
        // drawing the ship
        g.drawImage( ship, shipX, 585, ship.getWidth() / 7, ship.getHeight() / 7, this);
        
        // drawing fires
        g.setColor( Color.GREEN);
        for ( Rectangle r : fires ) {
            
            g.fillRect( r.x, r.y, 15, 25);
            
        } 

    }

    @Override
    public void repaint() {
        super.repaint();
    }
    
    public boolean isGameOver() {
        
        for ( Rectangle r : fires ) {
            
            if ( r.intersects( new Rectangle( ballX, 0, 20, 20) ) )
                return true;
        }
        return false;
    }
    
}
