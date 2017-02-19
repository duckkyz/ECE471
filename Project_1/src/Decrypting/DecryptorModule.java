package Decrypting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DecryptorModule {
		
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to the decryptor module. Which file would you like to decrypt?");
		System.out.println("Enter 1, 2, 3 or 4");
		System.out.println("");
		
		int fileSuffix = DecryptorModule.getFileSuffix();
		String filePath = "ciphertexts/cipher" + fileSuffix + ".txt";
		String inputString = "";
		String line = "";
		try{
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null){
				inputString = inputString + line;
			}
		}
		catch(FileNotFoundException ex){
			System.out.println("Could not find file");
			return;
		}

		ArrayList<cipherLetter> stringList = getLetterFreq(inputString);

		for(int i = 0; i<stringList.size(); i++){
			System.out.println(stringList.get(i).letter + ": " + stringList.get(i).freq + " - " + (double)stringList.get(i).freq/inputString.length() * 100 + "%");
			System.out.println(i);
		}
		
		double IC = getIC(stringList, inputString);
		
		System.out.println("Index of Coincidence: " + IC);
		
		System.out.println("See above for letter frequencies");
		System.out.println("");
		System.out.println("What type of decryption would you like to try?");
		System.out.println("	- 1: Shift");
		System.out.println(" 	- 2: Substitution");
		System.out.println("	- 3: THE ONLY ONE LEFT");
		System.out.println("	- 4: Vigenere");
		
		Scanner user_input = new Scanner(System.in);
		System.out.println("Please enter selection: ");
		int decryptionMethod = Integer.parseInt(user_input.next());
		
		if(decryptionMethod == 1){
			DecryptShift.decryptShiftCipher(stringList, inputString);
		}
		else if(decryptionMethod == 2){
			DecryptSubstitution.decryptSubstitution(stringList, inputString);
		}
		else if(decryptionMethod == 3){
			
		}
		else if(decryptionMethod == 4){
			DecryptVigenere.decryptVigenereCipher(stringList, inputString);
		}
		
		return;
	}
	
	public static char intToChar(int letter){
		char temp = (char) letter;
		
		return temp;
	}
	
	public static int charToInt(char letter){
		int temp = (int) letter;
		
		return temp;
	}
	
	public static ArrayList<cipherLetter> getLetterFreq(String inputString){
		ArrayList<cipherLetter> stringList = new ArrayList<cipherLetter>();
		for(int i=DecryptorModule.charToInt('A'); i<=DecryptorModule.charToInt('Z'); i++){
			cipherLetter tempLetter = new cipherLetter();
			tempLetter.letter = DecryptorModule.intToChar(i);
			int counter = 0;
			for(int j=0; j<inputString.length(); j++){
				if(DecryptorModule.charToInt(inputString.charAt(j)) == i){
					++counter;
				}
			}
			tempLetter.freq = counter;
			stringList.add(tempLetter);
		}
		Collections.sort(stringList, new Comparator<cipherLetter>(){
			public int compare(cipherLetter l1, cipherLetter l2){
				return (int)((l2.freq - l1.freq) * 100);
			}
		});
		return stringList;
	}
	
	public static double getIC(ArrayList<cipherLetter> stringList, String inputString){
		double IC = 0;
		for(int i = 0; i < stringList.size(); i++) {
			IC = IC + (stringList.get(i).freq * (stringList.get(i).freq - 1));
		}
		IC = IC / (.0385*(inputString.length()*(inputString.length()-1)));
		return IC;
	}
	
	public static String shiftMessage(String s, int shiftAmount){
		String returnString = "";
		for(int i=0; i<s.length(); i++){
			int shiftedVal = DecryptorModule.charToInt(s.charAt(i));
			int tempPoopA = DecryptorModule.charToInt('A');
			int tempPoopZ = DecryptorModule.charToInt('Z');
			if((shiftedVal - shiftAmount) < DecryptorModule.charToInt('A')){
				int shiftAmt = (DecryptorModule.charToInt('Z') - DecryptorModule.charToInt('A')) - shiftAmount  + 1;
				shiftedVal = shiftedVal + shiftAmt;
			}
			else{
				shiftedVal = shiftedVal - shiftAmount;
			}
			shiftedVal = ((shiftedVal - DecryptorModule.charToInt('A'))%26) + DecryptorModule.charToInt('A');
			char toPrint = DecryptorModule.intToChar(shiftedVal);
			returnString = returnString + toPrint;
			if(((i%75) == 0) & (i != 0)){
				returnString = returnString + "\n";
			}
		}
		return returnString;
	}
	
	public static int getFileSuffix(){
		int fileSuffix = 0;
		Scanner user_input = new Scanner(System.in);
		System.out.println("Please enter selection: ");
		fileSuffix = Integer.parseInt(user_input.next());
		
		boolean isValid = false;
		if(fileSuffix == 1){
			isValid = true;
		}
		else if(fileSuffix == 2){
			isValid = true;
		}
		else if(fileSuffix == 3){
			isValid = true;
		}
		else if(fileSuffix == 4){
			isValid = true;
		}
		while(!isValid){
			System.out.println("Please enter 1, 2, 3 or 4");
			fileSuffix = Integer.parseInt(user_input.next());
			if(fileSuffix == 1){
				isValid = true;
			}
			else if(fileSuffix == 2){
				isValid = true;
			}
			else if(fileSuffix == 3){
				isValid = true;
			}
			else if(fileSuffix == 4){
				isValid = true;
			}
		}

		return fileSuffix;
	}

	public static ArrayList<cipherLetter> getEnglishLetterFreq(){
		ArrayList<cipherLetter> returnList = new ArrayList<cipherLetter>();
		returnList.add(new cipherLetter('A', 8.167));
		returnList.add(new cipherLetter('B', 1.492));
		returnList.add(new cipherLetter('C', 2.782));
		returnList.add(new cipherLetter('D', 4.253));
		returnList.add(new cipherLetter('E', 12.702));
		returnList.add(new cipherLetter('F', 2.228));
		returnList.add(new cipherLetter('G', 2.015));
		returnList.add(new cipherLetter('H', 6.094));
		returnList.add(new cipherLetter('I', 6.966));
		returnList.add(new cipherLetter('J', 0.153));
		returnList.add(new cipherLetter('K', 0.772));
		returnList.add(new cipherLetter('L', 4.025));
		returnList.add(new cipherLetter('M', 2.406));
		returnList.add(new cipherLetter('N', 6.749));
		returnList.add(new cipherLetter('O', 7.507));
		returnList.add(new cipherLetter('P', 1.929));
		returnList.add(new cipherLetter('Q', 0.095));
		returnList.add(new cipherLetter('R', 5.987));
		returnList.add(new cipherLetter('S', 6.327));
		returnList.add(new cipherLetter('T', 9.056));
		returnList.add(new cipherLetter('U', 2.758));
		returnList.add(new cipherLetter('V', 0.978));
		returnList.add(new cipherLetter('W', 2.360));
		returnList.add(new cipherLetter('X', 0.150));
		returnList.add(new cipherLetter('Y', 1.974));
		returnList.add(new cipherLetter('Z', 0.074));
		return returnList;
	}
}
