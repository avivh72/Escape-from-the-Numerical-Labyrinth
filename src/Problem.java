/**
 * This data class models a math problem. It contains a question and
 * an array of 3 possible answers, only one of which is correct. It also
 * contains the index of the correct answer.
 * must be sorted.
 * @author Aviv Haber. Created class and core fields, setters and getters May 29. Approximate Work: 1 hour
 * @author Rian Waterson. Recoded class and the field it contains so it makes more sense with the level classes June 2. Approximate Work: 1 hour
 * @version 5.0
 */ 
public class Problem
{
    /**
     * The math question.
     */ 
    String question;
    
    /**
     * An array of 3 possible answers. Only one of these are correct.
     */ 
    String [] options;
    
    /**
     * The index of options that contains the correct answer;
     */ 
    int indexOfCorrect;
    
    /**
     * Initializes the object based on the values passed.
     * @param question The question.
     * @param options  The possible answer.
     * @param index The index of the correct answer.
     */ 
    public Problem (String question, String[] options, int index)
    {
        this.question=question;
        this.options=options;
        indexOfCorrect=index;
    }
    
    /**
     * Returns the question String.
     * @return The value of question.
     */ 
    public String getQuestion ()
    {
        return question;
    }
    
    /**
     * Returns the array of possible answers.
     * @return The value of options.
     */ 
    public String[] getOptions ()
    {
        return options;
    }
}