package Decrypting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DecryptorModule {
	public static void main(String[] args) throws IOException {
		int a = 3;
		int b = 5;
		//new File("Drawable_Images/Archer.png")
		//String filePath = "/Users/Ben/Desktop/ECE 471/Projects/Project 1/ciphertexts/cipher1.txt";
		String filePath = "ciphertexts/cipher1.txt";
		String s = "";
		String line = "";
		try{
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null){
				s = s + line;
			}
		}
		catch(FileNotFoundException ex){
			System.out.println("Counld not find file");
		}

		ArrayList<cipherLetter> stringList = new ArrayList<cipherLetter>();
		for(int i=DecryptorModule.charToInt('A'); i<DecryptorModule.charToInt('Z'); i++){
			cipherLetter tempLetter = new cipherLetter();
			tempLetter.letter = DecryptorModule.intToChar(i);
			int counter = 0;
			for(int j=0; j<s.length(); j++){
				if(DecryptorModule.charToInt(s.charAt(j)) == i){
					++counter;
				}
			}
			tempLetter.freq = counter;
			stringList.add(tempLetter);
		}
		Collections.sort(stringList, new Comparator<cipherLetter>(){
			public int compare(cipherLetter l1, cipherLetter l2){
				return l2.freq - l1.freq;
			}
		});
		for(int i = 0; i<stringList.size(); i++){
			System.out.println(stringList.get(i).letter + ": " + stringList.get(i).freq + " - " + (double)stringList.get(i).freq/s.length() * 100 + "%");
		}
		
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
			
			System.out.println(stringList.get(i).letter + " - E = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('E')));
			System.out.println(stringList.get(i).letter + " - T = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('T')));
			System.out.println(stringList.get(i).letter + " - A = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('A')));
			System.out.println(stringList.get(i).letter + " - O = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('O')));
			System.out.println(stringList.get(i).letter + " - I = " + Math.abs(DecryptorModule.charToInt(stringList.get(i).letter) - DecryptorModule.charToInt('I')));
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
		
		//Scanner user_input = new Scanner(System.in);
		//System.out.println("Please input shift amount: ");
		//int shiftAmount = Integer.parseInt(user_input.next());
		System.out.println("Shift amount will be: " + highest_Val);
		int shiftAmount = highest_Val;
		DecryptorModule.shiftDecrypt(s, shiftAmount);
		
		return;
	}
	
	public static char intToChar(int letter){
		char temp = (char) letter;
		
		return temp;
	}
	
	public static int charToInt(char letter){
		int temp = (int) letter;
		
		return temp;
	}
	
	public static void shiftDecrypt(String s, int shiftAmount){
		for(int i=0; i<s.length(); i++){
			int shiftedVal = DecryptorModule.charToInt(s.charAt(i));
			if((shiftedVal - shiftAmount) < DecryptorModule.charToInt('A')){
				shiftedVal = DecryptorModule.charToInt('Z') - (DecryptorModule.charToInt('A') - shiftedVal - shiftAmount);
			}
			else{
				shiftedVal = shiftedVal - shiftAmount;
			}
			shiftedVal = ((shiftedVal - DecryptorModule.charToInt('A'))%25) + DecryptorModule.charToInt('A');
			char toPrint = DecryptorModule.intToChar(shiftedVal);
			System.out.print(toPrint);
			if(((i%75) == 0) & (i != 0)){
				System.out.println("");
			}
		}
	}
}
