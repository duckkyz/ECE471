package testBench;
import java.awt.List;
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
		 for(int fileNum = 1; fileNum < 31; fileNum++){
			String filePath = "testFiles/test_" + fileNum +".txt";
			if(fileNum < 10){
				filePath = "testFiles/test_0" + (fileNum) + ".txt";
			}
			
			String inputText = getFile(filePath);

			if(fileNum < 11){
				System.out.println("Speed/IC test run: " + (fileNum - 0));
				System.out.print("	[1.1/2.1] Small ");
			}
			else if(fileNum < 21){
				System.out.println("Speed/IC test run: " + (fileNum - 10));
				System.out.print("	[1.2/2.2] Medium ");
			}
			else if(fileNum < 31){
				System.out.println("Speed/IC test run: " + (fileNum - 20));
				System.out.print("	[1.3/2.3] Large ");
			}
			System.out.println("file test:");
			testBench.time_IC_Test(inputText, key);
		 }
		 
		 for(int fileNum = 31; fileNum < 41; fileNum++){
				String filePath = "testFiles/test_" + fileNum +".txt";
				String inputText = getFile(filePath);
				for(int j = 0; j < 3; j++){
					if(j == 0){
						System.out.println("Repeating chunks test run: " + (fileNum - 30));
						System.out.println("	[3.1] Corruption file test:");
						testBench.corruption_Test(inputText, key);
					}
					else if(j == 1){
						System.out.println("	[4.1.1] Scramble file test:");
						testBench.scramble_Test(inputText, key);
					}
					else if(j == 2){
						System.out.println("	[4.2.1] Targeted Swap file test:");
					}
					
				}
		 }
		 
		 for(int fileNum = 11; fileNum < 21; fileNum++){
				String filePath = "testFiles/test_" + fileNum +".txt";
				String inputText = getFile(filePath);
				for(int j = 0; j < 3; j++){
					if(j == 0){
						System.out.println("Fully Random test run: " + (fileNum - 10));
						System.out.println("	[3.2] Corruption file test:");
						testBench.corruption_Test(inputText, key);
					}
					else if(j == 1){
						System.out.println("	[4.1.2] Scramble file test:");
						testBench.scramble_Test(inputText, key);
					}
					else if(j == 2){
						System.out.println("	[4.2.2] Targeted Swap file test:");
					}
					
				}
		 }
		
	}
	
	public static void time_IC_Test(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
			int[] IV = modes.createIV();
			//Start enc timer
			long encStartTime = System.nanoTime();
			//Encryption
			String encOutputString = "";
			if(i == 0){
				encOutputString = modes.ECB(inputText, key, true);
			}
			else if(i == 1){
				encOutputString = modes.CBC(inputText, key, IV, true);
			}
			else if(i == 2){
				encOutputString = modes.CFB(inputText, key, IV, true);
			}
			else if(i == 3){
				encOutputString = modes.OFB(inputText, key, IV, true);
			}
			else if(i == 4){
				encOutputString = modes.CRT(inputText, key, IV, true);
			}
			long encEndTime = System.nanoTime();
			
			long decStartTime = System.nanoTime();
			//Decryption
			String decOutputString = "";
			if(i == 0){
				decOutputString = modes.ECB(encOutputString, key, false);
			}
			else if(i == 1){
				decOutputString = modes.CBC(encOutputString, key, IV, false);
			}
			else if(i == 2){
				decOutputString = modes.CFB(encOutputString, key, IV, false);
			}
			else if(i == 3){
				decOutputString = modes.OFB(encOutputString, key, IV, false);
			}
			else if(i == 4){
				decOutputString = modes.CRT(encOutputString, key, IV, false);
			}
			long decEndTime = System.nanoTime();
			
			long encTime = encEndTime - encStartTime;
			long decTime = decEndTime - decStartTime;
			long totalTime = decEndTime - encStartTime;
			testBench.time_IC_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i);
		}
	}
	
	public static void time_IC_testFooter(long encTime, long decTime, long totalTime, String inputText, 
			String encOutputString, String decOutputString, int testNum){
		double divider = 1000000;
		if(testNum == 0){
			System.out.println("		ECB:");
		}
		else if(testNum == 1){
			System.out.println("		CBC:");
		}
		else if(testNum == 2){
			System.out.println("		CFB:");
		}
		else if(testNum == 3){
			System.out.println("		OFB:");
		}
		else if(testNum == 4){
			System.out.println("		CRT:");
		}
		System.out.println("			Encryption Index of Coincidence	: " + getIC(getLetterFreq(encOutputString), encOutputString) + 
				"	|	Encryption time	: " + (double)encTime/divider + " ms");
		double decIC = getIC(getLetterFreq(decOutputString), decOutputString);
		System.out.println("			Decryption Index of Coincidence	: " + decIC + 
				"	|	Decryption time	: " + (double)decTime/divider + " ms");
		double origIC = getIC(getLetterFreq(inputText), inputText);
		System.out.println("			Original Index of Coincidence	: " + origIC + 
				"	|	Total time	: " + (double)totalTime/divider + " ms");
		if (Double.isNaN(decIC)){
			//System.out.println("			ERROR: DecIC == NAN, Decoding is not working");
		}
		else if(decIC != origIC){
			System.out.println("			ERROR: DecIC != OrigIC, Decoding may not be working");
			//System.out.println(inputText);
			//System.out.println(decOutputString);
		}
	}
	
	public static void corruption_Test(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
			int[] IV = modes.createIV();
			//Start enc timer
			long encStartTime = System.nanoTime();
			//Encryption
			String encOutputString = "";
			if(i == 0){
				encOutputString = modes.ECB(inputText, key, true);
			}
			else if(i == 1){
				encOutputString = modes.CBC(inputText, key, IV, true);
			}
			else if(i == 2){
				encOutputString = modes.CFB(inputText, key, IV, true);
			}
			else if(i == 3){
				encOutputString = modes.OFB(inputText, key, IV, true);
			}
			else if(i == 4){
				encOutputString = modes.CRT(inputText, key, IV, true);
			}
			long encEndTime = System.nanoTime();
						
			//Corruption injection
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
			
			
			long decStartTime = System.nanoTime();
			//Decryption
			String decOutputString = "";
			if(i == 0){
				decOutputString = modes.ECB(encOutputString, key, false);
			}
			else if(i == 1){
				decOutputString = modes.CBC(encOutputString, key, IV, false);
			}
			else if(i == 2){
				decOutputString = modes.CFB(encOutputString, key, IV, false);
			}
			else if(i == 3){
				decOutputString = modes.OFB(encOutputString, key, IV, false);
			}
			else if(i == 4){
				decOutputString = modes.CRT(encOutputString, key, IV, false);
			}
			long decEndTime = System.nanoTime();
			
			long encTime = encEndTime - encStartTime;
			long decTime = decEndTime - decStartTime;
			long totalTime = decEndTime - encStartTime;
			testBench.time_IC_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i);
		}
	}

	public static void scramble_Test(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
			int[] IV = modes.createIV();
			//Start enc timer
			long encStartTime = System.nanoTime();
			//Encryption
			String encOutputString = "";
			if(i == 0){
				encOutputString = modes.ECB(inputText, key, true);
			}
			else if(i == 1){
				encOutputString = modes.CBC(inputText, key, IV, true);
			}
			else if(i == 2){
				encOutputString = modes.CFB(inputText, key, IV, true);
			}
			else if(i == 3){
				encOutputString = modes.OFB(inputText, key, IV, true);
			}
			else if(i == 4){
				encOutputString = modes.CRT(inputText, key, IV, true);
			}
			long encEndTime = System.nanoTime();
						
			//Scramble em
			String temp = encOutputString;
			ArrayList<String> stringList = new ArrayList<String>();
			for(int j = 0; j < temp.length()/8; j++){
				stringList.add(temp.substring(j, j+8));				
			}
			int origSize = stringList.size();
			temp = "";
			for(int j = 0; j < origSize; j++){
				if(stringList.isEmpty() == false){
					int idx = (int)(Math.random() * stringList.size());
					temp = temp + stringList.get(idx);
					stringList.remove(idx);
				}
			}
			encOutputString = temp;
			
			long decStartTime = System.nanoTime();
			//Decryption
			String decOutputString = "";
			if(i == 0){
				decOutputString = modes.ECB(encOutputString, key, false);
			}
			else if(i == 1){
				decOutputString = modes.CBC(encOutputString, key, IV, false);
			}
			else if(i == 2){
				decOutputString = modes.CFB(encOutputString, key, IV, false);
			}
			else if(i == 3){
				decOutputString = modes.OFB(encOutputString, key, IV, false);
			}
			else if(i == 4){
				decOutputString = modes.CRT(encOutputString, key, IV, false);
			}			
			long decEndTime = System.nanoTime();
			
			long encTime = encEndTime - encStartTime;
			long decTime = decEndTime - decStartTime;
			long totalTime = decEndTime - encStartTime;
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
