/**
 * Models a stopwatch thread running concurrently alongside other aspects of the program. The core 
 * of the class is the t variable, which increases over time an can be accessed at any point.
 * The thread can also be stopped, paused, and resumed.
 * @author Aviv Haber. Created class with interupt feature and utilizing System.currentTimeMillis() May 23. Approximate Work: 3 hours
 * @author Rian Waterson. Redesigned class to use thread.sleep June 1. Added a pause/resume feature June 3. Approximate Work: 3 hours
 * @version 5.0
 */ 
public class Timer extends Thread
{
    /**
     * Stores the amount of time that has passed since the thread was started.
     */ 
    private long t=0;
    
    /**
     * Stores whether the thread is till running.
     */ 
    private boolean isRunning=true;
    
    /**
     * Stores whether the thread has been paused or not.
     */ 
    private boolean isPaused=false;
    
    /**
     * Stops the thread.
     */ 
    public void interrupt()
    {
        isRunning=false;
    }
    
    /**
     * Either pauses or resumes the thread.
     */ 
    public void pauseAndResume ()
    {
        isPaused=!isPaused;
    }
    
    /**
     * Starts the thread. The method works by running in a while loop. Each iteration,
     * the thread sleeps for 100ms, then t is incremented by 100.
     */ 
    public void run ()
    {
        while (isRunning)
        {
            try
            {
                Thread.sleep (100);
            }
            catch (InterruptedException e){}
            
            if (!isPaused)
            {
                t+=100;
            }
        }
    }
    
    /**
     * Returns the current amount of time passed, in milliseconds.
     * @return The value of t.
     */ 
    public long getTime ()
    {
        return t;
    }
}