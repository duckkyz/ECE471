package Decrypting;

import java.util.ArrayList;

public class DecryptShift {

	public static void decryptShiftCipher(ArrayList<cipherLetter> stringList, String inputString){
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
	
}
