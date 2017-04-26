package des;

public class DES {
	/*Our DES implementation assumes a few things:\
	 * 	1. Input strings are formatted in binary and are exactly 64 bits
	 * 		a. This can be modified/fixed on the implementation side where the input is broken up into
	 * 			64-bit sized chunks
	 * 		b. This is fixed with padding from binToString()
	 * 	2. Key strings are formatted in binary and are exactly 64 bits
	 * 		a. The key is printed out so you can save it, but for the demo it randomly generates keys
	 * 	3. There is a boolean that must be set for either encryption or decryption
	 * 		a. This simplifies the design so you simply call "DESLoop(inputString, key, isEncrypting)"
	 * 			This can be implemented from any function and should be done, rather than modifying the
	 * 			DESLoop method.
	 */
	
	public static String DESLoop(String inputString, int[] key, boolean isEncrypting){
		//Get input/outputs initialized
		int[] inputText = DES.stringToBin(inputString);
		int[] outputText = new int[64];
		String outputString = "";
				
		//Visually print input text
		for(int i = 0; i < inputText.length/8; i++){
			int temp = (128 * inputText[i*8 + 0])
						+ (64 * inputText[i*8 + 1])
						+ (32 * inputText[i*8 + 2])
						+ (16 * inputText[i*8 + 3])
						+ (8 * inputText[i*8 + 4])
						+ (4 * inputText[i*8 + 5])
						+ (2 * inputText[i*8 + 6])
						+ (1 * inputText[i*8 + 7]);
			char tempLetter = (char) temp;
			System.out.print(tempLetter);
		}
		System.out.println("");
		System.out.println("inputText = ");
		for(int i = 0; i < inputText.length; i++){
			System.out.print(" "+ inputText[i] + ",");
		}
		System.out.println("");

		
		//Initial Permutation
		int[] initPerm = new int[64];
		if(isEncrypting){
			initPerm = permutation.initialPermutation(inputText);
		}
		else{
			initPerm = permutation.finalPermutation(inputText);
		}
		
		//Set the key
		keyMixing.originalKey = key;
		
		//Visually print the key
		System.out.println("Key = ");
		for(int i = 0; i < keyMixing.originalKey.length; i++){
			System.out.print(" "+ keyMixing.originalKey[i] + ",");
		}
		System.out.println("");
		
		//Get the modified Key ready for DES subkey schedule
		keyMixing.PC1();
		
		//Set initial parameters for Feistel chain
		int[] left = new int[32];
		int[] right = new int[32];
		for(int i = 0; i < 32; i++){
			left[i] = inputText[i];
			right[i] = inputText[i + 32];
		}
		
		//Get the subKeys, this simplifies the encrypting/decrypting process
		int[][] subKeyArray = new int[16][56];
		for(int i = 1; i < 17; i++){
			keyMixing.rotateKey(i);
			subKeyArray[i-1] = keyMixing.subKeyInput;
		}
		
		//Feistel chain
		for(int i = 1; i < 17; i++){
			//get SubKey
			int[] subKey = new int[56];
			//Get the subKey, depending on if enc/dec
			if(isEncrypting){
				subKey = subKeyArray[i-1];
			}
			else{
				subKey = subKeyArray[16-i];
			}

			//Set up the fBlock
			fBlock block = new fBlock(right, keyMixing.PC2(subKey));
			int[] fBlockOutput = block.process();
			int[] newRight = new int[32];
			
			//Preform the XOR ops
			for(int j = 0; j < 32; j++){	
				newRight[j] = left[j] ^ fBlockOutput[j];
			}
			
			//Set next iterations inputs
			if(i == 16){
				left = newRight;
				right = right;
			}
			else{
				left = right;
				right = newRight;
			}
		}
		
		//Concatenate the output string
		for(int i = 0; i < 32; i++){
			outputText[i] = left[i];
			outputText[i + 32] = right[i];
		}
		
		
		//Final permutation
		if(isEncrypting){
			outputText = permutation.finalPermutation(inputText);
		}
		else{
			outputText = permutation.initialPermutation(inputText);
		}
		
		
		System.out.println("outputText = ");
		for(int i = 0; i < outputText.length; i++){
			System.out.print(" "+ outputText[i] + ",");
		}
		System.out.println("");
		
		outputString = DES.binToString(outputText);
		
		return outputString;
	}
	
	public static String binToString(int[] inputBin){
		String outputString = "";
		for(int i = 0; i < inputBin.length/8; i++){
			int temp = (128 * inputBin[i*8 + 0])
						+ (64 * inputBin[i*8 + 1])
						+ (32 * inputBin[i*8 + 2])
						+ (16 * inputBin[i*8 + 3])
						+ (8 * inputBin[i*8 + 4])
						+ (4 * inputBin[i*8 + 5])
						+ (2 * inputBin[i*8 + 6])
						+ (1 * inputBin[i*8 + 7]);
			char tempLetter = (char) temp;
			outputString = outputString + tempLetter;
		}
		return outputString;
	}
	
	public static int[] stringToBin(String inputString){
		if(inputString.length()%8 != 0){
			//System.out.println("Going to append " + (8-inputString.length()%8) + " nulls");
			int itters = (8-inputString.length()%8);
			for(int i = 0; i < itters; i++){
				inputString = inputString + Character.toString((char)0);
			}
		}
		int[] inputText = new int[inputString.length()*8];
		for(int i = 0; i < inputString.length(); i++){
			int temp = (int) inputString.charAt(i);
			for(int j = 7; j >= 0; j--){
				int power = (int)Math.pow(2, j);
				inputText[(i+1)*8 - (j+1)] = (temp/power);
				temp = temp - ((temp/power) * power);
			}
		}
		return inputText;
	}
}


