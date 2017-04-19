
public class DES {

	public static void main(String[] args) {
		double startTime = System.currentTimeMillis();
		DESLoop();
		double endTime = System.currentTimeMillis();
		double totalTime = endTime - startTime;
		System.out.println("Total time: " + totalTime);
	}
	
	public static void DESLoop(){
		//Need to finish this
		int[] inputText = new int[64];
		int[] outputText = new int[64];
		for(int i = 0; i < inputText.length; i++){
			if((Math.random() * 100) > 50){
				inputText[i] = 0;
			}
			else{
				inputText[i] = 1;
			}
		}
		System.out.println("inputText = ");
		for(int i = 0; i < inputText.length; i++){
			System.out.print(" "+ inputText[i] + ",");
		}
		System.out.println("");

		//IP
		int[] initPerm = permutation.initialPermutation(inputText);
		
		keyMixing.originalKey = new int[64];
		for(int i = 0; i < keyMixing.originalKey.length; i++){
			if((Math.random() * 100) > 50){
				keyMixing.originalKey[i] = 0;
			}
			else{
				keyMixing.originalKey[i] = 1;
			}
		}
		
		System.out.println("Key = ");
		for(int i = 0; i < keyMixing.originalKey.length; i++){
			System.out.print(" "+ keyMixing.originalKey[i] + ",");
		}
		System.out.println("");
		keyMixing.PC1();
		//Feistel chain
		int[] left = new int[32];
		int[] right = new int[32];
		for(int i = 0; i < 32; i++){
			left[i] = inputText[i];
			right[i] = inputText[i + 32];
		}
		for(int i = 1; i < 17; i++){
			//get SubKey
			keyMixing.rotateKey(i);
			fBlock block = new fBlock(right, keyMixing.PC2());
			int[] fBlockOutput = block.process();
			int[] newRight = new int[32];
			for(int j = 0; i < 32; i++){	
				newRight[j] = left[j] ^ fBlockOutput[j];
			}
			if(i == 16){
				left = newRight;
				right = right;
			}
			else{
				left = right;
				right = newRight;
			}
		}
		for(int i = 0; i < 32; i++){
			outputText[i] = left[i];
			outputText[i + 32] = right[i];
		}
		
		
		//FP
		int[] finalPerm = permutation.finalPermutation(initPerm);
		
		for(int i = 0; i < inputText.length; i++){
			if(inputText[i] != finalPerm[i]){
				System.out.println("ERROR!!!! inputText and finalPerm do not match at i = " + i);
				return;
			}
		}	
		System.out.println("finalPerm = ");
		for(int i = 0; i < finalPerm.length; i++){
			System.out.print(" "+ finalPerm[i] + ",");
		}
		System.out.println("");

	}
}
