package testBench;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import des.*;
import modes.*;

public class testBench {
	public static void main(String[] args) {
		//Create random key
		int[] key = new int[64];
		for(int i = 0; i < 64; i++){
			if((Math.random() * 100) > 50){
				key[i] = 0;
			}
			else{
				key[i] = 1;
			}
		}
		
		/* Test 1.1 - 2.3:
		 * fileNum == 1
		 * 	Small file test:
		 * 		Tests 1.1 and 2.1
		 * fileNum == 2
		 * 	Medium file test:
		 * 		Tests 1.2 and 2.2
		 * fileNum == 3
		 * 	Large file test:
		 * 		Tests 1.3 and 2.3
		 */	
		 for(int fileNum = 1; fileNum < 4; fileNum++){
			String filePath = "testFiles/test" + fileNum +".txt";
			String inputText = getFile(filePath);
			testBench.time_IC_Test(inputText, key);
		}
		
	}
	
	public static void time_IC_Test(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
			//Start enc timer
			double encStartTime = System.currentTimeMillis();
			//Encryption
			String encOutputString = "";
			if(i == 0){
				encOutputString = modes.ECB(inputText, key, true);
			}
			else if(i == 1){
				encOutputString = modes.CBC(inputText, key, true);
			}
			else if(i == 2){
				encOutputString = modes.CFB(inputText, key, true);
			}
			else if(i == 3){
				encOutputString = modes.OFB(inputText, key, true);
			}
			else if(i == 4){
				encOutputString = modes.CNT(inputText, key, true);
			}
			double encEndTime = System.currentTimeMillis();
			
			double decStartTime = System.currentTimeMillis();
			//Decryption
			String decOutputString = "";
			if(i == 0){
				decOutputString = modes.ECB(encOutputString, key, false);
			}
			else if(i == 1){
				decOutputString = modes.CBC(encOutputString, key, false);
			}
			else if(i == 2){
				decOutputString = modes.CFB(encOutputString, key, false);
			}
			else if(i == 3){
				decOutputString = modes.OFB(encOutputString, key, false);
			}
			else if(i == 4){
				decOutputString = modes.CNT(encOutputString, key, false);
			}
			double decEndTime = System.currentTimeMillis();
			
			double encTime = encEndTime - encStartTime;
			double decTime = decEndTime - decStartTime;
			double totalTime = decEndTime - encStartTime;
			testBench.time_IC_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i);
		}
	}
	
	public static void time_IC_testFooter(double encTime, double decTime, double totalTime, String inputText, 
			String encOutputString, String decOutputString, int testNum){
		if(testNum == 0){
			System.out.println("ECB:");
		}
		else if(testNum == 1){
			System.out.println("CBC:");
		}
		else if(testNum == 2){
			System.out.println("CFB:");
		}
		else if(testNum == 3){
			System.out.println("OFB:");
		}
		else if(testNum == 4){
			System.out.println("CNT:");
		}
		System.out.println("Original Index of Coincidence: " + getIC(getLetterFreq(inputText), inputText));
		System.out.println("Encryption time: " + encTime + " ms");
		System.out.println("Encryption Index of Coincidence: " + getIC(getLetterFreq(encOutputString), encOutputString));
		System.out.println("Decryption time: " + decTime + " ms");
		System.out.println("Decryption Index of Coincidence: " + getIC(getLetterFreq(decOutputString), decOutputString));
		System.out.println("Total time: " + totalTime + " ms");
	}
	
	public static void corruption_Test(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
			//Start enc timer
			double encStartTime = System.currentTimeMillis();
			//Encryption
			String encOutputString = "";
			if(i == 0){
				encOutputString = modes.ECB(inputText, key, true);
			}
			else if(i == 1){
				encOutputString = modes.CBC(inputText, key, true);
			}
			else if(i == 2){
				encOutputString = modes.CFB(inputText, key, true);
			}
			else if(i == 3){
				encOutputString = modes.OFB(inputText, key, true);
			}
			else if(i == 4){
				encOutputString = modes.CNT(inputText, key, true);
			}
			double encEndTime = System.currentTimeMillis();
			int[] tempBinVals = DES.stringToBin(encOutputString);
			for(int j = 0; j < tempBinVals.length; j++){
				//Here there is a 10% chance for corruption
				if(Math.random()*100 > 90){
					if(tempBinVals[j] == 1){
						tempBinVals[j] = 0;
					}
					else{
						tempBinVals[j] = 1;
					}
				}
			}
			encOutputString = DES.binToString(tempBinVals);
			
			double decStartTime = System.currentTimeMillis();
			//Decryption
			String decOutputString = "";
			if(i == 0){
				decOutputString = modes.ECB(encOutputString, key, false);
			}
			else if(i == 1){
				decOutputString = modes.CBC(encOutputString, key, false);
			}
			else if(i == 2){
				decOutputString = modes.CFB(encOutputString, key, false);
			}
			else if(i == 3){
				decOutputString = modes.OFB(encOutputString, key, false);
			}
			else if(i == 4){
				decOutputString = modes.CNT(encOutputString, key, false);
			}
			double decEndTime = System.currentTimeMillis();
			
			double encTime = encEndTime - encStartTime;
			double decTime = decEndTime - decStartTime;
			double totalTime = decEndTime - encStartTime;
			testBench.time_IC_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i);
		}
	}

	
	public static String getFile(String filePath){
		String inputText = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line = "";
		    while ((line = br.readLine()) != null) {
		    	inputText += line;
		    }
		} catch( IOException ioe ){
		    System.out.println("File not found");
		}
		return inputText;
	}
	
	public static ArrayList<cipherLetter> getLetterFreq(String inputString){
		String temp = inputString.toUpperCase();
		ArrayList<cipherLetter> stringList = new ArrayList<cipherLetter>();
		for(int i=(int)'A'; i<=(int)'Z'; i++){
			cipherLetter tempLetter = new cipherLetter();
			tempLetter.letter = (char)i;
			int counter = 0;
			for(int j=0; j<temp.length(); j++){
				if(((int)(temp.charAt(j))) == i){
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
	
}
