import javax.swing.*;
import java.awt.*;

/**
 * Creates an small window that displays the company logo, then disappears after a few seconds.
 * This is displayed before the frame is.
 * @author Aviv Haber. Coded the entirety of the splash scren class June 4. Approximate Work: 0.5 hours
 * @author Rian Waterson. Redesigned the logo image used in the splash screen to look better June 5. Approximate Work: 0.5 hours
 * @version 5.0
 */ 
public class SplashScreen extends JWindow
{
    /**
     * Sets up the JWindow, draws the image, then destroys the JWindow after 2 seconds.
     */ 
    public SplashScreen ()
    {
        JLabel img = new JLabel (new ImageIcon ("res/logoSplash.png"));
        getContentPane().add(img, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo (null);
        setVisible (true);
        
        try
        {
            Thread.sleep (2000);
        }
        catch (InterruptedException e)
        {
            System.out.println (e.getMessage());
        }
        this.dispose();
    }
}