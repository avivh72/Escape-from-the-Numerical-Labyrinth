/**
 * The class mmodels the design for a
 * fraction problem. It inherits from the abstract class
 * Problem.
 * @author Aviv Haber. Redesigned how createProblem works so that more convincing wrong answers are generated June 3. Approximate Work: 4 hours
 * @author Rian Waterson. Created the class and wrote the core of it May 29. Approximate Work: 4 hours
 * @version 5.0
 */ 
public class FractionProblemGenerator extends ProblemGenerator
{   
  /**
   * Returns the lowest common multiple between two numbers.
   * 
   * int temp = Storage for numbers being operated on
   * int n = first number being compared
   * int d = second number being compared
   * 
   * @param num1 first number being compared
   * @param num2 second number being compared
   * @return The lowest common multiple of num1 and num2.
   * */
  public static int getLCD (int num1, int num2)
  {
    int temp;
    int n=num1;
    int d=num2;
    while (d > 0) 
    {
      temp = d;
      d = n % d;
      n = temp;
    }
    return n;
  }

   /**
   * Actually contains the code for generating a problem of the type "Fraction".
   * 
   * String [] answers = holds the array of option to be passed into the created problem object
   * int indexOfAns = holds the index of the answer within the array of options, so that the correct answer will be known to the problem class
   * String finalAns = holds a string representation of the answer as a fraction
   * 
   * int nume1 = Holds the first value being operated on 
   * int denom1 = Holds the second value being operated on 
   * int nume2 = Holds the third value being operated on 
   * int denom2 = Holds the fourth value being operated on 
   * int temp5 = Holds the fifth value being operated on 
   * 
   * Loops and conditional statements are marked with numbers, their descriptions are below
   * 
   * Loops
   * 1 The first loop runs through the three elements of the options, excluding the one filled with th answer
   * 2 The second loop structure helps to create valid wrong answer values.
   * 3 The second loop structure allows for the comparison of each value in the array, making sure that non of the options are the same. runs through 3 values
   * 
   * 
   * Conditionals
   * 1 The first conditional corresponds with the difficulty, setting higher numbers to be operated as the difficulty rises
   * 2 - 3  The second and third conditionals are used to randomize the use of different operations within the question.
   * 4 The fourth conditional controls whether or the not the program will set the wrong answers, making sure that the right answer is not overwritten
   * 5 The fifth conditional does the comparison operation between the current answer and the ones already set. 
   * 6 The sixth conditional checks whether or not the full loop had been iterated through, so that a valid value for answer could be inputted.
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
    String finalAns="";
    
  /**
   * index of the answer within the array of the options
   * */
    int indexOfAns;
    
  /**
   * Storage for a final denominator value to be added to the answer string, denominator value after operations 
   * */
    int denom=0;
    
  /**
   * Storage for a final numerator value to be added to the answer string, numerator value after operations 
   * */
    int nume=0;
    
  /**
   * Lowest common denominator, used for simplification and addition/subtraction operations 
   * */
    int lcd=0;
    
  /**
   * Count variable used to check if a valid wrong answer has been generated
   * */
    int y;
        
  /**
   * Stores the value of the wrong answer so its validity can be compared and contrasted to the other values
   * */
    String wrongAnswer="";
      
  /**
   * random numerator used within the random generation of fraction questions
   * */
    int nume1=randomIntRanged(1,8);
    
  /**
   *  random denominator used within the random generation of fraction questions
   * */
    int denom1=randomIntRanged(1,8);
    
  /**
   *  random numerator used within the random generation of fraction questions
   * */
    int nume2=randomIntRanged(1,6);
    
  /**
   * random denominator used within the random generation of fraction questions
   * */
    int denom2=randomIntRanged(1,6);
    
  /**
   *  random numerator used within the random generation of fraction questions
   * */
    int nume3=randomIntRanged(1,5);
    
  /**
   * random denominator used within the random generation of fraction questions
   * */
    int denom3=randomIntRanged(1,5);
    
    switch (difficulty)//Conditional 1 
    {
      case 1:
        int temp=randomIntRanged (0,1);
        if (temp==0)//Conditional 3
        {
          problem = nume1+"/" + denom1 + " + " + nume2+ "/" + denom2;
          denom= (denom1*denom2)/getLCD(denom1,denom2); 
          nume= (nume1*(denom / denom1) + nume2*(denom/denom2));
          lcd= getLCD(nume, denom);
          finalAns =nume/lcd + " / " + denom/lcd;
        }
        else
        {
          problem = nume1+"/" + denom1 + " - " + nume2+ "/" + denom2;
          denom= (denom1*denom2)/getLCD(denom1,denom2); 
          nume= (nume1*(denom / denom1) - nume2*(denom/denom2));
          lcd= getLCD(nume, denom);
          finalAns =nume/lcd + " / " + denom/lcd;
        }
        break;
      case 2: 
        temp=randomIntRanged (0,1);
        if (temp==0)//Conditional 2
        {
          problem = nume1+"/" + denom1 + " * " + nume2+ "/" + denom2;
          lcd= getLCD(nume1*nume2,denom1*denom2);
          finalAns =((nume1*nume2)/lcd)+"/"+((denom1*denom2)/lcd); 
        }
        else
        {
          problem = nume1+"/" + denom1 + " � " + nume2+ "/" + denom2;
          lcd= getLCD(nume1*denom2,denom1*nume2);
          finalAns =((nume1*denom2)/lcd)+"/"+((denom1*nume2)/lcd); 
        }
        break;
      case 3: 
        problem = nume1 +"/" + denom1 + " + " + nume2 + "/" + denom2 +  " � " + nume3+ "/" + denom3;    
        nume2*=denom3;
        denom2*=nume3;
        denom= (denom1*denom2)/getLCD(denom1,denom2);
        nume= (nume1*(denom / denom1) + (nume2) *(denom/denom2));
        lcd= getLCD(nume, denom);
        finalAns =nume/lcd + " / " + denom/lcd; 
        break;
    }
    
    indexOfAns=randomIntRanged(0,2);
    
    
    answers[indexOfAns]=finalAns;
    
    for (int x =0; x<3;x++)// Loop Structure 1
    {  
      if (x!=indexOfAns)//Conditional 4 
      {
       
        do // Loop Structure 2
        {
          nume1= Math.abs (nume - nume1*randomIntRanged(2,6));
          denom1=Math.abs (denom - denom1*randomIntRanged(2,6));
          lcd=getLCD(nume1,denom1);
        }
        while (lcd==0 || denom1==0|| nume1==0);
              
              wrongAnswer= nume1/lcd+ "/" + denom1/lcd;   
              
        for (y =0; y< 3;y++)// Loop Structure 3
        {
          if (answers[y].equals(wrongAnswer))// Conditional 4
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