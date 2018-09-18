import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * This class contians the list of high score entries and is
 * responsible for modifying it as well as reading and writing to
 * the high score test file. This class is also contained within
 * a JPanel and draws the scores.
 * @author Aviv Haber. Crated the class and wrote the methods realted to the list, fileIO, and entries June 3. Approximate Work: 3 hours
 * @author Rian Waterson. Wrote the JPanel related code and methods June 3. Approximate Work: 3 hours
 * @version 5.0
 */ 
public class HighScoreList extends JPanel
{
    /**
     * Stores a list of 10 high score entries that are sorted in increasing order according to time.
     */ 
    private ArrayList<HighScoreEntry> entries;
    
    /**
     * Stores the reference for the button that returns to the menu.
     */ 
    JButton backButton;
    
    /**
     * Stores the reference for the button that resets the file.
     */ 
    JButton resetButton;
    
    /**
     * Stores the background image for the panel.
     */ 
    private BufferedImage bg;
    
    /**
     * Stores the font used for drawing the scores.
     */
    private Font font;
    
    /**
     * Sets up the panel and the instance variables. This method also reads the bg image.
     */ 
    public HighScoreList ()
    {
        setLayout (null);
        backButton = new JButton ("Back");
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        add(backButton);
        backButton.setBounds (200,480,80,25);
        
        resetButton = new JButton ("Reset");
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        add(resetButton);
        resetButton.setBounds (300,480,80,25);
        font=new Font ("Century",Font.BOLD,18);
        
        try 
        {
            bg=ImageIO.read (new File ("res/highscore.png"));
        }
        
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        
        entries=new ArrayList<HighScoreEntry>();
        readFile();
    }
    
    /**
     * Reads the text file containing the high scores and stores them in the entries list.
     */ 
    public void readFile ()
    {
        Scanner sc;
        try
        {
            sc=new Scanner (new File ("res/highscores.txt"));                    
            for (int x=0;x<10;x++)
            {
                entries.add (new HighScoreEntry (sc.nextLine(),Integer.parseInt(sc.nextLine())));
            }
            sc.close();
        }
        catch (Exception e)
        {
            System.out.println (e.getMessage());
        }
    }
    
    /**
     * Writes the contents of the entries list to the file containing high scores.
     */ 
    public void writeFile ()
    {
        PrintWriter wr;
        try
        {
            wr = new PrintWriter (new FileWriter ("res/highscores.txt"));
            for (int x=0;x<10;x++)
            {
                wr.println (entries.get(x).getName());
                wr.println (entries.get(x).getTime());
            }
            wr.close();
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }
    
    /**
     * Resets the entries list and the file to the default values.
     * Every entry has a name of Bob and a time of 600 seconds.
     */ 
    public void resetFile ()
    {
        for (int x=0;x<10;x++)
        {
            entries.get(x).setName ("Bob");
            entries.get(x).setTime (600);
        }
        writeFile();
        repaint();
    }
    
    /**
     * Adds an entry to the entries list, sorts the list, then removes
     * the last element. After this, it writes to the file.
     * @return Whether the passed entry has made the high score list or not.
     * @param hs The high score entry to add.
     */ 
    public boolean addEntry (HighScoreEntry hs)
    {
        entries.add (hs);
        Collections.sort (entries);
        entries.remove (10);
        writeFile();
        return entries.contains (hs);
    }
    
    /**
     * Draws the list of 10 high score entries to the panel.
     */ 
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bg,0,0,null);
        g.setFont (font);
        for (int x=0;x<10;x++)
        {
            if (x!=9)
            {
                g.drawString ((x+1) + ".   Time: " + entries.get(x).getTime(),105,120+x*30);
                g.drawString ("Name: " + entries.get(x).getName(),320,120+x*30);
            }
            else
            {
                g.drawString ((x+1) + ". Time: " + entries.get(x).getTime(),105,120+x*30);
                g.drawString ("Name: " + entries.get(x).getName(),320,120+x*30);
            }
        }
    }
}