
public class DES {

	public static void main(String[] args) {
		//Create random input string
		int[] inputString = new int[64];
		for(int i = 0; i < inputString.length; i++){
			if((Math.random() * 100) > 50){
				inputString[i] = 0;
			}
			else{
				inputString[i] = 1;
			}
		}
		
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
		int[] outputString = DESLoop(inputString, key, true);
		double firstEndTime = System.currentTimeMillis();
		
		double secondStartTime = System.currentTimeMillis();
		//Decryption
		int[] secondOutputString = DESLoop(outputString, key, false);
		double endTime = System.currentTimeMillis();
		
		double firstTime = firstEndTime - startTime;
		double secondTime = endTime - secondStartTime;
		double totalTime = endTime - startTime;
		System.out.println("First time: " + firstTime + " ms");
		System.out.println("Second time: " + secondTime + " ms");
		System.out.println("Total time: " + totalTime + " ms");
	}
	
	public static int[] DESLoop(int[] inputString, int[] key, boolean isEncrypting){
		//Get input/outputs initialized
		int[] inputText = inputString;
		int[] outputText = new int[64];
		
		//Visually print input text
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
		return outputText;
	}
}
