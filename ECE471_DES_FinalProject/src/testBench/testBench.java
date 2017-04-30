package testBench;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import des.*;
import modes.*;

public class testBench {
	
	public static double[] ECBEncTime = {0,0,0};
	public static double[] CBCEncTime = {0,0,0};
	public static double[] CFBEncTime = {0,0,0};
	public static double[] OFBEncTime = {0,0,0};
	public static double[] CRTEncTime = {0,0,0};
	public static double[] ECBDecTime = {0,0,0};
	public static double[] CBCDecTime = {0,0,0};
	public static double[] CFBDecTime = {0,0,0};
	public static double[] OFBDecTime = {0,0,0};
	public static double[] CRTDecTime = {0,0,0};
	
	public static double ECBCorruptCount = 0;
	public static double CBCCorruptCount = 0;
	public static double CFBCorruptCount = 0;
	public static double OFBCorruptCount = 0;
	public static double CRTCorruptCount = 0;
	public static double IV_ECBCorruptCount = 0;
	public static double IV_CBCCorruptCount = 0;
	public static double IV_CFBCorruptCount = 0;
	public static double IV_OFBCorruptCount = 0;
	public static double IV_CRTCorruptCount = 0;
	
	public static double ECBScrambleCount = 0;
	public static double CBCScrambleCount = 0;
	public static double CFBScrambleCount = 0;
	public static double OFBScrambleCount = 0;
	public static double CRTScrambleCount = 0;

