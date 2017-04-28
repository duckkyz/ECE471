package modes;
import java.util.Random;
import java.util.Scanner;

import des.DES;

public class modes {
		
	public static String ECB(String inputString, int[] key, boolean isEncrypting){
		String desInputString = "";
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
		StringBuilder outputString = new StringBuilder();
		
		for(int x = 0; x < inputString.length(); x = x+8){
			desInputString = inputString.substring(x, x+8);
			outputString.append(DES.DESLoop(desInputString, key, isEncrypting));
		}
		
		return outputString.toString();
	}

	public static String CBC(String inputString, int[] key, int[] IV, boolean isEncrypting){
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
		int[] runningIV = new int[64];
		String desOutputString = "";
		StringBuilder outputString = new StringBuilder();
		
		if(isEncrypting == true){
			int[] binaryInput = new int[64];
			String desInputString = "";
			
			runningIV = IV;
			for(int x = 0; x < inputString.length(); x = x+8){
				String tempStr = inputString.substring(x, x+8);
				binaryInput = DES.stringToBin(tempStr);												//converts 8 chars to binary
				binaryInput = XOR(runningIV, binaryInput);											//XORs binaryInput with current IV equivalent
				desInputString = DES.binToString(binaryInput);										//converts to string
				desOutputString = DES.DESLoop(desInputString, key, isEncrypting);					//DES
				runningIV = DES.stringToBin(desOutputString);										//Converts resulting Ciphertext to XOR in next iteration
				outputString.append(desOutputString);												//appends result to outputString
			}
		}
		else if(isEncrypting == false){
			int [] binaryOutput = new int[64];
			
			for(int x = 0; x < inputString.length(); x = x+8){
				desOutputString = DES.DESLoop(inputString.substring(x, x+8), key, isEncrypting);	//DES
				binaryOutput = DES.stringToBin(desOutputString);									//DESresult to binary
				
				if(x == 0)
					binaryOutput = XOR(IV, binaryOutput);
				else
					binaryOutput = XOR(runningIV, binaryOutput);									//XOR runningIV and DESreult
				
				runningIV = DES.stringToBin(inputString.substring(x, x+8));							//gets the value used to XOR in next block
				outputString.append(DES.binToString(binaryOutput));									//add to outputString
			}
			
		}
		return outputString.toString();
	}

	public static String CFB(String inputString, int[] key, int[] IV, boolean isEncrypting){
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
		StringBuilder outputString = new StringBuilder();
		String desInputString = "";
		String desOutputString = "";
		int [] binaryOutput = new int[64];
		
		if(isEncrypting == true){
			
			desInputString = DES.binToString(IV);
			for(int x = 0; x < inputString.length(); x = x+8){
				desOutputString = DES.DESLoop(desInputString, key, isEncrypting);
				binaryOutput = DES.stringToBin(desOutputString);
				binaryOutput = XOR(binaryOutput, DES.stringToBin(inputString.substring(x, x+8)));
				desInputString = DES.binToString(binaryOutput);
				outputString.append(desInputString);
			}
		}
		
		else if(isEncrypting == false){
			
			desInputString = DES.binToString(IV);
			for(int x = 0; x < inputString.length(); x = x+8){
				desOutputString = DES.DESLoop(desInputString, key, isEncrypting);
				binaryOutput = DES.stringToBin(desOutputString);
				binaryOutput = XOR(binaryOutput, DES.stringToBin(inputString.substring(x, x+8)));
				desInputString = inputString.substring(x, x+8);
				outputString.append(DES.binToString(binaryOutput));
			}
		}
		
		return outputString.toString();
	}

	public static String OFB(String inputString, int[] key, int[] IV, boolean isEncrypting){
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
		StringBuilder outputString = new StringBuilder();
		String desInputString = "";
		String desOutputString = "";
		int [] binaryOutput = new int[64];
		
		desInputString = DES.binToString(IV);
		for(int x = 0; x < inputString.length(); x = x+8){
			desOutputString = DES.DESLoop(desInputString, key, isEncrypting);
			binaryOutput = DES.stringToBin(desOutputString);
			binaryOutput = XOR(binaryOutput, DES.stringToBin(inputString.substring(x, x+8)));
			desInputString = desOutputString;
			outputString.append(desInputString);
		}
		
		return outputString.toString();
	}

	public static String CRT(String inputString, int[] key, int[] IV, boolean isEncrypting){
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
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
			result[i] = input1[i] ^ input2[i];
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
