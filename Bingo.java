import java.io.*;
import java.util.*;




public class Bingo {

	public static BitSet checker = new BitSet(75);
	public static int[][] cardArray2D = new int[5][5];
	public static String[][] cardArray = new String[cardArray2D.length][cardArray2D.length];
	public static boolean HORIZONTALBINGO = false;
	public static boolean VERTICALBINGO = false;
	public static boolean DIAGONALBINGO = false;
	public static int counter = 0;
	public static int[] numbersCalled = new int[75]; 
	
	
	public static void main(String[] args)
	{
		
		int j = 0;
		String winType = "";
		String filename;
		Scanner input = new Scanner(System.in);
		
		//Receiving the file from which the bingo card numbers are located
		System.out.println("Input file with bingo card numbers: ");
		filename = input.nextLine();
		
		//Filling the Bingo card array with the numbers from the file
		cardArray2D = fillCard(filename);
		//Printing Bingo card before the game begins
		System.out.println("\nYOUR BINGO CARD : \n\n");
		printCard(cardArray2D);
		playGame();
		
		
		//Printing out which numbers were called during the Bingo run.
		System.out.println("\n\n");
		System.out.println("BINGO NUMBERS PICKED AT RANDOM FROM BIN :");
		for (int i = 0; i < counter; i++)
		{
			if ((j%9 != 0) || (j == 0))
			{
				System.out.printf("%8d", numbersCalled[i]);
			}
			else 
			{
				System.out.printf("\n%8d", numbersCalled[i]);
			}
			j++;
		}
		
		
		//Converting int[][] array to String[][]
		for (int i = 0; i < cardArray2D.length; i++)
		{
			for (j = 0; j < cardArray2D[0].length; j++)
			{
				Integer number = cardArray2D[i][j];
				cardArray[i][j] = number.toString();		
			}			
		}
			
		//Replacing the 0's in String[][] array to X's	
		for(int i = 0; i < cardArray.length; i++)
		{
			for (j = 0; j < cardArray[0].length; j++)
			{
				if (cardArray[i][j].compareTo("0") == 0)
				{
					cardArray[i][j] = "X";
				}
		
			}
		}
		
		//Checking to see what kind of win type it was
		if (HORIZONTALBINGO == true)
		{
			winType = "HORIZONTAL";
		}
		else if (VERTICALBINGO == true)
		{
			winType = "VERTICAL";
		}
		else if (DIAGONALBINGO == true)
		{
			winType = "DIAGONAL";
		}
		
		//Displaying type of win and number of random numbers that were picked from the bin
		System.out.println("\n\nYOU WIN WITH A " + winType + " BINGO AFTER " + counter + " PICKS!\n");
		
		//Displaying final bingo card with numbers called being marked off
		System.out.println("YOUR BINGO CARD : \n\n");
		printCard(cardArray);	
	
	}

