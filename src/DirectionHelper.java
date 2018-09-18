import java.awt.Point;

/**
 * A simple class that defines integer constants used as direction variables in other classes. The order of the directions is based on the ASCII key codes.
 * @author Aviv Haber. Added the getStartingPoint method May 30. Approximate Work: 1 hour
 * @author Rian Waterson. Created the class May 13. Approximate Work: 1 hour
 * @version 5.0
 */ 
public class DirectionHelper
{
    /**
     * The variable corresponding to the direction left.
     */ 
    public static final byte LEFT=0;
    
    /**
     * The variable corresponding to the direction up.
     */ 
    public static final byte UP=1;
    
    /**
     * The variable corresponding to the direction right.
     */ 
    public static final byte RIGHT=2;
    
    /**
     * The variable corresponding to the direction down.
     */ 
    public static final byte DOWN=3;
    
    /**
     * Gets the new position of the player after they move rooms,
     * based on which door they go through.
     * @param direction The door the player switched through.
     * @return A point containing the new coordinates of the player.
     */ 
    public static Point getStartingPoint (byte direction)
    {
        switch (direction)
        {
            case RIGHT:
                return new Point (50,220);
            case DOWN:
                return new Point (304,50);
            case LEFT:
                return new Point (600,220);
        }
        return new Point (304,402);
    }
}