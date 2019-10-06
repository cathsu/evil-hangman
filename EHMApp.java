import java.util.*;
import java.io.*;

/**
 * Write a description of class EHMApp here.
 * Evil Hangman Project 
 * Cathy Hsu and Janelle Uganiza
 * Version 1 (5/10/17)
 */
public class EHMApp
{
    public static void main(String[] args) throws FileNotFoundException
    {
        String fileName;
        Scanner inputFile;
        ArrayList<String> dictionary;
        String playAgain = " ";
        fileName = "dictionary.txt";
        dictionary = new ArrayList<String>(fileName.length());
        inputFile = new Scanner(new File(fileName));
        
        // read inputFile into dictionary ArrayList
        while (inputFile.hasNext())
        {
            dictionary.add(inputFile.next());
        }
        
        do {
            // Game startup
            Scanner userInput = new Scanner(System.in);
            boolean found = false; 
            int wordLength = 0; 
            do {
                System.out.println("Enter word length: "); 
                wordLength = userInput.nextInt();
        
                int i = 0; 
                while (!found && i<dictionary.size()){
                    if (dictionary.get(i).length() == wordLength)
                        found = true;    
                    i++; 
                }
            } while (!found); 
            
            int numGuesses = -1; 
            do {
                System.out.println("How many guesses allowed?"); 
                numGuesses = userInput.nextInt(); 
            }while (numGuesses < 1);
           
            String answer = "Y";
            do {
                System.out.println("Do you want a running total of the number of words?"); 
                answer = userInput.next().toLowerCase(); 
            } while (!(answer.substring(0,1).equals( "y") || answer.substring(0,1).equals("n")));
            
            // create dictionary
            Dictionary hangmanWords = new Dictionary(dictionary, wordLength, numGuesses);
       
            // play game
            play(userInput, hangmanWords, numGuesses, answer);
            
            // end game
            if (endGame(hangmanWords)) 
                System.out.println("Out of guesses, Loser!");
            else {
                System.out.println("Winner!");
            }
            do {
                System.out.println("Do you want to play again?"); 
                playAgain = userInput.next().toLowerCase();
            }while (! (playAgain.substring(0,1).equals("y") || playAgain.substring(0,1).equals("n"))); 
        }while (playAgain.substring(0,1).equals("y")); 
    }
    public static void play(Scanner userInput, Dictionary hangmanWords, int numGuesses, String answer)
    {
            while ((hangmanWords.getRemainingGuesses(numGuesses) > 0 && hangmanWords.order().contains("_")) )
            {
                boolean succeeded = false; 
                System.out.println("Guesses remaining: " + hangmanWords.remainingGuesses());
                System.out.println("Letters guessed: " + hangmanWords.guessedLetters());
                char userGuess = ' '; 
           
                 while (!succeeded && ((int)userGuess < 95 || (int)userGuess > 122)){
                    System.out.println("Enter a (lower-case) letter to guess: ");
                    userGuess = userInput.next().charAt(0);
                    if ((int)userGuess >= 95 && (int)userGuess <= 122)
                        succeeded = true; 
                }
                
                if (hangmanWords.guessedLetters().contains(userGuess)){
                    System.out.println("Letter was already guessed!");
                    numGuesses = hangmanWords.getRemainingGuesses(numGuesses)-1;
                }
                else 
                {
                    int letterCount = hangmanWords.store(userGuess);
                    if (letterCount == 0){
                        System.out.println("No " + userGuess + "'s");
                        numGuesses = hangmanWords.getRemainingGuesses(numGuesses)-1;
                    }
                    else {
                        System.out.println(letterCount + " " + userGuess + "(s)");
                        numGuesses = hangmanWords.getRemainingGuesses(numGuesses);
                    }
                }
                System.out.println("current word order: " + hangmanWords.order()); 
                if ((answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') && hangmanWords.order().contains("_"))
                    System.out.println("number of words remaining = " + hangmanWords.numWordsRemaining); 
                
                hangmanWords.setRemainingGuesses(numGuesses);
            }
    }
    
    public static boolean endGame(Dictionary hangmanWords)
    {
       return hangmanWords.order().contains("_"); 
    }
}
