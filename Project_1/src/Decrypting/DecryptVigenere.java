package Decrypting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DecryptVigenere {
	public static void decryptVigenereCipher(ArrayList<cipherLetter> stringList, String inputString){
		kasiskiTest(inputString);
	}
	
	
	public static int kasiskiTest(String inputString){
		int keyLength = 0; //should output 9 should be worcester
		int minDist = inputString.length();
		for(keyLength = 3; keyLength < 11; keyLength++){
			System.out.println("\n\n\n% % % % NEW ITERATION % % % %\n");
			minDist = inputString.length();
			ArrayList<cipherString> stringList = new ArrayList<cipherString>();
			for(int i=0; i<inputString.length(); i = i + keyLength + 1){
				//System.out.println("stringPosition = " + i + " endOfString = " + (i+keyLength-1));
				boolean found = false;
				cipherString tempString = new cipherString();
				if((i + keyLength) < inputString.length()){
					tempString.string = inputString.substring(i, i + keyLength);
				}
				else{
					break;
				}
				int counter = 1;
				
				for(cipherString cs : stringList){
					if(cs.string.equals(tempString.string)){
						cs.locations.add(i);
						++cs.freq;
						found = true;
					}
				}
				if(found == false){
					tempString.freq = counter;
					tempString.locations.add(i);
					stringList.add(tempString);
				}
			}
			Collections.sort(stringList, new Comparator<cipherString>(){
				public int compare(cipherString l1, cipherString l2){
					return l2.freq - l1.freq;
				}
			});
			int stringCount = 0;
			for(cipherString cs : stringList){
				if(cs.freq > 1){
					//System.out.println("String: " + cs.string + " freq: " + cs.freq);
					System.out.println("String: " + cs.string);
				}
				for(int loc = 1; loc < cs.locations.size(); loc++){		
					int startingLoc = cs.locations.get(loc - 1);
					if((cs.locations.get(loc) - startingLoc) != 0){
						int tempDist = cs.locations.get(loc) - startingLoc;
						
						if(tempDist < minDist){
							minDist = tempDist;
							System.out.println("	produced new min dist = " + minDist);
						}
					}
				}
				stringCount = stringCount + cs.freq;
			}
			//System.out.println(inputString.length() + " / "+ keyLength + " = " + (float)inputString.length()/keyLength);
			System.out.println("minDist for keyLength: " + keyLength + " is: " + minDist);
		}
	
		test(inputString);
		return keyLength;
	}

	public static void test(String inputString){
		String mostCommon = "YOCNQWBWY";
		String key = "WORCESTER";
		String plaintext = "CALLMEISHMAELSOMEYEARSAGONEVERMINDHOWLONGPRECISELYHAVINGLITTLEORNOMONEYINMYPURSEANDNOTHINGPARTICULARTOINTERESTMEONSHOREITHOUGHTIWOULDSAILABOUTALITTLEANDSEETHEWATERYPARTOFTHEWORLDITISAWAYIHAVEOFDRIVINGOFFTHESPLEENANDREGULATINGTHECIRCULATIONWHENEVERIFINDMYSELFGROWINGGRIMABOUTTHEMOUTHWHENEVERITISADAMPDRIZZLY";
		
		for(int i=0; i<plaintext.length(); i++){
			String temp = inputString.substring(i, i+1);
			int shiftAmount = DecryptorModule.charToInt(key.charAt(i%key.length())) - DecryptorModule.charToInt('A');
			//System.out.println("Sending in : String " + temp + " Shift " + shiftAmount);
			String tempOut = DecryptorModule.shiftMessage(temp, shiftAmount);
			if(DecryptorModule.charToInt(tempOut.charAt(0)) != DecryptorModule.charToInt(plaintext.charAt(i))){
				System.out.println(inputString.charAt(i) + " shifted by " + key.charAt(i%key.length()) + " = " + tempOut + ", should equal " + plaintext.charAt(i) + "  keyIndex = " + i%key.length());
			}
		}
		
		/*
		String output = "";
		for(int i=0; i<inputString.length(); i++){
			String temp = inputString.substring(i, i+1);
			int shiftAmount = DecryptorModule.charToInt(key.charAt(i%key.length())) - DecryptorModule.charToInt('A');
			//System.out.println("Sending in : String " + temp + " Shift " + shiftAmount);
			output = output + DecryptorModule.shiftMessage(temp, shiftAmount);
			if(i!=0){
				if((i+1)%key.length() == 0){
					output = output + "\n";
				}
			}
		}
		System.out.println(output);
		*/
		
	}
	
	
}
