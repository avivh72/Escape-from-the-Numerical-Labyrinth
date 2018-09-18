import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * The class is responsible for creating a boss level, which is a type of Level. 
 * The boss level contains a Boss, and a ProblemGenerator, which generates a new question
 * every time the player answers one correctly. Getting right answers chips away the boss's health and
 * getting wrong answers chips away your health. While this happening, the player is slowly losing health, so the boss
 * must be beaten within a certain time. Questions are continually answered by stepping on the correct buttons until the 
 * boss runs out of health. This class also detects keyboard input and draws graphics accordingly. The level is all contained within a JPanel.
 * @author Aviv Haber. Created the class on May 14. Implemented problem generation, health loss, and pausing on June 2. Approximate Work: 5 hours
 * @author Rian Waterson. Added the collision methods for walls and buttons one June 1. Rewrote the paintComponent method to increase efficiency on June 4. Approximate Work: 5 hours
 * @version 5.0
 */  
public class BossLevel extends Level
{
    /**
     * The main room which contains the difficult problems.
     */ 
    private Room mainRoom;
    
    /**
     * The generator for the problems.
     */ 
    private ProblemGenerator pg;
    
    /**
     * Stores which level nuber this is.
     */ 
    private int levelNumber;
    
    /**
     * Stores two images of the demon: one where the demon is normal,
     * and one where the demon has been hit.
     */ 
    private BufferedImage[] demonImage;
    
    /**
     * Stores the background image.
     */ 
    private BufferedImage bg;
    
    /**
     * Stores the images for the 4 walls on each direction. The order of the elements are based on the DirectionHelper class.
     */ 
    private BufferedImage [] walls;
    
    /**
     * Stores the character images for each direction the player can potentially face. 
     */ 
    private BufferedImage [] character;
    
    /**
     * Stores two images of the button: one where the button is pressed, and when where the button
     * is not pressed.
     */ 
    private BufferedImage[] button;
    
    /**
     * The Boss object which is contained in the room.
     */ 
    private Boss demon;
    
    /**
     * Stores whether the demon has been hit or not.
     */ 
    private boolean demonHit;
    
    /**
     * Stores whether the player has moved since
     * correctly answering the last question.
     */ 
    private boolean hasMoved;
    
    /**
     * Stores the amount of time that has passed,
     * in milliseconds, since the demon has been hit last.
     */ 
    private long demonHitTime;
    
    /**
     * Stores whether the boss has beaten or not.
     */ 
    private boolean hasWon;
    
    /**
     * Stores the amount of time, in milliseconds,
     * that has passed since the last time
     * the player lost health. This is used for 
     * the gradual health loss mechanic.
     */ 
    private long healthLossTime;
    
    /**
     * Stores how frequently health should be lost
     * depending on the level number.
     */ 
    private int[] healthLossIntervals;
    