	public static void main(String[] args) {
		
		boolean speedTest = true;
		boolean corruptionTest = true;
		boolean scrambleTest = true;

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
		
		if(speedTest == true){
			for(int fileNum = 1; fileNum < 31; fileNum++){
				String filePath = "testFiles/test_" + fileNum +".txt";
				if(fileNum < 10){
					filePath = "testFiles/test_0" + (fileNum) + ".txt";
				}
				
				String inputText = getFile(filePath);
				int size = 0;
				if(fileNum < 11){
					System.out.println("Speed/IC test run: " + (fileNum - 0));
					System.out.print("	[1.1/2.1] Small ");
				}
				else if(fileNum < 21){
					System.out.println("Speed/IC test run: " + (fileNum - 10));
					System.out.print("	[1.2/2.2] Medium ");
					size = 1;
				}
				else if(fileNum < 31){
					System.out.println("Speed/IC test run: " + (fileNum - 20));
					System.out.print("	[1.3/2.3] Large ");
					size = 2;
				}
				System.out.println("file test:");
				testBench.time_IC_Test(inputText, key, size);
			}
		}
			 
		for(int fileNum = 31; fileNum < 41; fileNum++){
			String filePath = "testFiles/test_" + fileNum +".txt";
			String inputText = getFile(filePath);
			for(int j = 0; j < 3; j++){
				if(j == 0){
					System.out.println("Repeating chunks test run: " + (fileNum - 30));
					if(corruptionTest == true){
						System.out.println("	[3.1.1] Block Corruption file test:");
						testBench.block_corruption_Test(inputText, key);
					}
				}
				else if(j == 1){
					if(corruptionTest == true){
						System.out.println("	[3.2.1] IV Corruption file test:");
						testBench.IV_corruption_Test(inputText, key);
					}
				}
				else if(j == 2){
					if(scrambleTest == true){
						System.out.println("	[4.1.1] Scramble file test:");
						testBench.scramble_Test(inputText, key);
					}
				}				
			}
		}
		 
		for(int fileNum = 11; fileNum < 21; fileNum++){
			String filePath = "testFiles/test_" + fileNum +".txt";
			String inputText = getFile(filePath);
			for(int j = 0; j < 3; j++){
				if(j == 0){
					System.out.println("Fully Random test run: " + (fileNum - 10));
					if(corruptionTest == true){
						System.out.println("	[3.1.2] Block Corruption file test:");
						testBench.block_corruption_Test(inputText, key);
					}
				}
				else if(j == 1){
					if(corruptionTest == true){
						System.out.println("	[3.2.2] IV Corruption file test:");
						testBench.IV_corruption_Test(inputText, key);
					}
				}
				else if(j == 2){
					if(scrambleTest == true){
						System.out.println("	[4.1.2] Scramble file test:");
						testBench.scramble_Test(inputText, key);
					}
				}
			}
		}	
		for(int i = 0; i < 3; i++){
			if(i == 0){
				System.out.println("Small file: ");
			}
			else if(i == 1){
				System.out.println("Med file: ");
			}
			else{
				System.out.println("Large file: ");
			}
			System.out.println("	ECB avg enc time: " + testBench.ECBEncTime[i]/10 + " ms");
			System.out.println("	ECB avg dec time: " + testBench.ECBDecTime[i]/10 + " ms");
			System.out.println("	CBC avg enc time: " + testBench.CBCEncTime[i]/10 + " ms");
			System.out.println("	CBC avg dec time: " + testBench.CBCDecTime[i]/10 + " ms");
			System.out.println("	CFB avg enc time: " + testBench.CFBEncTime[i]/10 + " ms");
			System.out.println("	CFB avg dec time: " + testBench.CFBDecTime[i]/10 + " ms");
			System.out.println("	OFB avg enc time: " + testBench.OFBEncTime[i]/10 + " ms");
			System.out.println("	OFB avg dec time: " + testBench.OFBDecTime[i]/10 + " ms");
			System.out.println("	CRT avg enc time: " + testBench.CRTEncTime[i]/10 + " ms");
			System.out.println("	CRT avg dec time: " + testBench.CRTDecTime[i]/10 + " ms");
		}
		for(int i = 0; i < 3; i++){
			if(i == 0){
				System.out.println("Block corruption: ");
				System.out.println("	ECB avg corruption: " + testBench.ECBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CBC avg corruption: " + testBench.CBCCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CFB avg corruption: " + testBench.CFBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	OFB avg corruption: " + testBench.OFBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CRT avg corruption: " + testBench.CRTCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
			}
			else if(i == 1){
				System.out.println("IV corruption: ");
				System.out.println("	ECB avg corruption: " + testBench.IV_ECBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CBC avg corruption: " + testBench.IV_CBCCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CFB avg corruption: " + testBench.IV_CFBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	OFB avg corruption: " + testBench.IV_OFBCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CRT avg corruption: " + testBench.IV_CRTCorruptCount/10 + " total corrupted bytes per initial corrupted bytes");
			}
			else{
				System.out.println("Scramble corruption: ");
				System.out.println("	ECB avg corruption: " + testBench.ECBScrambleCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CBC avg corruption: " + testBench.CBCScrambleCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CFB avg corruption: " + testBench.CFBScrambleCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	OFB avg corruption: " + testBench.OFBScrambleCount/10 + " total corrupted bytes per initial corrupted bytes");
				System.out.println("	CRT avg corruption: " + testBench.CRTScrambleCount/10 + " total corrupted bytes per initial corrupted bytes");
			}
		}
	}
	
	public static void time_IC_Test(String inputText, int[] key, int size){
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
			testBench.time_IC_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i, size);
		}
	}
	
	public static void time_IC_testFooter(long encTime, long decTime, long totalTime, String inputText, 
			String encOutputString, String decOutputString, int testNum, int size){
		double divider = 1000000;
		if(testNum == 0){
			System.out.println("		ECB:");
			testBench.ECBEncTime[size] += (double)encTime/divider;
			testBench.ECBDecTime[size] += (double)decTime/divider;
		}
		else if(testNum == 1){
			System.out.println("		CBC:");
			testBench.CBCEncTime[size] += (double)encTime/divider;
			testBench.CBCDecTime[size] += (double)decTime/divider;
		}
		else if(testNum == 2){
			System.out.println("		CFB:");
			testBench.CFBEncTime[size] += (double)encTime/divider;
			testBench.CFBDecTime[size] += (double)decTime/divider;
		}
		else if(testNum == 3){
			System.out.println("		OFB:");
			testBench.OFBEncTime[size] += (double)encTime/divider;
			testBench.OFBDecTime[size] += (double)decTime/divider;
		}
		else if(testNum == 4){
			System.out.println("		CRT:");
			testBench.CRTEncTime[size] += (double)encTime/divider;
			testBench.CRTDecTime[size] += (double)decTime/divider;
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
			//System.out.println("			ERROR: DecIC != OrigIC, Decoding may not be working");
			//System.out.println(inputText);
			//System.out.println(decOutputString);
		}
	}
	
