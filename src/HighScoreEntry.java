/**
 * This data class models an entry in the high score table. It contains
 * a name and a score (time). This class is also comparable, because
 * the high score table will contain a list of HighScoreEntries that
 * must be sorted.
 * @author Aviv Haber. Created the class June 1. Approximate Work: 2 hours.
 * @author Rian Waterson. Made the class comparable and added the compareTo method June 3. Approximate Work: 2 hours
 * @version 5.0
 */
public class HighScoreEntry implements Comparable<HighScoreEntry>
{
    /**
     * The name of the player who got the high score.
     */ 
    private String name;
    
    /**
     * The total time of the high score.
     */ 
    private int time;
    
    /**
     * Initializes the instance variables.
     * @param n The name.
     * @param t The time.
     */ 
    public HighScoreEntry (String n, int t)
    {
        name=n;
        time=t;
    }
    
    /**
     * Returns the name of player who got the entry.
     * @return The value of name.
     */ 
    public String getName ()
    {
        return name;
    }
    
    /**
     * Returns the time of the entry.
     * @return The value of time.
     */ 
    public int getTime ()
    {
        return time;
    }
    
    /**
     * Sets the value of name to the value passed.
     * @param n The new name for the entry.
     */ 
    public void setName (String n)
    {
        name=n;
    }
    
    /**
     * Sets the value of time to the value passed.
     * @param t The new time for the entry.
     */ 
    public void setTime (int t)
    {
        time=t;
    }
    
    /**
     * Compares two HighScoresEntries based on time.
     * @return A positive value if this entry's time is greater than the other, a negative
     * value if this entry's time is smaller than the other, and 0 if the two are equal.
     * @param other The other entry to compare to.
     */ 
    public int compareTo (HighScoreEntry other)
    {
        return getTime()-other.getTime();
    }
}