import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MyHangman
{
    public static int MAX_GUESSES = 7;
   	public static MyArrayListHM<String> words = new MyArrayListHM<String>();
    public static MyArrayListHM<Character> secretWord = new MyArrayListHM<Character>();
    public static MyArrayListHM<Character> guessedWord = new MyArrayListHM<Character>();
    public static int guessesLeft;
	
	
	public static void loadWords(String filename)
	{
		try
		{
		File wordFile = new File(filename);
		Scanner scan = new Scanner(wordFile);
		int index = 0;
		
		while(scan.hasNextLine())
		{
			String data = scan.nextLine();
			
			words.add(index, data);
			index++;
			
		}
		scan.close();
	 }
	  
	 catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
     }
	 
	System.out.print("The words:");
	System.out.println(words);
	System.out.print("The sorted words:");
	if(words.sortList())
		 System.out.println(words);
	 
	}
	
	public static void startNewGame()
	{
		String selectedWord;
		Random random = new Random();
		int randomIndex= random.nextInt(words.getSize()); 
		selectedWord= words.get(randomIndex).toUpperCase();
		
		secretWord.clear();
		int index = 0;
		for(char c : selectedWord.toCharArray())
		{
			secretWord.add(index , c);
			index++;
		}
		
		guessedWord.clear();
		
		
		for(int i = 0 ; i < selectedWord.length() ; i++)
			guessedWord.add(i,'_');
		
		guessesLeft = MAX_GUESSES;
		
		   System.out.println();
		   System.out.println("Welcome to Hangman! ");
		   
		   System.out.println("Guessed word: " + guessedWord);
		   System.out.println("Guesses left: " + guessesLeft);
	}
	
	public static void  updateGuessedWord(char letter)
	{	
		ArrayList<Integer> listOfIndexesToBeUpdated = new ArrayList<>();
		
		for(int i = 0 ; i < secretWord.getSize() ; i++)
		{
			if((secretWord.get(i)) == letter)
			{
				 listOfIndexesToBeUpdated.add(i);;		
			}
		}
		
		for(int i = 0 ; i < listOfIndexesToBeUpdated.size() ; i++)
		{
					guessedWord.remove(listOfIndexesToBeUpdated.get(i));
					guessedWord.add(listOfIndexesToBeUpdated.get(i), letter);
		}	
	}
	
	public static void playGame()
	{
			
		Scanner input = new Scanner(System.in);
		
		while(true)
		{
			
			System.out.print("Enter a letter: ");
		    char letter = Character.toUpperCase(input.next().charAt(0));
			
			if(guessedWord.contains(letter))
			{
				System.out.println("letter already guessed , try again");
				
			}
			else if(secretWord.contains(letter))
			{
				System.out.println("Correct guess.");
				updateGuessedWord(letter);
			}
			else
			{
				System.out.println("Incorrect guess.");
				guessesLeft--;
			}
			
			System.out.println(guessedWord);
			
			if(guessesLeft <=0)
			{
				System.out.println("maximum Number of guesses reached! , the word was: " + secretWord);
				
				break;
			}
			
			if(guessedWord.isWordGuessed())
			{
				System.out.println("Congratulations! You guessed the word: " +  secretWord);
				
				break;
			}
			System.out.println("Guesses left: " +guessesLeft);
			
		}

			
		
	}

    public static void main(String[] args) {
		
		loadWords("words.txt");	
		startNewGame();
		playGame();
    }
	
}