	public static void block_corruption_Test(String inputText, int[] key){
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
			// Randomly corrupt the first block and observe how it propgates
			int[] tempBinVals = DES.stringToBin(encOutputString);
			int corruption_count = 0;
			for(int j = 0; j < 64; j++){
				//Here there is a 80% chance for corruption
				if(Math.random()*100 > 80){
					corruption_count++;
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
			testBench.corruption_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i, corruption_count, 0);
		}
	}
	
	public static void IV_corruption_Test(String inputText, int[] key){
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
			// Randomly corrupt the IV and see what happens!
			int corruption_count = 0;
			for(int j = 0; j < 64; j++){
				//Here there is a 80% chance for corruption
				if(Math.random()*100 > 80){
					corruption_count++;
					if(IV[j] == 1){
						IV[j] = 0;
					}
					else{
						IV[j] = 1;
					}
				}
			}
			
			
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
			testBench.corruption_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i, corruption_count, 1);
		}
	}

	public static void corruption_testFooter(long encTime, long decTime, long totalTime, String inputText, 
			String encOutputString, String decOutputString, int testNum, int corruption_count, int testType){
		double divider = 1000000;
		int extended_corruption = 0;
		for(int i = 0; i <inputText.length(); i++){
			if(inputText.charAt(i) != decOutputString.charAt(i)){
				++extended_corruption;
			}
		}
		
		if(testNum == 0){
			System.out.println("		ECB:");
			if(testType == 0){
				testBench.ECBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 1){
				testBench.IV_ECBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 2){
				testBench.ECBScrambleCount += ((double)extended_corruption/(double)corruption_count);
			}
		}
		else if(testNum == 1){
			System.out.println("		CBC:");
			if(testType == 0){
				testBench.CBCCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 1){
				testBench.IV_CBCCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 2){
				testBench.CBCScrambleCount += ((double)extended_corruption/(double)corruption_count);
			}
		}
		else if(testNum == 2){
			System.out.println("		CFB:");
			if(testType == 0){
				testBench.CFBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 1){
				testBench.IV_CFBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 2){
				testBench.CFBScrambleCount += ((double)extended_corruption/(double)corruption_count);
			}
		}
		else if(testNum == 3){
			System.out.println("		OFB:");
			if(testType == 0){
				testBench.OFBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 1){
				testBench.IV_OFBCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 2){
				testBench.OFBScrambleCount += ((double)extended_corruption/(double)corruption_count);
			}
		}
		else if(testNum == 4){
			System.out.println("		CRT:");
			if(testType == 0){
				testBench.CRTCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 1){
				testBench.IV_CRTCorruptCount += ((double)extended_corruption/(double)corruption_count);
			}
			else if(testType == 2){
				testBench.CRTScrambleCount += ((double)extended_corruption/(double)corruption_count);
			}
		}		
		
		System.out.println("			Encryption Index of Coincidence	: " + getIC(getLetterFreq(encOutputString), encOutputString) + 
				"	|	Encryption time	: " + (double)encTime/divider + " ms");
		double decIC = getIC(getLetterFreq(decOutputString), decOutputString);
		System.out.println("			Decryption Index of Coincidence	: " + decIC + 
				"	|	Decryption time	: " + (double)decTime/divider + " ms");
		double origIC = getIC(getLetterFreq(inputText), inputText);
		System.out.println("			Original Index of Coincidence	: " + origIC + 
				"	|	Total time	: " + (double)totalTime/divider + " ms");
		System.out.println("			Corruption propogation : " + ((double)extended_corruption/(double)corruption_count) + 
				" corrupted chars per initial corruptions");
		if (Double.isNaN(decIC)){
			//System.out.println("			ERROR: DecIC == NAN, Decoding is not working");
		}
		else if(decIC != origIC){
			//System.out.println("			ERROR: DecIC != OrigIC, Decoding may not be working");
			//System.out.println(inputText);
			//System.out.println(decOutputString);
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
						
			//Scramble the blocks
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
			testBench.corruption_testFooter(encTime, decTime, totalTime, inputText, encOutputString, decOutputString, i, origSize, 2);
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
