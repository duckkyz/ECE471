package modes;
import java.util.Random;
import java.util.Scanner;

import des.DES;

public class modes {
		
	public static String ECB(String inputString, int[] key, boolean isEncrypting){
		String desInputString = "";
		StringBuilder outputString = new StringBuilder();
		
		for(int x = 0; x < inputString.length(); x = x+8){
			
			//check if less than 8
			
			desInputString = inputString.substring(x, x+8);
			outputString.append(DES.DESLoop(desInputString, key, isEncrypting));
		}
		
		return outputString.toString();
	}

	public static String CBC(String inputString, int[] key, int[] IV, boolean isEncrypting){
		int[] runningIV = new int[64];
		String desOutputString = "";
		StringBuilder outputString = new StringBuilder();
		
		if(isEncrypting == true){
			int[] binaryInput = new int[64];
			String desInputString = "";
			
			runningIV = IV;
			for(int x = 0; x < inputString.length(); x = x+8){
				binaryInput = DES.stringToBin(inputString.substring(x, x+8));						//converts 8 chars to binary
				binaryInput = XOR(runningIV, binaryInput);											//XORs binaryInput with current IV equivalent
				desInputString = DES.binToString(binaryInput);										//converts to string
				desOutputString = DES.DESLoop(desInputString, key, isEncrypting);					//DES
				runningIV = DES.stringToBin(desOutputString);										//Converts resulting Ciphertext to XOR in next iteration
				outputString.append(desOutputString);												//appends result to outputString
			}
		}
		else if(isEncrypting == false){
			int [] binaryOutput = new int[64];
			
			runningIV = DES.stringToBin(inputString.substring(0, 8));
			for(int x = 0; x < inputString.length(); x = x+8){
				runningIV = DES.stringToBin(inputString.substring(x, x+8));							//gets the value used to XOR in next block
				desOutputString = DES.DESLoop(inputString.substring(x, x+8), key, isEncrypting);	//DES
				binaryOutput = DES.stringToBin(desOutputString);									//DESresult to binary
				
				if(x == 0)
					binaryOutput = XOR(IV, binaryOutput);
				else
					binaryOutput = XOR(runningIV, binaryOutput);									//XOR runningIV and DESreult
				
				outputString.append(DES.binToString(binaryOutput));									//add to outputString
			}
			
		}
		return outputString.toString();
	}

	public static String CFB(String inputString, int[] key, int[] IV, boolean isEncrypting){
		String outputString = "";
		
		return outputString;
	}

	public static String OFB(String inputString, int[] key, int[] IV, boolean isEncrypting){
		String outputString = "";
		
		return outputString;
	}

	public static String CNT(String inputString, int[] key, int[] IV, boolean isEncrypting){
		String outputString = "";
		
		return outputString;
	}
	
	public static int[] createIV(){
		int[] IV = new int[64];
		
		for(int i = 0; i < 64; i++){
			if((Math.random() * 100) > 50){
				IV[i] = 0;
			}
			else{
				IV[i] = 1;
			}
		}
		
		return IV;
	}
	
	public static int[] XOR(int[] input1, int[] input2){
		int[] result = new int[64];
		for(int i = 0; i < 64; i++){
			if(input1[i] == 1 & input2[i] == 0)
				result[i] = 1;
			else if(input1[i] == 0 & input2[i] == 1)
				result[i] = 1;
			else
				result[i] = 0;
		}
		return result;
	}
	
	public static int[] invertArray(int[] inputArray){
		int[] returnArray = new int[64];
		
		for(int x=0; x < inputArray.length / 2;x++){										//reverses the IV to use on the reversed inputString
			int temp = inputArray[x];
			returnArray[x] = inputArray[inputArray.length - x - 1];
			returnArray[inputArray.length - x - 1] = temp;
		}
		
		return returnArray;
	}

}