	//Method to fill the 2D array with numbers from file
	public static int[][] fillCard(String filename)
	{
		//Declaring the card arrays
		int[] cardArray = new int[26];
		int[][] cardArray2D = new int[5][5];
		int i = 0; 
		int p = 0;
		Scanner input = new Scanner(System.in);
	
		//Reading the file and a catch in case the file isn't found
       		 try {
			input = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Filling the array
        while (input.hasNext() && (i < 25))
        {
        	cardArray[i] = input.nextInt();       
        	i++;
        }
        
        for (i = 0; i < cardArray2D.length; i++)
        {
            for (int j = 0; j < cardArray2D[0].length; j++)
            {
               cardArray2D[i][j] = cardArray[p];
               p++;
            } 
        }
        return cardArray2D;
	}
	
	//Method to print card with int type
	public static void printCard(int[][] cardArray2D)
	{
		int p = 0;
		System.out.println("   B    I    N    G    O  ");
		System.out.println("--------------------------");
		for (int i = 0; i < cardArray2D.length; i++)
		{
			for (int j = 0; j < cardArray2D[0].length; j++)
			{ 	//Checking to see if it should go to the next line
				if ((p%5 != 0) || (p == 0))
				{
				System.out.printf("|%3d ", cardArray2D[i][j]);
				}
				else
				{
				System.out.print("|\n--------------------------");
				System.out.printf("\n|%3d ", cardArray2D[i][j]);
				}
				p++;
			}
			
		}
		System.out.println("|");
	
	}

	//Method to print card with String type
	public static void printCard(String[][] cardArray2D)
	{
		int p = 0;
		System.out.println("   B    I    N    G    O  ");
		System.out.println("--------------------------");
		for (int i = 0; i < cardArray2D.length; i++)
		{  	//Checking to see if it should go to the next line
			for (int j = 0; j < cardArray2D[0].length; j++)
			{	
				if ((p%5 != 0) || (p == 0))
				{
				System.out.printf("|%3s ", cardArray2D[i][j]);
				}
				else
				{
				System.out.print("|\n--------------------------");
				System.out.printf("\n|%3s ", cardArray2D[i][j]);
				}
				p++;

			}
		}
		System.out.println("|");
	}


	
	//Method that calls method to generate random number and method to check for win
	public static void playGame()
	{
		boolean result = false;
		int randomInt = 0;
		
		//Loop to run until win is found
		while (result == false)
		{
			randomInt = numberGenerator();
			result = checkForWin();
			numbersCalled[counter] = randomInt;
			counter++;
		}	
	}
	
	
	//Method that generates a random number, picks a number that hasn't
	//been used, and marks the card if the number picked is on the card.
	public static int numberGenerator()
	{
		//Generating and checking if random integer has been used and setting it to true if it hasn't been.
		Random numberGenerator = new Random(); 
		int randomInt = numberGenerator.nextInt(75) + 1;
		while (checker.get(randomInt) == true)
		{
			randomInt = numberGenerator.nextInt(75) + 1;
		}
		checker.set(randomInt);
		
		
		//Checking to see if the random integer is in the array,
		//and setting the value to 0 at the index in which it is found. 
		for (int i = 0; i < cardArray2D.length; i++)
		{
			for (int j = 0; j < cardArray2D[0].length; j++)
			{
				if (cardArray2D[i][j] == randomInt)
					{
						cardArray2D[i][j] = 0;
					}
				else 
				{}
			}
		}
		return randomInt;
	}
	
	//Method that checks for win and assigns win type by setting a boolean value to the type variables
	public static boolean checkForWin()
	{
		int i = 0;
		int j = 0;
		int k = 0;
		int horizontalSum = 1;
		int verticalSum = 1;
		int diagonalSum1 = 1;
		int diagonalSum2 = 1;

		//Horizontal sum
		while ((horizontalSum != 0) && (i < cardArray2D.length))
		{
			horizontalSum = checkHorizontalSum(i);
			i++;
		}
		//Horizontal bingo check
		if (horizontalSum == 0)
		{
			HORIZONTALBINGO = true;
		}
		

		//Vertical sum
		while ((verticalSum != 0) && (j < cardArray2D[0].length))
		{
			verticalSum = checkVerticalSum(j);
			j++;
		}
		//Vertical Bingo check
		if (verticalSum == 0)
		{
			VERTICALBINGO = true;
		}
			
	
		//Left to Right diagonal sum
		for (i = 0; i < cardArray2D.length; i++)
       		 {
        	   diagonalSum1 += cardArray2D[i][i];
       		 }
		
		//Right to left diagonal sum
		for  (i = 0; i < cardArray2D.length; i++)
	        {
	           diagonalSum2 += cardArray2D[i][cardArray2D.length-i-1];
       		 }
		
		//Diagonal Bingo check
		if ((diagonalSum1 == 0) || (diagonalSum2 == 0))
		{
			DIAGONALBINGO = true;
		}

		
		//Overall check if there was a bingo
		if ((HORIZONTALBINGO == true) || (VERTICALBINGO == true) || (DIAGONALBINGO == true))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	//Method that sums the current row (inputed as a parameter)
	public static int checkHorizontalSum(int i)
	{
		int sum = 0;
			
		for (int j = 0; j < cardArray2D[0].length; j++)
		{
			sum += cardArray2D[i][j];
		}
		
		return sum;
	}
	
	//Method that sums the current column (inputed as a parameter)
	public static int checkVerticalSum(int j)
	{
		int sum = 0;
		
		for (int i = 0; i < cardArray2D.length; i++)
		{
			sum += cardArray2D[i][j];
		}
		return sum;	
	}
}
