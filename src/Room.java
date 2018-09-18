/**
 * The class mmodels the design for a room. It contains a problem
 * of any kind and a boolean array of doors which indicates which doors
 * are open and which are closed.
 * @author Aviv Haber. Designed and created class with these fields May 31. Approximate Work: 2 hours
 * @author Rian Waterson. Added reset method and figured out coordiantes for centred buttons June 1. Approximate Work: 2 hours
 * @version 5.0
 */  
public class Room 
{
    Button [] buttons;
    
    /**
     * Stores whether this room contains a boss.
     */ 
    boolean isBoss;
    
    /**
     * Stores whether the problem in this room has been solved.
     */ 
    boolean solved;
    
    /**
     * Stores which doors can potentially be walked through and which cannot.
     */ 
    boolean[] existentDoors;
    
    /**
     * Stores the problem for this room.
     */ 
    Problem prob;
    
    /**
     * Initializes the room by setting the instance variables to the values passed and sets unblocked doors to all false;
     * @param doors The value for the boolean array for door states.
     * @param prob The value for the Problem that the level stores.
     * @param isBoss Whether this room is a boos or not.
     */ 
    public Room(boolean[] doors, Problem prob, boolean isBoss)
    {
        buttons=new Button[3];
        buttons[0]=new Button (190,300,70,37);
        buttons[1]=new Button (290,300,70,37);
        buttons[2]=new Button (390,300,70,37);
        existentDoors=new boolean[4];
        for (int x=0;x<4;x++)
        {
            existentDoors[x]=doors[x];
        }
        this.prob=prob;
        this.isBoss=isBoss;
        solved=false;
    }
    
    /**
     * Resets the room as if it had never been entered.
     */ 
    public void reset ()
    {
        solved=false;
        for (int x=0;x<3;x++)
        {
            buttons[x].setPressed(false);
        }
    }
}