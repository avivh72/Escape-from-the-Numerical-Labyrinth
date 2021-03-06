import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * The class responsible for creating a basic maze level (subtype of Level), which includes a maze. The maze contains a 2D array of rooms, and each 
 * element in the matrix contains a problem that must be solved. Solving the problem onlocks the doors to the other rooms.
 * Incorrectly answering a question will lose the player health. Problems are solved by stepping on the correct button.
 * The level is beaten once the player reaches the boss level.
 * This class also detects keyboard input and draws graphics accordingly. The level is all contained within a JPanel.
 * @author Aviv Haber. Created class and wrote code for wall collision May 25. Implemented room change system May 29. Implemented bottom UI bar June 1. Approximate Work: 7 hours
 * @author Rian Waterson. Wrote code for button collision, problem solving, and door breaking May 31. Fixed bug where game froze after solving problem June 2. Approximate Work: 7 hours
 * @version 5.0
 */  
public class MazeLevel extends Level implements ActionListener
{
    /**
     * The level reader object which will read the map files.
     */ 
    private LevelReader lr;
    
    /**
     * Stores the amount of time that has passed, in milliseconds since a problem has been solved.
     * This is used to have the doors uinlocked after a delay.
     */ 
    private long solveScheduledTime;
    
    /**
     * Stores whether the problem has been solved and the doors are scheduled to break.
     */ 
    private boolean solveScheduled;
    
    /**
     * Stores whether the player has reached the boss level.
     */ 
    private boolean inBoss;
    
    /**
     * Stores the current row of the matrix of rooms.
     */ 
    private int row;
    
    /**
     * Stores the current column of the matrix of rooms.
     */ 
    private int col;
    
    /**
     * The 2D array of rooms which represents the maze.
     */ 
    private Room [][] rooms;
    
    /**
     * Stores the background image.
     */ 
    private BufferedImage bg;
    
    /**
     * Stores 4 images of the walls for each direction. The indexes are based off the DirectionHelper class.
     */ 
    private BufferedImage [] walls;
    
     /**
     * Stores the character images for each direction the player can potentially face. The order of the elements are based on the DirectionHelper class.
     */ 
    private BufferedImage [] character;
    
    /**
     * Stores the image for the logs (locked doors) for the top and bottom of the screen.
     */ 
    private BufferedImage logsTopAndBottom;
    
    /**
     * Stores the image for the logs (locked doors) for the left and right sides of the screen.
     */ 
    private BufferedImage logsLeftAndRight;
    
    /**
     * Stores 2 images for each state of the button (pressed and unpressed). Index 0 is unpressed and index 1 is pressed.
     */ 
    private BufferedImage[] button;
    
