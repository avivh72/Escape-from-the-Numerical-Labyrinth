import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;

/**
 * Creates an instances of each level, boss, and the menu, and adds each one to the frame.
 * Also controls how the menu buttons are handled, and switching between panels.
 * In general, it controls the flow of the program.
 * @author Aviv Haber. Wrote the core code for the Gam thread, which handles the flow of the levels May 29. This had to be redone several times because of game freezing. Approximate Work: 7 hours
 * @author Rian Waterson. Created the class and added all the panels and button listeners June 3. Created inner panel classes June 4. Approximate Work: 7 hours
 * @version 5.0
 */ 
public class NumericalLabyrinthFrame extends JFrame implements ActionListener
{   
    /**
     * The main Level object which the mazes and bosses will be stored in, one at a time.
     */ 
    private Level lev;
    
    /**
     * The introduction panel.
     */ 
    private Introduction intro;
    
    /**
     * The high score list panel.
     */ 
    private HighScoreList hsl;
    
    /**
     * The main menu panel.
     */ 
    private MainMenu menu;
    
    /**
     * The audio object which controls music playback.
     */ 
    private Audio sound;
    
    /**
     * The instructions panel that is used for math and game instrcution..
     */ 
    private InstructionPanel instruct;
    
    /**
     * The main thread object which handles the consecutive execution of levels.
     */ 
    private Game thread;
    
    /**
     * The credit roll panel.
     */ 
    private CreditRoll cr;
    
    /**
     * The name asker panel.
     */ 
    private NameAsker name;
    
    /**
     * The end game info panel.
     */ 
    private EndGameInfo egi;
    
    /**
     * The total time spent playing levels, in milliseconds.
     */ 
    private long totalTime;
    
    /**
     * The name of the player.
     */ 
    private String playerName;
    
    /**
     * Creates and initilaizes the JFrame and the fields. Also adds action listeners to the various buttons in other panels.
     */ 
    public NumericalLabyrinthFrame ()
    {
        super ("Escape From The Numerical Labyrinth");
        new SplashScreen ();
        sound=new Audio();
        menu = new MainMenu();
        instruct=new InstructionPanel();
        name = new NameAsker();
        hsl=new HighScoreList();
        egi=new EndGameInfo();
        intro=new Introduction();
        
        setSize (646,548);
        setLocationRelativeTo (null);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setIconImage (new ImageIcon ("res/johnnyDown.png").getImage());
        setResizable (false);
        
        menu.playButton.addActionListener (this);
        menu.exitButton.addActionListener (this);
        menu.instructionsButton.addActionListener (this);
        menu.mathInstButton.addActionListener (this);
        menu.highScoreButton.addActionListener (this);
        
        instruct.backButton.addActionListener (this);
        
        name.enterButton.addActionListener (this);
        name.backButton.addActionListener (this);
        
        hsl.backButton.addActionListener (this);
        hsl.resetButton.addActionListener (this);
        
        egi.continueButton.addActionListener (this);
        
        add (menu);
        
        sound.playMusic();
        setVisible (true);
    }
    
