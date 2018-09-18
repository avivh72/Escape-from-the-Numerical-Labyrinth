/**
 * This data class models a button, and is a type of entity. A button
 * is different from an Entity because it can be either pressed or
 * not pressed.
 * @author Aviv Haber. Created the class June 1. Approximate Work: 0.5 hours
 * @author Rian Waterson. Added the pressed variable and related methods on June 1. Approximate Work: 0.5 hours
 * @version 5.0
 */ 
public class Button extends Entity
{
    /**
     * Stores whether the button has been pressed.
     */ 
    private boolean pressed;
    
    /**
     * Initilizes the instance variables.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width.
     * @param height The height.
     */ 
    public Button (int x, int y, int width, int height)
    {
        super (x,y,width,height);
        pressed=false;
    }
    
    /**
     * Returns if the entity is pressed.
     * @return The value of pressed.
     */ 
    public boolean getPressed ()
    {
        return pressed;
    }
    
    /**
     * Sets pressed to the specified value.
     * @param newPressed The new value of pressed.
     */ 
    public void setPressed (boolean newPressed)
    {
        pressed=newPressed;
    }
}