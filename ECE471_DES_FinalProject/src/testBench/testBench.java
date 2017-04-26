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
		
		int fileNum = 1;
		String filePath = "testFiles/test" + fileNum +".txt";
		String inputText = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line = "";
		    while ((line = br.readLine()) != null) {
		    	inputText += line;
		    }
		} catch( IOException ioe ){
		    System.out.println("File not found");
		}
		
		//Get IC for plain text
		ArrayList<cipherLetter> stringList = getLetterFreq(inputText);
		for(int i = 0; i<stringList.size(); i++){
			System.out.println(stringList.get(i).letter + ": " + stringList.get(i).freq + " - " + (double)stringList.get(i).freq/inputText.length() * 100 + "%");
			System.out.println(i);
		}
		
		double IC = getIC(stringList, inputText);
		System.out.println("File " + fileNum + " encryption Index of Coincidence: " + IC);
		
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
		testBench.smallFileTest(inputText, key);		
	}
	
	public static void smallFileTest(String inputText, int[] key){
		for(int i = 0; i < 5; i++){
		//Start enc timer
		double encStartTime = System.currentTimeMillis();
		//Encryption
		String encOutputString= modes.ECB(inputText, key, true);
		double encEndTime = System.currentTimeMillis();
		
		double decStartTime = System.currentTimeMillis();
		//Decryption
		String decOutputString = modes.ECB(encOutputString, key, false);
		double decEndTime = System.currentTimeMillis();
		
		double encTime = encEndTime - encStartTime;
		double decTime = decEndTime - decStartTime;
		double totalTime = decEndTime - encStartTime;
		System.out.println("Encryption time: " + encTime + " ms");
		System.out.println("Decryption time: " + decTime + " ms");
		System.out.println("Total time: " + totalTime + " ms");
		
		modes.ECB(inputText, key);
		modes.CBC(inputText, key);
		modes.CFB(inputText, key);
		modes.OFB(inputText, key);
		modes.CNT(inputText, key);
		}
	
		
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
