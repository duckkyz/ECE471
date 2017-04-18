
public class DES {

	public void DESLoop(){
		//Need to finish this
		int[] inputText = new int[64];
		for(int i = 0; i < inputText.length; i++){
			if((Math.random() * 100) > 50){
				inputText[i] = 0;
			}
			else{
				inputText[i] = 1;
			}
		}
		
		int[] initPerm = permutation.initialPermutation(inputText);
		
		
		int[] finalPerm = permutation.finalPermutation(initPerm);
		
		for(int i = 0; i < inputText.length; i++){
			if(inputText[i] != finalPerm[i]){
				System.out.println("ERROR!!!! inputText and finalPerm do not match at i = " + i);
			}
		}		
	}
	
}
