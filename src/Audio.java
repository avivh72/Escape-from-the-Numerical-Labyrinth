import java.io.*;
import javax.sound.sampled.*;
import java.util.*;

/**
 * This class is responsible for playing all the audio files found in the game. It loads
 * all the files, and has different methods for playing each file.
 * @author Aviv Haber. Worked on initially creating the class on May 29. Approximate Work: 2 hours 
 * @author Rian Waterson. Added more sound files to the class. May 31. Approximate Work: 2 hours
 * @version 5.0
 */ 
public class Audio
{
    /**
     * The sound file for when the user gets a math question wrong.
     */ 
    Clip wrong;
    /**
     * The sound file for when the user gets a math question right.
     */ 
    Clip right;
    /**
     * The sound file for when the wooden doors are shattered.
     */ 
    Clip shatter;
    /**
     * The sound file for the game's background music.
     */ 
    Clip music;
    /**
     * The sound file for when the demon takes damage.
     */ 
    Clip damage;
    /**
     * The sound file for when the vortex is spinning in the introduction.
     */ 
    Clip vortex;
    
    /**
     * Loads all the audio files and prepares them for playing.
     */
    public Audio ()
    {
        try
        {
            File file1 = new File("res/incorrect.wav");
            File file2 = new File("res/correct.wav");
            File file3 = new File("res/shatter.wav");
            File file4 = new File("res/music.wav");
            File file5 = new File("res/damage.wav");
            File file6 = new File("res/vortex.wav");
            AudioInputStream stream1 = AudioSystem.getAudioInputStream(file1);
            AudioInputStream stream2 = AudioSystem.getAudioInputStream(file2);
            AudioInputStream stream3 = AudioSystem.getAudioInputStream(file3);
            AudioInputStream stream4 = AudioSystem.getAudioInputStream(file4);
            AudioInputStream stream5 = AudioSystem.getAudioInputStream(file5);
            AudioInputStream stream6 = AudioSystem.getAudioInputStream(file6);
            wrong = AudioSystem.getClip();
            right = AudioSystem.getClip();
            shatter = AudioSystem.getClip();
            music = AudioSystem.getClip();
            damage = AudioSystem.getClip();
            vortex = AudioSystem.getClip();
            wrong.open(stream1);
            right.open(stream2);
            shatter.open(stream3);
            music.open(stream4);
            damage.open (stream5);
            vortex.open (stream6);
        }
        catch (UnsupportedAudioFileException e) 
        {
            System.out.println (e.getMessage());
        }
        catch (LineUnavailableException e) 
        {
            System.out.println (e.getMessage());
        }
        catch (IOException e) 
        {
            System.out.println (e.getMessage());
        }
    }
    
    /**
     * Plays the wrong answer sound.
     */ 
    public void playWrong ()
    {
        wrong.setFramePosition (0);
        wrong.start();
    }
    
    /**
     * Plays the right answer sound.
     */ 
    public void playRight()
    {
        right.setFramePosition (0);
        right.start();
    }
    
    public void playShatter ()
    {
        shatter.setFramePosition (0);
        shatter.start();
    }
    
    /**
     * Plays the wood shattering sound.
     */ 
    public void playDamage ()
    {
        damage.setFramePosition (0);
        damage.start();
    }
    
    /**
     * Plays the vortex spinning sound.
     */ 
    public void playVortex ()
    {
        vortex.setFramePosition (0);
        vortex.start();
    }
    
    /**
     * Plays and loops and backgroun music sound.
     */ 
    public void playMusic ()
    {
        music.setFramePosition (0);
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Stops the background music sound.
     */ 
    public void stopMusic ()
    {
        music.stop();
    }
}
