import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * This class is responsible for creating the main menu panel, which contains many
 * buttons navigating to different parts of the program. The listeners for the buttons
 * are contained within the NumericalLabyrinthFrame class, not this class.
 * @author Aviv Haber. Switched layout to absolute layout and customized coordinates for each button June 2. Approximate Work: 2 hours
 * @author Rian Waterson. Created class and made all the button variables May 30. Approximate Work: 2 hours
 * @version 5.0
 */
public class MainMenu extends JPanel
{ 
    /**
     * Stores the image for the title text.
     */ 
    BufferedImage title;
    
    /**
     * Stores the image for the background.
     */ 
    BufferedImage menuBG;
    
    /**
     * Stores the reference for the button that loads the game instructions.
     */ 
    JButton instructionsButton = new JButton ("Instructions");
    
    /**
     * Stores the reference for the button that starts the game.
     */ 
    JButton playButton = new JButton ("Play Game");
    
    /**
     * Stores the reference for the button that exits the game.
     */ 
    JButton exitButton = new JButton ("Exit Game");
    
    /**
     * Stores the reference for the button that loads the high scores.
     */ 
    JButton highScoreButton = new JButton ("High Scores");
    
    /**
     * Stores the reference for the button that loads the math instructions.
     */ 
    JButton mathInstButton = new JButton ("Learn Math");
    
    /**
     * Initilizes the panel and the buttons, as well as loads the images needed.
     */ 
    public MainMenu ()
    {
        try 
        {
            title=ImageIO.read (new File ("res/title.png"));
            menuBG=ImageIO.read (new File ("res/menuBG.png"));
        }
        
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        setSize(646,548);
        setLayout (null);
        
        instructionsButton.setBorderPainted(false);
        instructionsButton.setFocusPainted(false);
        
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        
        highScoreButton.setBorderPainted(false);
        highScoreButton.setFocusPainted(false);
        
        mathInstButton.setBorderPainted(false);
        mathInstButton.setFocusPainted(false);
        
        add(playButton,BorderLayout.SOUTH);
        add(instructionsButton,BorderLayout.SOUTH);
        add(mathInstButton,BorderLayout.SOUTH);
        add(highScoreButton,BorderLayout.SOUTH);
        add(exitButton,BorderLayout.SOUTH);
        playButton.setBounds (6,400,120,25);
        instructionsButton.setBounds (132,400,120,25);
        mathInstButton.setBounds (258,400,120,25);
        highScoreButton.setBounds (384,400,120,25);
        exitButton.setBounds (510,400,120,25);
    }
    
    /**
     * Draws the background image and title text image on the panel.
     * @param g  Object containing graphics context that is used for rendering images and 2d shapes.
     */ 
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuBG,0,0,null);
        g2.drawImage(title,50,40,null);
    }
    
}