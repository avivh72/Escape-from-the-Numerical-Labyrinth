import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.*;

/**
 * The class is responsible for creating a generic level. This class handles keyboard input, movement, timing, and sound. 
 * MazeLevel and BossLevel extend this class. The Level is all contained within a JPanel.
 * @author Aviv Haber. Created the class as a super class for MazeLevel and BossLevel May 29. Wrote core code for running level May 31. Approximate Work: 5 hours
 * @author Rian Waterson. Redesigned key input and movement to be more fluid and intuitive June 1. Approximate Work: 5 hours
 * @version 5.0
 */ 
public abstract class Level extends JPanel implements KeyListener, ActionListener
{
    /**
     * The player object in the level.
     */ 
    Player player;
    
    /**
     * The reference for the button that pauses the game.
     */ 
    JButton pauseButton;
    
    /**
     * The constant for the player's horizontal speed. Changing this value
     * will make the player faster or slower.
     */ 
    public static final int X_SPEED = 1;
    
    /**
     * The constant for the player's vertical speed. Changing this value
     * will make the player faster or slower.
     */ 
    public static final int Y_SPEED = 1;
    
    /**
     * The regular sized font used for drawing text at the bottom bar of the level.
     */ 
    Font fontRegular;
    
    /**
     * The large font used for displaying end level text and the pause screen text.
     */ 
    Font fontLarge;
    
    /**
     * The level number of this level.
     */ 
    int levelNumber;
    
    /**
     * The game timer for this level. It keeps track
     * of how much time has passed while the level is being played.
     */ 
    Timer gameTimer;
    
    /**
     * Stores how much time has passed, in milliseconds, since the level was started at the previous iteration of the game loop.
     */ 
    long previousTime;
    
    /**
     * Stores how much time has passed, in milliseconds, since the level was started.
     */ 
    long t;
    
    /**
     * Stores a properly formatted String showing the time. It is used for the bottom UI bar. The format is "Time: x"
     */ 
    String formattedTime;
    
    /**
     * Stores whether the player has died yet.
     */ 
    boolean hasLost;
    
    /**
     * Stores whether the game is currently paused.
     */ 
    boolean isPaused;
    
    /**
     * The Audio object for the level that controls sound playback.
     */ 
    Audio sound;
    
     /**
     * Stores the arrow key most recently pressed. The possible values are based on the DirectionHelper class. A value of -1 indicates
     * that no arrow key is being pressed.
     */ 
    int keyPressed;
    
    /**
     * Iniitializes the instance variables to their default values and sets up the player.
     * @param x The stating x coordinate of the player.
     * @param y The stating y coordinate of the player.
     */ 
    public Level (int x, int y)
    {
        player = new Player (x,y,29,32);
        keyPressed=-1;
        isPaused=false;
        
        setLayout (null);
        pauseButton=new JButton("Pause");
        pauseButton.addActionListener (this);
        pauseButton.setBorderPainted(false);
        pauseButton.setFocusPainted(false);
        add (pauseButton);
        
        pauseButton.setBounds (455,487,100,25);
        fontRegular=new Font ("Century Gothic",Font.PLAIN,18);
        fontLarge = new Font ("Century Gothic",Font.BOLD,72);
        sound= new Audio();
        previousTime=0;
        hasLost=false;
        t=0;
    }
    
    /**
     * Called when the user presses the pause button. This method
     * either resumes or pauses the game.
     */ 
    public void actionPerformed (ActionEvent e)
    {
        if (pauseButton.getText().equals("Pause"))
        {
            pauseButton.setText ("Resume");
            player.setXVelocity(0);
            player.setYVelocity(0);
        }
        else
        {
            pauseButton.setText ("Pause");
            requestFocus();
        }
        isPaused=!isPaused;
        gameTimer.pauseAndResume();
        repaint ();
    }
    
    /**
     * Resets the values for xVelocity and yVelocity. Based on the value for keyPressed, the correct velocity variable will be incremented. This
     * method is called whenever a key is pressed to detect the changes.
     */ 
    public void processKeys ()
    {
        player.setXVelocity(0);
        player.setYVelocity(0);
        
        if (keyPressed==DirectionHelper.UP)
        {
            player.incYVelocity(-Y_SPEED); 
        }
        else if (keyPressed==DirectionHelper.DOWN)
        {
            player.incYVelocity(Y_SPEED);
        }
        else if (keyPressed==DirectionHelper.LEFT)
        {
            player.incXVelocity(-X_SPEED);
        }
        else if (keyPressed==DirectionHelper.RIGHT)
        {
            player.incXVelocity(X_SPEED);
        }
    }
    
    /**
     * When a key has been pressed, this method is called. It assigns keyPressed to the correct code and then calls
     * processKeys. Afterwards, it updates characterIndex, which is the direction the character is facing.
     * @param k  Object containing information about the key event source.
     */ 
    @Override
    public void keyPressed (KeyEvent k)
    { 
        if (k.getKeyCode()>=37 && k.getKeyCode()<=40)//if the key pressed is any of the arrow keys
        {
            if (keyPressed==-1)
            {
                keyPressed = k.getKeyCode () - 37;
                processKeys ();
                player.setCharacterIndex((byte)(k.getKeyCode()-37)); 
            }
        }
    }
    
    /**
     * When a key has been released, this method is called. It assigns keyPressed to -1 and resets the velocity variables.
     * @param k  Object containing information about the key event source.
     */ 
    @Override
    public void keyReleased(KeyEvent k) 
    {        
        if (k.getKeyCode()>=37 && k.getKeyCode()<=40)//if the key released is any of the arrow keys
        {
            keyPressed = -1;
            int key = k.getKeyCode () -37;
            player.setXVelocity(0);
            player.setYVelocity(0);
        }
    }
    
    /**
     * When a key has been typed, this method is called. Currently, it does no action but still must be defined.
     * @param k  Object containing information about the key event source.
     */ 
    @Override
    public void keyTyped (KeyEvent k)
    {
    }
    
    /**
     * The main game loop for the level
     * @return An array of two ints where the first number is the termination code and the second number is the
     * time it took to beat the level, in milliseconds. A code of 0 means the level was beaten, and anything else
     * means the player died in the level.
     */ 
    public abstract int[] run ();
    
    /**
     * Draws the all required images and shapes on screen. This is called as necessary for every iteration in the game loop.
     * @param g  Object containing graphics context that is used for rendering images and 2d shapes.
     */ 
    public abstract void paintComponent (Graphics g);
}