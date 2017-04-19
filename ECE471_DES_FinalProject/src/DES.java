
public class DES {

	public static void main(String[] args) {
		DESLoop();
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
		
		//IP
		int[] initPerm = permutation.initialPermutation(inputText);

		keyMixing.originalKey = new int[64];
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
	}
}
