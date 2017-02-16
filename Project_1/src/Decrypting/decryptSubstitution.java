package Decrypting;

import java.util.ArrayList;

public class decryptSubstitution {

	public static void decryptSubstitution(ArrayList<cipherLetter> stringList, String inputString){
		System.out.println(stringList.get(0).letter);
		System.out.println(inputString.charAt(0));
		char[] newString = new char[100000];
		
		for(int i = 0; i < inputString.length(); i++){
			newString[i] = subTable(stringList, inputString.charAt(i));
			//System.out.print(subTable(stringList, inputString.charAt(i)));
		}
		System.out.println(newString);
	}
	
	public static char subTable(ArrayList<cipherLetter> stringList, char currLetter){
		char result = 'A';
		char[] letterFreq = {'E', 'T', 'A', 'O', 'I', 'N', 'S', 'H', 'R', 'D', 'L', 'U', 'C', 'M', 'W', 'F', 'Y', 'G', 'P', 'B', 
				'V', 'K', 'X', 'J', 'Q', 'Z'};
		
		for(int x = 0; x < stringList.size(); x++){
			if(currLetter == stringList.get(x).letter){
				result = letterFreq[x];
				break;
			}
			
		}
		
		return result;
	}

}

	