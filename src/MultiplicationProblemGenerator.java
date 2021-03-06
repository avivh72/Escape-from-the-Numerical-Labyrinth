/**
 * The class models the design for a
 * multiplication problem. It inherits from the abstract class
 * Problem.
 * @author Aviv Haber. Rewrote code to properly generate questions and answers in a way where there would be no duplicates. Approximate Work: 3 hours
 * @author Rian Waterson. Created class and wrote core code for createProblem() May 28. Approximate Work: 3 hours
 * @version 5.0
 */ 

public class MultiplicationProblemGenerator extends ProblemGenerator
{ 
  /**
   * Actually contains the code for generating a problem of the type "Multiplication".
   * 
   * String [] answers = holds the array of option to be passed into the created problem object
   * int indexOfAns = holds the index of the answer within the array of options, so that the correct answer will be known to the problem class
   * int multiple1 = holds the first number being multiplied
   * int multiple2 = holds the second number being multiplied
   * 
   * Loops and conditional statements are marked with numbers, their descriptions are below
   * 
   * Loops
   * 1 The first loop runs through the three elements of the options, excluding the one filled with th answer
   * 2 The second loop structure allows for the comparison of each value in the array, making sure that non of the options are the same. runs through 3 values
   * 
   * 
   * Conditionals
   * 1 The first conditional corresponds with the difficulty, setting higher numbers to be operated as the difficulty rises
   * 2 The second conditional controls whether or the not the program will set the wrong answers, making sure that the right answer is not overwritten
   * 3 The third conditional helps randomize the generation of wrong answers
   * 4 The fourth conditional does the comparison operation between the current answer and the ones already set. 
   * 5 The fifth conditional checks whether or not the full loop had been iterated through, so that a valid value for answer could be inputted.
   * 
   * @param difficulty The difficulty of the problem to generate.
   */ 
  public Problem createProblem (int difficulty)
  {
        /**
   * Stores the array off answers to be passed into the new problem
   * */
    String [] answers={"","",""};
    
     /**
   * storage of the value of the actual answer
   * */
    int indexOfAns;
    
      /**
   * Count variable used to check if a valid wrong answer has been generated
   * */
    int y;
    
       /**
   * Stores the value of the wrong answer so its validity can be compared and contrasted to the other values
   * */
    String wrongAnswer="";
    
        /**
   *  random number used within the random generation of multiplication questions
   * */
    int multiple1 = 0;
    
        /**
   *  random number used within the random generation of multiplication questions
   * */
    int multiple2 = 0;
    
    
    switch (difficulty) // Conditional 1
    {
      case 1: 
        multiple1 = randomIntRanged(1,6);
        multiple2 = randomIntRanged(1,6);
        break;
      case 2: 
        multiple1 = randomIntRanged(1,20);
        multiple2 = randomIntRanged(1,20);
        break;
      case 3: 
        multiple1 = randomIntRanged(1,12);
        multiple2 = randomIntRanged(1,12);
        break;
    }
    
    problem = multiple1 + "*" + multiple2 + "=?";
    answer=multiple1*multiple2;
    indexOfAns=randomIntRanged(0,2);
    answers[indexOfAns]=Integer.toString(answer);
    
    for (int x =0; x<3;x++)// Loop Structure 1
    {
      if (x!=indexOfAns)// Conditional 2
      {
        
        if ((answer-randomIntRanged(1,4))%2==0)// Conditional 3
          wrongAnswer=Integer.toString(answer-multiple2*(randomIntRanged(2,6)));
        else 
          wrongAnswer=Integer.toString(answer+multiple1*(randomIntRanged(2,6)));
        
        for (y =0; y< 3;y++)// Loop Structure 2
        {
          if (answers[y].equals(wrongAnswer) || Integer.parseInt(wrongAnswer)<1 )// Conditional 4
          {
            x--;
            break;
          }
        }
        if (y==3)// Conditional 5
        {
          answers[x]=wrongAnswer;
        }  
      }
    }    
    return (new Problem (problem, answers, indexOfAns));
  }  
}