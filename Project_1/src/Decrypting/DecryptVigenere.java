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
		ArrayList<cipherDistance> distanceList = new ArrayList<cipherDistance>();
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
						cipherDistance tempCD = new cipherDistance();
						tempCD.distance = cs.locations.get(loc) - startingLoc;
						boolean isFound = false;
						for(cipherDistance cD : distanceList){
							if(cD.distance == tempCD.distance){
								cD.freq += 1;
								isFound = true;
								break;
							}
						}
						if(isFound != true){
							tempCD.freq = 1;
							distanceList.add(tempCD);
						}
					}
				}
				stringCount = stringCount + cs.freq;
				//if(cs.freq > 1)
				//	cs.getDistances();
			}
			//System.out.println(inputString.length() + " / "+ keyLength + " = " + (float)inputString.length()/keyLength);
			System.out.println("minDist for keyLength: " + keyLength + " is: " + minDist);
			
		}
		Collections.sort(distanceList, new Comparator<cipherDistance>(){
			public int compare(cipherDistance l1, cipherDistance l2){
				return l2.freq - l1.freq;
			}
		});
		test(inputString);
		return keyLength;
	}

	public static void test(String inputString){		
		
		String mostCommon = "YOCNQWBWY";
		String key = "WORCESTER";
		
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
		
	}
	
	
}
