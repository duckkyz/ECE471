package Decrypting;

import java.util.ArrayList;
import java.util.Scanner;

public class DecryptSubstitution {

	public static void decryptSubstitution(ArrayList<cipherLetter> stringList, String inputString){
		char[] letterFreq = {'E', 'T', 'A', 'O', 'I', 'N', 'S', 'H', 'R', 'D', 'L', 'U', 'C', 'M', 'W', 'F', 'Y', 'G', 'P', 'B', 
				'V', 'K', 'X', 'J', 'Q', 'Z'};
		char[] currKey = new char[26];										//holds the current key
		char[] plaintext = new char[100000];								//holds the current plaintext
		
		for(int i = 0; i < stringList.size(); i++){							//initialize current key array
			currKey[i] = stringList.get(i).letter;
		}
		
		for(int i = 0; i < inputString.length(); i++){						//substitutes assuming perfect conditions
			plaintext[i] = subTable(stringList, inputString.charAt(i), letterFreq);		
		}
		
		while(true){
			
			for(int i = 0; (i < plaintext.length) & (i < 400); i++){
				System.out.print(plaintext[i]);
				if((i%100 == 0) & (i != 0))
					System.out.println("");
			}
			
			Scanner user_input = new Scanner(System.in);
			System.out.println("\nPress 1 to see current key, 2 to switch characters, 3 to see found solution (given plaintext only), or 4 to exit");
			int option = Integer.parseInt(user_input.next());
			
			if(option == 1){
				for(int i = 0; i < letterFreq.length; i++){
					System.out.println("Plain/Cypth: " + currKey[i] +"/" + letterFreq[i]);
				}
			}
			else if(option == 2){
				System.out.println("Enter the character you want to swap: ");
				Scanner reader = new Scanner(System.in);
				char char1 = reader.next().charAt(0);
				System.out.println("Enter the character you want to swap with: ");
				char char2 = reader.next().charAt(0);
				
				
				for(int x = 0; x < currKey.length; x++){						//changes the key
					if(currKey[x] == char1){
						currKey[x] = char2;
					}
					else if(currKey[x] == char2)
						currKey[x] = char1;
				}
				
				for(int x = 0; x < plaintext.length; x++){						//changes the plaintext
					if(plaintext[x] == char1)
						plaintext[x] = char2;
					else if(plaintext[x] == char2)
						plaintext[x] = char1;
				}
			}
			else if(option == 3){
				currKey = new char[] {'T', 'G', 'Z', 'Q', 'R', 'F', 'D', 'P', 'U', 'Y', 'W', 'H', 'A', 'E', 'K', 'X', 'O', 'L', 'C', 'M', 
						'J', 'B', 'N', 'S', 'I', 'V'};
				for(int i = 0; i < inputString.length(); i++){		
					
					for(int x=0; x < currKey.length; x++){
						if(inputString.charAt(i) == currKey[x]){
							plaintext[i] = letterFreq[x];
							break;
						}
					}
					
				}	
			}
			else
				break;
			
			
		}
	}
	
	public static char subTable(ArrayList<cipherLetter> stringList, char currLetter, char[] letterFreq){
		char result = 'A';
		
		for(int x = 0; x < stringList.size(); x++){
			if(currLetter == stringList.get(x).letter){
				result = letterFreq[x];
				break;
			}
			
		}
		
		return result;
	}
	


}

	