    /**
     * Called whenever a button is clicked. In general, this method
     * handles swithcing between panels. It adds panels, removes panels,
     * then revalidates and repaints.
     */ 
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource()==menu.exitButton)     
        {
            sound.stopMusic();
            dispose ();
        }
        else if (e.getSource()==menu.playButton)
        {
            remove (menu);
            add (name);
            name.focus();
            revalidate ();
            repaint ();
        }
        else if (e.getSource()==menu.instructionsButton)
        {
            remove (menu);
            add (instruct);
            instruct.setImage (0);
        }
        else if (e.getSource()==menu.mathInstButton)
        {
            remove (menu);
            add (instruct);
            instruct.setImage (1);
        }
        else if (e.getSource()==menu.highScoreButton)
        {
            remove (menu);
            add (hsl);
            revalidate ();
            repaint ();
        }  
        else if (e.getSource()==name.enterButton)
        {
            playerName=name.getName();
            remove (name);
            mainLoop ();
        }
        else if (e.getSource()==name.backButton)
        {
            remove (name);
            name.resetField();
            add (menu);
            revalidate ();
            repaint ();
        }
        else if (e.getSource()==instruct.backButton)
        {
            remove (instruct);
            add (menu);
            revalidate ();
            repaint ();
        }
        else if (e.getSource()==hsl.backButton)
        {
            remove (hsl);
            add (menu);
            revalidate ();
            repaint ();
        }
        else if (e.getSource()==hsl.resetButton)
        {
            hsl.resetFile();
        }
        else if (e.getSource()==egi.continueButton)
        {
            remove (egi);
            add (menu);
            revalidate ();
            repaint ();
        }
    }
    
    /**
     * Starts the Game thread, which starts the entire game.
     */ 
    public void mainLoop ()
    {
        thread=new Game();
        thread.start();   
    }
    
    /**
     * Inner thread class that goes through the execution of each level. Also
     * handles level win and loss, and adds the end game info and credit roll panel.
     * At the end, the menu is added.
     */ 
    public class Game extends Thread
    {
        int [] temp=new int[2];
        public void run()
        { 
            totalTime=0;
            
            add (intro);
            revalidate ();
            repaint();
            intro.run();
            remove (intro);
            
            outer:
            for (int x=0;x<3;x++)//go through 3 levels
            {
                inner:
                for (int y=0;y<2;y++)//go through maze then boss
                {
                    if (y==0)//if it is a maze
                    {
                        lev=new MazeLevel(x+1);
                    }
                    else //if it is a boss
                    {
                        lev=new BossLevel(x+1);
                    }                   
                    add (lev);
                    revalidate ();
                    repaint();
                    temp=lev.run();
                    remove (lev);
                    totalTime+=temp[1];
                    if (temp[0]!=0) //if they died in the level then exit the loop
                    {
                        System.out.println (temp[0]);
                        break outer;
                    }
                }
            }
            if (temp[0]==0)//if the player won, show credits and end game info
            {
                cr=new CreditRoll();
                add (cr);
                revalidate();
                repaint();
                cr.run();
                remove (cr);
                revalidate ();
                repaint ();
                egi.update();
                add (egi);
                revalidate ();
                repaint ();             
                return;
            }
            add (menu);
            revalidate();
            repaint();
        }
    }
    
    public class InstructionPanel extends JPanel
    {
        BufferedImage[] bg;
        int index=0;
        
        JButton backButton;
    
        public InstructionPanel ()
        {
            bg=new BufferedImage[2];
            try 
            {
                bg[0]=ImageIO.read (new File ("res/instructions.png"));
                bg[1]=ImageIO.read (new File ("res/learn.png"));
            }
            
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            setLayout (null);
            backButton = new JButton ("Back");
            backButton.setBorderPainted(false);
            backButton.setFocusPainted(false);
            
            add(backButton);
            backButton.setBounds (290,480,80,25);
        }
        
        public void setImage (int i)
        {
            index=i;
            this.revalidate ();
            this.repaint ();
        }
    
        public void paintComponent (Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(bg[index],0,0,null);
        }
    }
    
    /**
     * This inner class creates the name asker panel. This panel
     * simply prompts the user for their name in a text field.
     * The user can continue with the name, or go back to the menu.
     */ 
    public class NameAsker extends JPanel
    {
        /**
         * The current name of the player.
         */ 
        private String name;
        
        /**
         * The label next to the button.
         */ 
        private JLabel label;
        
        /**
         * The text input field for the name.
         */ 
        private JTextField field;
        
        /**
         * The confirm button that goes to the next screen.
         */ 
        JButton enterButton;
        
        /**
         * The back button that returns to the menu.
         */ 
        JButton backButton;
        
        /**
         * Sets up the JPanel and fields. Also initializes the buttons.
         */ 
        public NameAsker ()
        {
            setLayout (null);
            JPanel jp = new JPanel ();
            label= new JLabel ("Player Name:");
            label.setFont (new Font ("Century Gothic", Font.PLAIN, 16));
            
            enterButton=new JButton ("Enter");
            backButton=new JButton ("Back");
            field = new JTextField (20);
            field.setFont (new Font ("Century Gothic", Font.PLAIN, 12));
            resetField();
            
            jp.setLayout (new FlowLayout(FlowLayout.CENTER,20,20));
            jp.add (label);
            jp.add (field);
            jp.add (enterButton);
            jp.setBounds (20,230,600,60);
            backButton.setBounds (300,300,75,25);
            add (jp);
            add (backButton);
        }
        
        /**
         * Return the String the user entered in the field.
         * @return The name of the player.
         */ 
        public String getName ()
        {
            return field.getText();
        }
        
        /**
         * Resets the field's text to the default value.
         */ 
        public void resetField ()
        {
            field.setText ("Your Name");
        }
        
        /**
         * Puts the cursor on the field and moves the cursor to the end.
         */ 
        public void focus ()
        {
            resetField();
            field.requestFocus();
            field.setCaretPosition(9);
        }
    }
    
    /**
     * This inner class creates the end game info panel. This panel
     * simply displays to the user their name and total time.
     * It also adds their score to the high score list  and tells the
     * user whether they made the high score table.
     */ 
    public class EndGameInfo extends JPanel
    {
        /**
         * Label that diplsays the name.
         */ 
        private JLabel nm;
        
        /**
         * Label that displays the time.
         */ 
        private JLabel tm;
        
        /**
         * Label that displays whether the user made the high score table.
         */ 
        private JLabel label;
        
        /**
         * Reference to button that returns to the menu.
         */ 
        JButton continueButton;
        
        /**
         * Sets up the panel, fields, and buttons.
         */ 
        public EndGameInfo ()
        {
            Font small=new Font ("Century Gothic", Font.PLAIN, 16);
            Font verySmall=new Font ("Century Gothic", Font.PLAIN, 12);
            
            setLayout (null);
            
            nm=new JLabel ("");
            tm=new JLabel ("");
            
            label = new JLabel ("");
            nm.setFont (small);
            tm.setFont (small);
            label.setFont (verySmall);
            continueButton=new JButton ("Continue");
            
            nm.setBounds (20,170,600,20);
            tm.setBounds (20,200,600,20);
            label.setBounds (20,230,600,20);
            continueButton.setBounds (270,270,100,25);
            
            add(nm);
            add(tm);
            add(label);
            add(continueButton);
        }
        
        /**
         * Sets the text of the labels based on the data from playerName and totalTime.
         */ 
        public void update ()
        {
            String text;
            boolean temp=hsl.addEntry (new HighScoreEntry(playerName,(int)totalTime/1000));
            if (temp)
            {
                text="Congratulations, your time was fast enough to be added to the high score table!";
            }
            else
            {
                text="Unfortunately, your time was not fast enough to make the high score table. Better luck next time!";
            }
            label.setText(text);
            nm.setText("Player Name: "+ playerName);
            tm.setText("Total Time: "+ totalTime/1000 + " Seconds");
        }
    }
}