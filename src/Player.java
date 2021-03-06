import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

/**
 * This class models a player object and extends Entity. It contains all the necessary data for the player
 * in order to play the game.
 * @author Aviv Haber. Created class and moved variables from Entity to here May 23. Approximate Work: 1 hour
 * @author Rian Waterson. Added a health variable and related methods May 25. Fixed decHealth method so that it cannot go below 0 June 3. Approximate Work: 1 hour
 * @version 5.0
 */ 
public class Player extends Entity
{    
    /**
     * The starting health of the player.
     */ 
    public static final double MAX_HEALTH=100;
    
    /**
     * The player's current health, out of 100.
     */ 
    private int health;
    
    /**
     * The X coordinate of the character.
     */ 
    private int x;
    
    /**
     * The Y coordinate of the character.
     */
    private int y;
    
    /**
     * The amount of pixels x is incremented for each iteration of the game loop. This value is based on
     * X_SPEED and is actually responsible for moving the character. A positive value will move the
     * character right, a negative value will move the character left, and a value of 0 will not
     * move the charater horizontally at all.
     */
    private static int xVelocity;
    
    /**
     * The amount of pixels y is incremented for each iteration of the game loop. This value is based on
     * Y_SPEED and is actually responsible for moving the character. A positive value will move the
     * character down, a negative value will move the character up, and a value of 0 will not
     * move the charater vertically at all.
     */
    private static int yVelocity;
    
    /**
     * The current direction the character is facing. This also corresponds to which character image to draw. The possible values are based on the DirectionHelper class.
     */ 
    private byte characterIndex;
    
    /**
     * Contrcuts a new player by initializing instance variables as well as reading the images.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width.
     * @param height The height.
     */ 
    public Player(int x, int y, int width, int height)
    {
        super (x,y,width,height);
        
        health = (int)MAX_HEALTH;
        xVelocity = 0;
        yVelocity = 0;
        
        characterIndex = DirectionHelper.UP;
    }
    
    /**
     * Sets the charcter image index to the specified value.
     * @param newCharInd The new index of the character image.
     */ 
    public void setCharacterIndex(byte newCharInd)
    {
        characterIndex=newCharInd;
    }
    
    
    /**
     * Returns the value of characterIndex.
     * @return The value of characterIndex.
     */ 
    public byte getCharacterIndex()
    {
        return characterIndex;
    }
    
    
    /**
     * Returns the value of xVelocity.
     * @return The value of xVelocity.
     */ 
    public int getXVelocity()
    {
        return xVelocity;
    }
    
    /**
     * Sets the value of xVelocity to the value specified.
     * @param newXVelocity The new value for xVelocity.
     */ 
    public void setXVelocity(int newXVelocity)
    {
        xVelocity=newXVelocity;
    }
    
    
    /**
     * Returns the value of yVelocity.
     * @return The value of yVelocity.
     */ 
    public int getYVelocity()
    {
        return yVelocity;
    }
    
    /**
     * Sets the value of yVelocity to the value specified.
     * @param newYVelocity The new value for yVelocity.
     */ 
    public void setYVelocity(int newYVelocity)
    {
        yVelocity=newYVelocity;
    }
    
    
    /**
     * Increments the value for xVelocity
     * @param amount The amount to increment.
     */ 
    public void incXVelocity(int amount)
    {
        xVelocity+=amount;
    }
    
    
    /**
     * Increments the value for yVelocity.
     * @param amount The amount to increment.
     */ 
    public void incYVelocity(int amount)
    {
        yVelocity+=amount;
    }
    
    /**
     * Returns the value of health.
     * @return The value of health.
     */ 
    public int getHealth()
    {
        return health;
    }
    
    /**
     * Sets the value of health to the value specified.
     * @param newHealth The new value for health.
     */ 
    public void setHealth(int newHealth)
    {
        health=newHealth;
    }
    
    /**
     * Decrements the value of health by the specified amount.
     * @param amount The amount of health to decrement.
     */ 
    public void decHealth(int amount)
    {
        health-=amount;
        if (health<0)
        {
            health=0;
        }
    }
}