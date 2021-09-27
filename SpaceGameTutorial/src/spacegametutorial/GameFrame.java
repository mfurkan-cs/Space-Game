package spacegametutorial;

import javax.swing.JFrame;

/**
 * GameFrame class extends from JFrame
 * @author Murphy Code
 * @version 1.0
 */
public class GameFrame extends JFrame {
    
    // properties
    private GamePanel gp;
    
    // constructor
    public GameFrame() {
        
        // frame related code
        setVisible( true);
        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setResizable( false);
        setFocusable( false);
        
        gp = new GamePanel();
        
        add( gp);
        gp.requestFocus();
        pack();
    }
  
}
