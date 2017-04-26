package testBench;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import des.DES;

public class testBench {
	public static void main(String[] args) {
		//Convert input string to binary
		String inputText = "DEADBEEF";
		
		
		ArrayList<cipherLetter> stringList = getLetterFreq(inputText);

		for(int i = 0; i<stringList.size(); i++){
			System.out.println(stringList.get(i).letter + ": " + stringList.get(i).freq + " - " + (double)stringList.get(i).freq/inputText.length() * 100 + "%");
			System.out.println(i);
		}
		
		double IC = getIC(stringList, inputText);
		System.out.println("Index of Coincidence: " + IC);
		
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
		
		//Start timer
		double startTime = System.currentTimeMillis();
		//Encryption
		String outputString = DES.DESLoop(inputText, key, true);
		double firstEndTime = System.currentTimeMillis();
		
		double secondStartTime = System.currentTimeMillis();
		//Decryption
		String secondOutputString = DES.DESLoop(outputString, key, false);
		double endTime = System.currentTimeMillis();
		
		double firstTime = firstEndTime - startTime;
		double secondTime = endTime - secondStartTime;
		double totalTime = endTime - startTime;
		System.out.println("First time: " + firstTime + " ms");
		System.out.println("Second time: " + secondTime + " ms");
		System.out.println("Total time: " + totalTime + " ms");
		
	}
	
	public static ArrayList<cipherLetter> getLetterFreq(String inputString){
		ArrayList<cipherLetter> stringList = new ArrayList<cipherLetter>();
		for(int i=(int)'A'; i<=(int)'Z'; i++){
			cipherLetter tempLetter = new cipherLetter();
			tempLetter.letter = (char)i;
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
	
}
