/**
 * The class models the design for an abstract problem.
 * It contains an answer and a problem. Many subclasses will inherit
 * from this to create different types of problems.
 * @author Aviv Haber. Created generic class and abstract methods May 21. Added randomIntRanged method June 2. Approximate Work: 2 hours
 * @author Rian Waterson. Created concrete fields and getters for the new answer and problem fields June 3. Approximate Work: 2 hours
 * @version 5.0
 */ 
public abstract class ProblemGenerator
{
    /**
     * The answer to the problem.
     */ 
    int answer;
    /**
     * The entire question represented as a string. I.e "5*4?"
     */ 
    String problem;
    
    /**
     * Generates a problem with the speciied difficulty.
     * @param difficulty The difficulty of the problem to generate.
     * @return A randomly generated problem with the specified difficulty.
     */ 
    public abstract Problem createProblem(int difficulty);
    
    /**
     * Returns the problem String.
     * @return The value of problem.
     */ 
    public String getProblem()
    {
        return problem;
    }
    
    /**
     * Returns the answer int.
     * @return The value of answer.
     */ 
    public int getAnswer()
    {
        return answer;
    }
    
    /**
     * A general purpose method for generating a random integer within a range.
     * @param min The minimum value of the random integer.
     * @param max The maximum value of the random integer.
     * @return A random integer between min and mix, inclusive.
     */ 
    public static int randomIntRanged (int min, int max)
    {
        return (int)(Math.random()*(max - min + 1) + min);
    }
}