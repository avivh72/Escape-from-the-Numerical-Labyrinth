/**
 * This data class models the Boss and is a type of entity. The difference between the boss
 * and an entity is that the boss has health.
 * @author Aviv Haber. Created the class on June 2. Approximate Work: 0.5 hours
 * @author Rian Waterson. Fixed the Boss constructor to use the super class contrcutor. Approximate Work: 0.5 hours
 * @version 5.0
 */ 
public class Boss extends Entity
{
    /**
     * The starting health of the boss.
     */ 
    public static final double MAX_HEALTH=200;
    
    /**
     * The current health of this boss.
     */ 
    private int health;
    
    /**
     * Initializes the boss by setting the coordinates, width, height, and initial health.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param w The width.
     * @param h The height.
     */ 
    public Boss (int x, int y, int w, int h)
    {
        super (x,y,w,h);
        health=(int)MAX_HEALTH;
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
    }
}