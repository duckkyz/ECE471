package Decrypting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DecryptVigenere {
	public static void decryptVigenereCipher(ArrayList<cipherLetter> stringList, String inputString){
		int keyLength = kasiskiTest(inputString);
		String key = getKey(keyLength, inputString);
		System.out.println("Key chosen is: " + key + "\n");
		
		String output = "";
		for(int i=0; i<inputString.length(); i++){
			String temp = inputString.substring(i, i+1);
			int shiftAmount = DecryptorModule.charToInt(key.charAt(i%key.length())) - DecryptorModule.charToInt('A');
			//System.out.println("Sending in : String " + temp + " Shift " + shiftAmount);
			output = output + DecryptorModule.shiftMessage(temp, shiftAmount);
			if(i!=0){
				//if((i+1)%key.length() == 0){
				if((i+1)%50 == 0){
					output = output + "\n";
				}
			}
		}
		System.out.println(output);
	}
		
	public static int kasiskiTest(String inputString){
		int keyPeriod = 0; //should output 9 should be worcester
		ArrayList<cipherDistance> distanceList = new ArrayList<cipherDistance>();
		for(keyPeriod = 3; keyPeriod < 11; keyPeriod++){
			cipherDistance temp = new cipherDistance();
			temp.shiftDist = keyPeriod;
			String[] stringList = new String[keyPeriod];
			for(int i = 0; i<keyPeriod; i++){
				stringList[i] = "";
			}
			for(int i = 0; i<(inputString.length()/keyPeriod); i++){
				String tempString = stringList[i%keyPeriod];
				tempString += inputString.substring(i, i+1);
				stringList[i%keyPeriod] = tempString;
			}
			
			double finalIC = 0;
			for(int i = 0; i<keyPeriod; i++){
				finalIC += DecryptorModule.getIC(DecryptorModule.getLetterFreq(stringList[i]), stringList[i]);
			}	
			temp.IC = finalIC/keyPeriod;
			distanceList.add(temp);
		}
		Collections.sort(distanceList, new Comparator<cipherDistance>(){
			public int compare(cipherDistance l1, cipherDistance l2){
				return (int)((l2.IC - l1.IC) * 100);
			}
		});
		for(cipherDistance cD : distanceList){
			System.out.println("For distance " + cD.shiftDist + " IC = " + cD.IC);
		}
		
		keyPeriod = distanceList.get(0).shiftDist;
		System.out.println("\n" + keyPeriod + " will be chosen since it is the highest IC val\n\n");

		return keyPeriod;
	}
	
	public static String getKey(int keyLength, String inputString){
		String solutionKey = "WORCESTER";
		
		String keyString = "";
		for(int i=0; i<keyLength; i++){
			keyString += "A";
		}
		
		String[] stringList = new String[keyLength];
		for(int i = 0; i<keyLength; i++){
			stringList[i] = "";
		}
		for(int i = 0; i<(inputString.length()/keyLength); i++){
			String tempString = stringList[i%keyLength];
			tempString += inputString.substring(i, i+1);
			stringList[i%keyLength] = tempString;
		}
		
		ArrayList<ArrayList<cipherLetter>> fullChiList = getChiVals(keyLength, stringList);

		boolean done = false;
		while(done == false){
			System.out.println("\n\nLets try to find the key now!\n");
			System.out.println("What would you like to do?");
			System.out.println("	- 1: See Chi values");
			System.out.println(" 	- 2: See/Modify Key");
			System.out.println("	- 3: Test current key");
			System.out.println("	- 4: See solution key");
			System.out.println("	- 5: Exit with current key");
			Scanner user_input = new Scanner(System.in);
			System.out.println("Please enter selection: ");
			int choice = Integer.parseInt(user_input.next());
			
			switch(choice){
				case(1): 
					seeChiValues(fullChiList);
					break;
				case(2):
					changeKey(keyString);
					break;
				case(3):
					System.out.println("** Testing key: " + keyString);
					testOutput(inputString, keyString);
					break;
				case(4):
					break;
				case(5):
					done = true;
					break;
				default:
					System.out.println("Oops! You didnt enter a valid choice");
					break;
			}
			
			
		}
		return keyString;
	}
	
	public static void seeChiValues(ArrayList<ArrayList<cipherLetter>> fullChiList){
		
		System.out.println("Which Chi values would you like to see?");
		System.out.println("	- 0: See all Chi values");
		System.out.println(" 	- 1 to " + (fullChiList.size()) + ": See specific Chi value");
		Scanner user_input = new Scanner(System.in);
		System.out.println("Please enter selection: ");
		int choice = Integer.parseInt(user_input.next());
		
		if(choice > 0 && (choice-1) < fullChiList.size()){
			for(cipherLetter chiVal : fullChiList.get(choice - 1)){
				System.out.println("For letter " + chiVal.letter + " chi Val = " + chiVal.freq);
			}
		}
		else{
			for(ArrayList<cipherLetter> chiList : fullChiList){
				System.out.println("% % % % List " + (fullChiList.indexOf(chiList) + 1) + " OF " + fullChiList.size() + " % % % %");
				for(int j = 0; j < 5; j++){
					System.out.println("For letter " + chiList.get(j).letter + " chi Val = " + chiList.get(j).freq);
				}
			}
		}
				
	}
	
	public static ArrayList<ArrayList<cipherLetter>> getChiVals(int keyLength, String[] stringList){
		ArrayList<ArrayList<cipherLetter>> fullChiList = new ArrayList<ArrayList<cipherLetter>>();
		ArrayList<cipherLetter> englishLetterFreq = DecryptorModule.getEnglishLetterFreq();
		for(int i = 0; i<keyLength; i++){
			String tempString = stringList[i];
			ArrayList<cipherLetter> tempStringLetterFreq = DecryptorModule.getLetterFreq(tempString);
			
			Collections.sort(tempStringLetterFreq, new Comparator<cipherLetter>(){
				public int compare(cipherLetter l1, cipherLetter l2){
					return (int)((l1.letter - l2.letter) * 100);
				}
			});
			
			double chiSquared = 0;			
			ArrayList<cipherLetter> chiVals = new ArrayList<cipherLetter>();
			
			for(int j = 0; j<26; j++){
				chiSquared = 0;
				double letterCount = tempStringLetterFreq.get(j).freq;
				double expectedCount = tempString.length() * (englishLetterFreq.get(j).freq / 100);
				chiSquared = Math.pow((letterCount - expectedCount),2)/expectedCount;
				chiVals.add(new cipherLetter(tempStringLetterFreq.get(j).letter, chiSquared));
			}
			
			Collections.sort(chiVals, new Comparator<cipherLetter>(){
				public int compare(cipherLetter l1, cipherLetter l2){
					return (int)((l1.freq - l2.freq) * 10000);
				}
			});
			
			fullChiList.add(chiVals);
		}
		return fullChiList;
	}
	
	public static void changeKey(String keyString){
		System.out.println("What would you like to do?");
		System.out.println("	- 1: Change whole key");
		System.out.println(" 	- 2: Change one letter");
		Scanner user_input = new Scanner(System.in);
		System.out.println("Please enter selection: ");
		int choice = Integer.parseInt(user_input.next());
		
		if(choice == 1){
			user_input = new Scanner(System.in);
			String key = "";
			while(key.length() != keyString.length()){
				System.out.println("Please enter new key of size " + keyString.length() + ": ");
				key = user_input.nextLine();
			}
			key.toUpperCase();
			keyString = key;
		}
		else if(choice == 2){
			System.out.println("Please enter which letter you would like to replace (1-" + keyString.length() + ": ");
			int letterIdx = Integer.parseInt(user_input.next());
		}
		else{
			System.out.println("Invalid choice, returning key menu");
		}
	}
	
	public static void testOutput(String inputString, String key){				
		String output = "";
		for(int i=0; i<50; i++){
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
