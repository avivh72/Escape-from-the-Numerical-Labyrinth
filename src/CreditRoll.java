import java.awt.*;
import javax.swing.*;
import java.awt.Graphics2D;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
/**
 * The class creates a credit animation crediting those who created the graphics and other resources for the game.
 *
 * When the animation is run the user will see a scrolling credits screen starting with a congratulatory message,
 * It stays still for a few seconds and then starts scrolling down towards the crediting of each of those people
 * that played a part in the the game development.
 *
 *
 * @author Aviv Haber. Fixed a bug where the image was drawn in the wrong spot June 2. Made the varaibles reset after execution June 3. Approximate Work: 2 hours
 * @author Rian Waterson. Created the class June 1. Approximate Work: 2 hours
 * @version 5.0
 */
public class CreditRoll extends JPanel
{
    /**
     * x Position everything else is based around, credits are drawn relative to this x coord
     **/
    private int xPos = 100;
    
    /**
     * y Position everything else is based around, credits are drawn relative to this y coord
     **/
    private int yPos = 650;
    
    /**
     * stores what run of the animation part has happened
     **/
    private int firstRun = 0;
    
    /**
     * Stores the image for the title drawn as part of the animation
     **/
    BufferedImage title;
    
    
    /**
     * Reads the images to be used in the animation, and initializes the screen to its default state.
     */
    public CreditRoll ()
    {
        try
        {
            title = ImageIO.read (new File ("res/title.png"));
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage ());
        }
        repaint ();
    }
    
    
    /**
     * paint method, paints the different stages of the animation, calls to repaint() redraw the screen with the changed
     * elements.
     *
     * @param g Passes a reference to the graphics class of the frame, allowing the use of the Graphics methods
     */
    public void paint (Graphics g)
    {
        super.paintComponent (g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font ("Abadi MT Condensed Extra Bold", Font.PLAIN, 14);
        
        g2.setColor (Color.BLACK);
        g2.fillRect (0, 0, getWidth (), getHeight ());
        
        g2.drawImage (title, xPos + 100, yPos - 220, null);
        
        g2.setColor (Color.WHITE);
        g2.setFont (new Font ("Abadi MT Condensed Extra Bold", Font.PLAIN, 24));
        g2.drawString ("Congratulations, you helped Johnny", xPos + 10, yPos - 600);
        g2.drawString ("escape the labyrinths and his textbook.", xPos, yPos - 560);
        g2.drawString ("While solving the problems, you helped him ", xPos - 20, yPos - 520);
        g2.drawString ("learn enough so he can ace the exam tomorrow!", xPos - 40, yPos - 480);
        
        g2.setFont (font);
        g2.drawString ("Avri Games Incorporated", xPos - 40, yPos - 60);
        
        g2.drawString ("Developers", xPos, yPos);
        g2.drawString ("Rian Waterson", xPos + 105, yPos);
        g2.drawString ("Aviv Haber", xPos + 105, yPos + 20);
        
        g2.drawString ("Team Leader", xPos, yPos + 60);
        g2.drawString ("Aviv Haber", xPos + 105, yPos + 60);
        g2.drawString ("Team Member", xPos, yPos + 80);
        g2.drawString ("Rian Waterson", xPos + 105, yPos + 80);
        
        g2.drawString ("Client", xPos, yPos + 120);
        g2.drawString ("VK, CEO of VK Enterprises", xPos + 105, yPos + 120);
        
        
        g2.drawString ("Music", xPos, yPos + 160);
        g2.drawString ("X-Ray", xPos + 105, yPos + 160);
        
        g2.drawString ("Graphics", xPos, yPos + 200);
        g2.drawString ("Isaiah658", xPos + 105, yPos + 200);
        
        g2.drawString ("Sheep", xPos + 105, yPos + 220);
        g2.drawString ("Peter Blain", xPos + 105, yPos + 240);
        g2.drawString ("MoikMellah", xPos + 105, yPos + 260);
        g2.drawString ("Codeus", xPos + 105, yPos + 280);
        g2.drawString ("PixelShines", xPos + 105, yPos + 300);
        g2.drawString ("Rian Waterson", xPos + 105, yPos + 320);
        g2.drawString ("Aviv Haber", xPos + 105, yPos + 340);
        
    }
    
    
    /**
     * run method, stays in a loop repaint the screen and changing variables until the animation is done.
     * 
     * Conditionals and Loop structures are marked with numbers and described below
     * 
     * Conditionals
     * 1. The first Conditional changes the variables changing animation parts
     * 
     * Loop Structures
     * 1. Runs the animations loop until the final part of the animation has finished
     * 
     * @return returns 0 when the running of the animation stops
     */  
    public int run ()
    {
        try
        {
            Thread.sleep (1500);
        }
        catch (InterruptedException m)
        {
            m.getMessage ();
        }
        while (yPos > -400)//Loop structure 1
        {
            try
            {
                Thread.sleep (30);
            }
            catch (InterruptedException m)
            {
                m.getMessage ();
            }
            yPos--;
            repaint ();
            if (firstRun == 0)//Conditional 1
            {
                firstRun++;
                try
                {
                    Thread.sleep (2000);
                }
                catch (InterruptedException m)
                {
                    m.getMessage ();
                }
            }
        }
        xPos=100;
        yPos=650;
        firstRun=0;
        return 0;
    }
}
