package Decrypting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DecryptVigenere {
	public static void decryptVigenereCipher(ArrayList<cipherLetter> stringList, String inputString){
		
		
		
		
		
		
		ArrayList<cipherLetter> letterFreqList = new ArrayList<cipherLetter>();
		letterFreqList.add(new cipherLetter(127, 'E'));
		ArrayList<Integer> freqList = new ArrayList<Integer>();
		for(int i=0; i<6; i++){
			System.out.println("");
			System.out.println("New letter: " + stringList.get(i).letter);
			
			freqList.add(Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('E')));
			freqList.add(Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('T')));
			freqList.add(Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('A')));
			freqList.add(Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('O')));
			freqList.add(Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('I')));
			/*	
			System.out.println(stringList.get(i).letter + " - E = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('E')));
			System.out.println(stringList.get(i).letter + " - T = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('T')));
			System.out.println(stringList.get(i).letter + " - A = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('A')));
			System.out.println(stringList.get(i).letter + " - O = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('O')));
			System.out.println(stringList.get(i).letter + " - I = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('I')));
			 */
		}
		int highest_Val = 0;
		for(int i = 0; i < 26; i++){
			int temp = 0;
			for(Integer myNum : freqList){
				if(myNum.equals(i)){
					++temp;
				}
			}
			if(temp > highest_Val){
				highest_Val = i;
			}
			if(temp != 0){
				System.out.println("Occurances of shift amount " + i + " : " + temp);
			}
		}
		
		System.out.println("\nShift amount will be: " + highest_Val);
		int shiftAmount = highest_Val;
		String toPrint = DecryptorModule.shiftMessage(inputString, shiftAmount);
		
		System.out.print(toPrint);
	}
	
	
	public static int kasiskiTest(String inputString){
		int keyLength = 0; //should output 9
		
		for(keyLength = 3; keyLength < 10; keyLength++){
			ArrayList<cipherString> stringList = new ArrayList<cipherString>();
			for(int i=0; i<=Math.ceil(inputString.length()/keyLength); i = i + keyLength + 1){
				boolean found = false;
				cipherString tempString = new cipherString();
				tempString.string = inputString.substring(i, i + keyLength);
				int counter = 1;
				
				for(cipherString cs : stringList){
					if(cs.string.equals(tempString.string)){
						++cs.freq;
						found = true;
					}
				}
				if(found == false){
					tempString.freq = counter;
					stringList.add(tempString);
				}
			}
			Collections.sort(stringList, new Comparator<cipherString>(){
				public int compare(cipherString l1, cipherString l2){
					return l2.freq - l1.freq;
				}
			});
		}
	
		
		return keyLength;
	}
	
}
