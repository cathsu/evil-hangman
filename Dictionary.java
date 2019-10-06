import java.util.*;
/**
 * Write a description of class Dictionary here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dictionary
{
    private int numGuesses; // count how many times the user has guessed
    private int numOfRemainingLetters; // number of remaining letters to guess
    private int numOfGuessesRemaining;
    public int numWordsRemaining; 
    private String wordOrder;
    private ArrayList<Character> userGuesses; // characters the user has guessed
    private ArrayList<String> remainingWords; // words that are remaining based on guesses
    private ArrayList<Character> guessedLetters = new ArrayList<Character>(); 
    private ArrayList<String> dictionary = new ArrayList<String>();
   
    public Dictionary(ArrayList<String> dictionary, int wordLength, int maxNumGuesses)
    {
        remainingWords = new ArrayList<String>();
        for (String word : dictionary) {
            if (word.length() == wordLength) {
                remainingWords.add(word);
            }
        }
        
        wordOrder = "";
        for (int i = 0; i < wordLength; i++)
              wordOrder += "_ ";
        userGuesses = new ArrayList<Character>();
        numGuesses = maxNumGuesses;
        numOfGuessesRemaining = numGuesses; 
    }
    
    public int getNumGuesses(int numGuesses){
        return numGuesses;  
    }
    
    public int getRemainingGuesses(int numGuesses){
        return numOfGuessesRemaining; 
    }
    
    public void setRemainingGuesses(int userGuess){
        numOfGuessesRemaining = userGuess; 
    }
    
    public void guessedLetter(){
        numGuesses--; 
    }
    
    public ArrayList<Character> guessedLetters()
    {
        return userGuesses;
    }
    
    public ArrayList<String> words()
    {
        return remainingWords;
    }
    
    public int remainingGuesses()
    {
        return numOfGuessesRemaining; // times the user has to guess
    }
    
    public String order()
    {
        return wordOrder;
    }
    
    public int store(char userGuess) {
        Map<String, ArrayList<String>> evilWord = new TreeMap<String, ArrayList<String>>();
        wordFamily(evilWord, userGuess);    
        return chooseWordFamily(evilWord, userGuess);
    }
    
    private String getString (char userGuess, String word) {
        String letterPosition = "";
        char[] letters = word.toCharArray(); // split word and place letters into array
        for (int i = 0; i < word.length(); i++) {
            if (letters[i] == userGuess) {
                letterPosition = letterPosition + userGuess + " ";
            } else {
                letterPosition += "_ ";
            }
        }
        
        return letterPosition;
    }
    
    private String mergeOrders(String order, String currentOrder) {
        boolean altered = false;
        for (int i = 0; i < order.length()-1; i += 2) {
            if ((order.charAt(i) != currentOrder.charAt(i)) && (!currentOrder.substring(i, i+1).equals("_"))) {
                order = order.substring(0, i) + currentOrder.charAt(i) + order.substring(i+1);
                altered = true;
            }
        }
        if (altered) {
            numGuesses++;
        }
        return order;
    }
    
    private void wordFamily(Map<String, ArrayList<String>> evilWord, char userGuess) {
        for (String word : remainingWords) {
            String pattern = getString(userGuess, word);      
            ArrayList<String> wordLocation = new ArrayList<String>();   
            if (evilWord.containsKey(pattern)) {
                wordLocation = evilWord.get(pattern);
            }
            wordLocation.add(word);
            evilWord.put(pattern, wordLocation);
        }   
    }
    
    private int chooseWordFamily(Map<String, ArrayList<String>> evilWord, char userGuess) {
        int maxWordCount = 0;
        String fixedOrder = "";
        for (String userOrder : evilWord.keySet()) {
            if (evilWord.get(userOrder).size() > maxWordCount) {
                maxWordCount = evilWord.get(userOrder).size();
                fixedOrder = userOrder;
                remainingWords = evilWord.get(userOrder);
            }
        }
        numWordsRemaining = remainingWords.size(); 
        int letterCount = 0;
        for (int i = 0; i < fixedOrder.length(); i++) {
            char current = fixedOrder.charAt(i);
            if (current == userGuess) {
                letterCount++;              
            }
        }
        wordOrder = mergeOrders(wordOrder, fixedOrder);
        userGuesses.add(userGuess);
        return letterCount;
    }
    
    /* Sources:
     * http://stackoverflow.com/questions/13878437/how-to-fill-an-array-of-characters-from-user-input 
     * http://stackoverflow.com/questions/24501237/given-a-arraylist-of-words-and-user-input-how-to-map-them-into-families-in
     * https://github.com/knkeniston/EvilHangman/blob/master/src/HangmanManager.java
     * https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
     * https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html
     * Lab 10
     */
}