    /**
     * Initializes the Boss by setting the fields to their default values.
     * All the images are also loaded here.
     * @param level The level number of the boss.
     */ 
    public BossLevel (int level)
    {
        super (304,400);
        levelNumber=level;
        player.setCharacterIndex (DirectionHelper.UP);
        demon=new Boss(200,40,192,192);
        
        walls = new BufferedImage [4];
        character = new BufferedImage [4];
        button = new BufferedImage [2];
        demonImage=new BufferedImage[2];
        healthLossIntervals=new int[3];
        
        healthLossIntervals[0]=600;
        healthLossIntervals[1]=1500;
        healthLossIntervals[2]=3000;
        
        demonHit=false;
        hasMoved=true;
        hasWon=false;
        formattedTime="Time: 0";
        
        setFocusable (true);
        addKeyListener (this);
        try
        {
            bg=ImageIO.read (new File ("res/level.png"));
            
            demonImage[0]=ImageIO.read (new File ("res/demon.png"));
            demonImage[1]=ImageIO.read (new File ("res/demonHit.png"));
            
            button[0]=ImageIO.read (new File ("res/buttonUnpressed.png"));
            button[1]=ImageIO.read (new File ("res/buttonPressed.png"));
            
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
        
        if (level==1)
        {
            pg=new MultiplicationProblemGenerator();
        }
        else if (level==2)
        {
            pg=new AlgebraProblemGenerator();
        }
        else
        {
            pg=new FractionProblemGenerator();
        }
        mainRoom=new Room((new boolean[]{false,false,false,false}),pg.createProblem(2),true);
        
        for (int x=0;x<3;x++)
        {
            mainRoom.buttons[x].incY(30);
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
        if (player.getX()>=mainRoom.buttons[0].getX() && player.getX()<=(mainRoom.buttons[0].getX()+mainRoom.buttons[0].getWidth()) && player.getY()>=mainRoom.buttons[0].getY() && player.getY()-15<=mainRoom.buttons[0].getY() && !mainRoom.buttons[0].getPressed())
        {
            temp=0;
        }
        else if (player.getX()>=mainRoom.buttons[1].getX() && player.getX()<=(mainRoom.buttons[1].getX()+mainRoom.buttons[1].getWidth()) && player.getY()>=mainRoom.buttons[1].getY() && player.getY()-15<=mainRoom.buttons[1].getY() && !mainRoom.buttons[1].getPressed())
        {
            temp=1;
        }
        else if (player.getX()>=mainRoom.buttons[2].getX() && player.getX()<=(mainRoom.buttons[2].getX()+mainRoom.buttons[2].getWidth()) && player.getY()>=mainRoom.buttons[2].getY() && player.getY()-15<=mainRoom.buttons[2].getY() && !mainRoom.buttons[2].getPressed())
        {
            temp=2;
        }
        
        
        if (temp==-1 || !hasMoved)
        {
            if (temp==-1)
            {
                hasMoved=true;
            }
            return;
        }
        
        if (temp==mainRoom.prob.indexOfCorrect)
        {
            demonHit=true;
            demonHitTime=gameTimer.getTime();
            demon.decHealth(20);
            mainRoom.prob=pg.createProblem(2);
            mainRoom.reset();
            hasMoved=false;
            repaint ();
            sound.playDamage();
            if (demon.getHealth()<=0)
            {
                hasWon=true;
            }
        }
        else
        {
            sound.playWrong();
            player.decHealth (20);
            mainRoom.buttons[temp].setPressed(true);
            repaint();
        }
    }
    
    /**
     * Analyzes the coordinates of the player and the buttons
     * to see if the player should be allowed to move. If they
     * are allowed to move, the X and Y of the player is incremented.
     */ 
    public void detectBoundaryCollision ()
    {
        boolean yOutsideDemon=(player.getY()+player.getHeight()<demon.getY() || player.getY()>demon.getY()+demon.getHeight()-25);
        boolean xOutsideDemon=(player.getX()+player.getWidth()<demon.getX() || player.getY()>demon.getX()+demon.getWidth()+10);
        if (player.getX()>32 && player.getXVelocity()<0 || player.getX()<582 && player.getXVelocity()>0)
        {          
            player.incX(player.getXVelocity()); 
  
        }
        if (player.getY()>235 && player.getYVelocity()<0 || player.getY()<412 && player.getYVelocity()>0)
        {
            player.incY(player.getYVelocity());
        }
    }
    
    /**
     * Draws the all required images and shapes on screen. This is called as necessary for every iteration in the game loop.
     * @param g  Object containing graphics context that is used for rendering images and 2d shapes.
     */ 
    @Override
    public void paintComponent (Graphics g)
    {
        g.setFont (fontRegular);
        g.setColor (Color.BLACK);
        g.fillRect (0,480,646,100);
        
        g.drawImage(bg, 0, 0, null); //draw background
        
        g.drawImage (walls[DirectionHelper.LEFT],0,192,null);
        g.drawImage (walls[DirectionHelper.RIGHT],608,192,null);
        g.drawImage (walls[DirectionHelper.UP],252,-1,null);
        g.drawImage (walls[DirectionHelper.DOWN],252,448,null);
        
        g.fillRect (32,230,576,5);
        
        g.setColor (Color.WHITE);
        
        for (int x=0;x<3;x++)
        {
            if (mainRoom.buttons[x].getPressed())
            {
                g.drawImage (button[1],mainRoom.buttons[x].getX(),mainRoom.buttons[x].getY(),null);
            }
            else
            {
                g.drawImage (button[0],mainRoom.buttons[x].getX(),mainRoom.buttons[x].getY(),null);
            }
            g.drawString (mainRoom.prob.options[x],mainRoom.buttons[x].getX()+8,mainRoom.buttons[x].getY()+25);
        }
        g.drawString (mainRoom.prob.question,280,300);
        
        if (!demonHit)
        {
            g.drawImage(demonImage[0],demon.getX(),demon.getY(), null); //draw demon
        }
        else
        {
            g.drawImage(demonImage[1],demon.getX(),demon.getY(), null); //draw demon
        }
        
        g.drawImage(character[player.getCharacterIndex()], player.getX(), player.getY(), null); //draw character
        
        g.drawString (formattedTime,560,507);
        g.drawString ("Boss "+levelNumber,390,507);
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
        g.fillRect (80,490,(int)((player.getHealth()/player.MAX_HEALTH)*300),20); //player health       
        g.fillRect (402,40+(int)(177-(demon.getHealth()/demon.MAX_HEALTH)*177),10,(int)((demon.getHealth()/demon.MAX_HEALTH)*177)); //demon health
    }
    
    /**
     * The main game loop for the boss. It calls the collision and coordinate update methods. It also
     * sleeps for 4ms. It only calls repaint if there has been a change since the last iteration (ie. a KeyEvent).
     * It also detects win conditions and exits as necessary.
     * @return An integer array  of 2 elements where the first element is the termination code and
     * the second element is the time it took to beat the boss, in milliseconds. A code of 0 means
     * the boss was beaten normally. Anything else indicates that the player lost to the boss.
     */
    @Override
    public int[] run ()
    {
        gameTimer=new Timer();
        gameTimer.start();
        requestFocus();
        previousTime=t;
        int code=-1;
        healthLossTime=gameTimer.getTime();
        while (!hasLost)
        { 
            detectBoundaryCollision ();
            detectButtonCollision();
            
            t=gameTimer.getTime();
            
            if ((previousTime/1000)!=(t/1000))
            {
                formattedTime = "Time: " + t/1000;
                repaint (0,480,646,100);
            }
            
            if (gameTimer.getTime()-healthLossTime>healthLossIntervals[levelNumber-1])
            {
                player.decHealth(1);
                repaint (0,480,646,100);
                healthLossTime=gameTimer.getTime();
            }
            
            if (demonHit && (gameTimer.getTime()-demonHitTime)>=400)
            {
                demonHit=false;
                repaint();
            }
            
            if (player.getXVelocity()!=0 || player.getYVelocity()!=0)
            {
                repaint (player.getX()-2,player.getY()-2,32,35);
            }

            if (hasWon)
            {
                code=0;
                break;
            }
            if (player.getHealth()<=0)
            {
                hasLost=true;
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
        if (code==0)
        {
            g2.drawString("LEVEL COMPLETE",40,200);
        }
        else
        {
            g2.drawString("YOU LOSE",150,200);
        }
        try
        {
            Thread.sleep (2000);
        }
        catch (InterruptedException e)
        {
            e.getMessage();
        }
        return new int[]{code,(int)gameTimer.getTime()};
    }
}