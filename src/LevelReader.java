import java.util.Scanner;
import java.io.*;

/**
 * This class is responsible for reading a level file and returning a 2D array of rooms that is than read by the MazeLevel class.
 * @author Aviv Haber.  Wrote the core code for reading level files and storing rooms June 1. Approximate Work: 3 hours
 * @author Rian Waterson. Created class and planned out how the data would be stores and transfered May 28. Approximate Work: 3 hours
 * @version 5.0
 */ 
public class LevelReader
{
    /**
     * The scanner object that reads the level file.
     */ 
    private Scanner sc;
    
    /**
     * The 2D array that stores the room being read
     */ 
    private Room[][] rooms;
    
    /**
     * The problem generator for the reader which generates a random problem for each room.
     */ 
    private ProblemGenerator pg;
    
    /**
     * The row the player starts in.
     */ 
    private int rowStart;
    
    /**
     * The column the player starts in.
     */ 
    private int colStart;
    
    /**
     * The amount of rows in the room matrix.
     */ 
    private int rows;
    
    /**
     * The amount of columns in the room matrix.
     */ 
    private int cols;
    
    /**
     * Initilizes the scanner and reads the first few lines of text, which set up the
     * array and problem generator.
     * @param fileName The file name for the level text file.
     */ 
    public LevelReader (String fileName)
    {
        int temp;
        try
        {
            sc=new Scanner (new File ("res/" + fileName));
        }
        catch (Exception e)
        {
        }
        
        temp=sc.nextLine().charAt(0);
        if (temp=='m')
        {
             pg=new MultiplicationProblemGenerator();
        }
        else if (temp=='f')
        {
             pg=new FractionProblemGenerator();
        }
        else
        {
             pg=new AlgebraProblemGenerator();
        }
        
        rows=Integer.parseInt(sc.nextLine());
        cols=Integer.parseInt(sc.nextLine());
        
        rooms=new Room[rows][cols];
        
        readFile ();
    }
    
    /**
     * Reads the file and stores the 2D array of rooms in the rooms field.
     */ 
    private void readFile ()
    {
        String temp;
        boolean [] doors= new boolean [4];
        boolean isBoss;
        
        rowStart=Integer.parseInt(sc.nextLine());
        colStart=Integer.parseInt(sc.nextLine());
        
        outer:
        for (int x=0;x<rows;x++)
        {
            inner:
            for (int y=0;y<cols;y++)
            {
                temp=sc.next();
                
                if (temp.equals("nnnn0"))
                {
                     rooms[x][y]=null;
                     continue inner;
                }
                
                for (int i=0;i<4;i++)
                {
                     doors[i]=temp.substring(i,i+1).equals("1");
                }
                isBoss=temp.substring(4).equals("1");
                rooms[x][y]=new Room (doors,pg.createProblem(ProblemGenerator.randomIntRanged(1,3)),isBoss);
            }
        }
        sc.close();
    }
    
    /**
     * Returns a 2D array of rooms corresponding to the file.
     * @return A 2D of rooms repesenting the maze contained in the text file.
     */ 
    public Room[][] getRooms ()
    {
        return rooms;
    }
    
    /**
     * Returns the row the player starts at.
     * @return The value of rowStart.
     */ 
    public int getStartingRow ()
    {
        return rowStart;
    }
    
    /**
     * Returns the column the player starts at.
     * @return The value of colStart.
     */ 
    public int getStartingColumn ()
    {
        return colStart;
    }
}