    /**
     * Creates the JPanel, initializes all the instance variables, and loads the images.
     * @param num The level number.
     */ 
    public MazeLevel (int num)
    {
        super (304,190);
        
        solveScheduled=false;
        inBoss=false;
        levelNumber=num;
        formattedTime="Time: 0";
        hasLost=false;
        lr=new LevelReader("level"+levelNumber+".txt");
        rooms=lr.getRooms();
        row=lr.getStartingRow();
        col=lr.getStartingColumn();
        walls = new BufferedImage [4];
        character = new BufferedImage [4];
        button = new BufferedImage [2];
        
        setFocusable (true);
        addKeyListener (this);
        try
        {
            bg=ImageIO.read (new File ("res/level.png"));
            
            button[0]=ImageIO.read (new File ("res/buttonUnpressed.png"));
            button[1]=ImageIO.read (new File ("res/buttonPressed.png"));
            
            logsLeftAndRight=ImageIO.read (new File ("res/logsTall.png"));
            logsTopAndBottom=ImageIO.read (new File ("res/logsWide.png"));
            
            character[DirectionHelper.UP]=ImageIO.read (new File ("res/johnnyUp.png"));
            character[DirectionHelper.DOWN]=ImageIO.read (new File ("res/johnnyDown.png"));
            character[DirectionHelper.LEFT]=ImageIO.read (new File ("res/johnnyLeft.png"));
            character[DirectionHelper.RIGHT]=ImageIO.read (new File ("res/johnnyRight.png"));
            
            walls[DirectionHelper.UP]=ImageIO.read (new File ("res/wallUp.png"));
            walls[DirectionHelper.DOWN]=ImageIO.read (new File ("res/wallDown.png"));
            walls[DirectionHelper.LEFT]=ImageIO.read (new File ("res/wallLeft.png"));
            walls[DirectionHelper.RIGHT]=ImageIO.read (new File ("res/wallRight.png"));
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }
    
    /**
     * Analyzes the coordinates of the player and the buttons
     * to see if the player stepped on a button. If they have,
     * the appropriate logic will be carried out.
     */ 
    public void detectButtonCollision ()
    {
        int temp=-1;
        if (player.getX()>=rooms[row][col].buttons[0].getX() && player.getX()<=(rooms[row][col].buttons[0].getX()+rooms[row][col].buttons[0].getWidth()) && player.getY()>=rooms[row][col].buttons[0].getY() && player.getY()-15<=rooms[row][col].buttons[0].getY() && !rooms[row][col].buttons[0].getPressed() && !rooms[row][col].solved)
        {
            temp=0;
        }
        else if (player.getX()>=rooms[row][col].buttons[1].getX() && player.getX()<=(rooms[row][col].buttons[1].getX()+rooms[row][col].buttons[1].getWidth()) && player.getY()>=rooms[row][col].buttons[1].getY() && player.getY()-15<=rooms[row][col].buttons[1].getY() && !rooms[row][col].buttons[1].getPressed() && !rooms[row][col].solved)
        {
            temp=1;
        }
        else if (player.getX()>=rooms[row][col].buttons[2].getX() && player.getX()<=(rooms[row][col].buttons[2].getX()+rooms[row][col].buttons[2].getWidth()) && player.getY()>=rooms[row][col].buttons[2].getY() && player.getY()-15<=rooms[row][col].buttons[2].getY() && !rooms[row][col].buttons[2].getPressed() && !rooms[row][col].solved)
        {
            temp=2;
        }
        
        
        if (temp==-1)
        {
            return;
        }
        
        if (temp==rooms[row][col].prob.indexOfCorrect)
        {
            solveScheduled=true;
            solveScheduledTime=gameTimer.getTime();
            sound.playRight();
            repaint ();
        }
        else
        {
            sound.playWrong();
            player.decHealth (20);
            if (player.getHealth()<=0)
            {
                hasLost=true;
            }
            repaint();
        }
        rooms[row][col].buttons[temp].setPressed(true);
    }
    
    /**
     * Analyzes the velocities and coordinats of the player and
     * determines whether the player is allowed to move past walls.
     * If the player is allowed to move, this method also
     * increments the player's X and Y values.
     */ 
    public void detectWallCollision ()
    {
        if (player.getX()>32 && player.getXVelocity()<0 && player.getY()<413 && player.getY()>31 || player.getX()<582 && player.getXVelocity()>0 && player.getY()<413 && player.getY()>31)
        {
            player.incX(player.getXVelocity());
        }
        else if (player.getXVelocity()>0 && player.getY()>=205 && player.getY()<=240 && rooms[row][col].existentDoors[DirectionHelper.RIGHT] && rooms[row][col].solved)
        {
            player.incX(player.getXVelocity());
        }
        else if (player.getXVelocity()<0 && player.getY()>=205 && player.getY()<=240 && rooms[row][col].existentDoors[DirectionHelper.LEFT] && rooms[row][col].solved)
        {
            player.incX(player.getXVelocity());
        }
        else if (player.getY()>=412 || player.getY()<=32)
        {
            if (player.getXVelocity()>0 && player.getX()<=323 || player.getXVelocity()<0 && player.getX()>=292)
            {
                player.incX(player.getXVelocity());
            }
        }
        
        
        if (player.getY()>32 && player.getYVelocity()<0 && player.getX()>31 && player.getX()<584 || player.getY()<412 && player.getYVelocity()>0 && player.getX()>31 && player.getX()<584)
        {
            player.incY(player.getYVelocity());
        }
        else if (player.getYVelocity()>0 && player.getX()>=290 && player.getX()<=325 && rooms[row][col].existentDoors[DirectionHelper.DOWN] && rooms[row][col].solved)
        {
            player.incY(player.getYVelocity());
        }
        else if (player.getYVelocity()<0 && player.getX()>=290 && player.getX()<=325 && rooms[row][col].existentDoors[DirectionHelper.UP] && rooms[row][col].solved)
        {
            player.incY(player.getYVelocity());
        }
        else if (player.getX()>=584 || player.getX()<=31)
        {
            if (player.getYVelocity()>0 && player.getY()<=238 || player.getYVelocity()<0 && player.getY()>=210)
            {
                player.incY(player.getYVelocity());
            }
        }
    }
    
    /**
     * Analyzes the player's coordinates
     * to see if they have changed rooms. If they have, the
     * rows and column variables are incremented,
     * the coordinates are updated, and the character
     * index is adjusted.
     * 
     */ 
    public void detectRoomChange ()
    {
        byte dir=-1;
        if (player.getX()<-32)
        {
            dir=DirectionHelper.LEFT;
            col--;
        }
        else if (player.getX()>646)
        {
            dir=DirectionHelper.RIGHT;
            col++;
        }
        else if (player.getY()<-32)
        {
            dir=DirectionHelper.UP;
            row--;
        }
        else if (player.getY()>478)
        {
            dir=DirectionHelper.DOWN;
            row++;
        }
        
        if (dir==-1)
        {
            return;
        }
        
        if (rooms[row][col].isBoss)
        {
            inBoss=true;
            return;
        }
        Point p=DirectionHelper.getStartingPoint (dir);
        player.setX(p.x);
        player.setY(p.y);
        player.setCharacterIndex (dir);
        for (int x=0;x<3;x++)
        {
            rooms[row][col].buttons[x].setPressed(false);
        }
        repaint();
    }
    
    /**
     * Draws the required images on screen. This is called as necessary for every iteration in the game loop.
     * @param g  Object containing graphics context that is used for rendering images and 2d shapes.
     */ 
    @Override
    public void paintComponent(Graphics g) 
    { 
        g.setFont (fontRegular);
        g.setColor (Color.BLACK);
        g.fillRect (0,480,646,100);
        g.setColor (Color.WHITE);
        
        g.drawImage(bg, 0, 0, null); //draw background
        
        if (!rooms[row][col].existentDoors[DirectionHelper.LEFT]) //draw the walls and logs
        {
            g.drawImage (walls[DirectionHelper.LEFT],0,192,null);
        }
        else if (!rooms[row][col].solved)
        {
            g.drawImage (logsLeftAndRight,0,192,null); //left
        }
        if (!rooms[row][col].existentDoors[DirectionHelper.RIGHT])
        {
            g.drawImage (walls[DirectionHelper.RIGHT],608,192,null);
        }
        else if (!rooms[row][col].solved)
        {
            g.drawImage (logsLeftAndRight,608,192,null); //right
        }
        if (!rooms[row][col].existentDoors[DirectionHelper.UP])
        {
            g.drawImage (walls[DirectionHelper.UP],252,-1,null);
        }
        else if (!rooms[row][col].solved)
        {
            g.drawImage (logsTopAndBottom,274,-1,null); //top
        }
        if (!rooms[row][col].existentDoors[DirectionHelper.DOWN])
        {
            g.drawImage (walls[DirectionHelper.DOWN],252,448,null);
        }
        else if (!rooms[row][col].solved)
        {
            g.drawImage (logsTopAndBottom,274,448,null); //bottom
        }
        
        
        if (!solveScheduled && !rooms[row][col].solved) //draw the problem and buttons
        {
            for (int x=0;x<3;x++)
            {
                if (rooms[row][col].buttons[x].getPressed())
                {
                    g.drawImage (button[1],rooms[row][col].buttons[x].getX(),rooms[row][col].buttons[x].getY(),null);
                }
                else
                {
                    g.drawImage (button[0],rooms[row][col].buttons[x].getX(),rooms[row][col].buttons[x].getY(),null);
                }
                g.drawString (rooms[row][col].prob.options[x],rooms[row][col].buttons[x].getX()+8,rooms[row][col].buttons[x].getY()+25);
            }
            g.drawString (rooms[row][col].prob.question,290,250);
        }
        
        g.drawImage(character[player.getCharacterIndex()], player.getX(), player.getY(), null); //draw character
        
        g.drawString (formattedTime,560,507);
        g.drawString ("Level "+levelNumber,390,507);
        g.drawString ("Health:",10,507);
        g.drawRect (79,489,301,21);
        
        if (isPaused)
        {
            g.setColor (Color.BLACK);
            g.fillRect (150,200,350,200);
            g.setFont (fontLarge);
            g.setColor (Color.WHITE);
            g.drawString ("PAUSED",195,320);
        }
        
        g.setColor (Color.RED);
        g.fillRect (80,490,(int)((player.getHealth()/player.MAX_HEALTH)*300),20);
    }
    
    /**
     * The main game loop for the level. It calls the collision and coordinate update methods. It also
     * sleeps for 4ms. It only calls repaint if there has been a change since the last iteration (ie. a KeyEvent).
     * It also detects win conditions and exits as necessary.
     * @return An integer array  of 2 elements where the first element is the termination code and
     * the second element is the time it took to beat the level, in milliseconds. A code of 0 means
     * the level was beaten normally. Anything else indicates that the player died in the level.
     */
    public int[] run ()
    {
        gameTimer=new Timer();
        gameTimer.start();
        requestFocus();
        previousTime=t;
        while (!hasLost)
        { 
            detectWallCollision();
            detectButtonCollision();
            detectRoomChange();
            
            if (inBoss)
            {
                gameTimer.interrupt();
                return new int[]{0,(int)gameTimer.getTime()};
            }
            
            t=gameTimer.getTime();
            
            if ((previousTime/1000)!=(t/1000))
            {
                formattedTime = "Time: " + t/1000;
                repaint (0,480,646,100);
            }
            
            if (solveScheduled && (gameTimer.getTime()-solveScheduledTime)>=1000)
            {
                solveScheduled=false;
                rooms[row][col].solved=true;
                sound.playShatter();
                repaint();
            }
            
            if (player.getXVelocity()!=0 || player.getYVelocity()!=0)
            {
                repaint (player.getX()-2,player.getY()-2,32,35);
            }
            
            try
            {
                Thread.sleep (4);
            }
            catch (InterruptedException e)
            {
                e.getMessage();
            }            
        }
        
        gameTimer.interrupt();
        Graphics g2=getGraphics();
        g2.setFont(fontLarge);
        g2.setColor(Color.WHITE);
        g2.drawString("YOU LOSE",150,200);
        try
        {
            Thread.sleep (2000);
        }
        catch (InterruptedException e)
        {
            e.getMessage();
        }
        return new int[]{1,(int)gameTimer.getTime()};
    }
}