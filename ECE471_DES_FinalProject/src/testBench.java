
public class testBench {
	public static void main(String[] args) {
		//Convert input string to binary
		String inputText = "DEADBEEF";
		
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
		String outputString = DES.DESLoop(inputText, key, true);
		double firstEndTime = System.currentTimeMillis();
		
		double secondStartTime = System.currentTimeMillis();
		//Decryption
		String secondOutputString = DES.DESLoop(outputString, key, false);
		double endTime = System.currentTimeMillis();
		
		double firstTime = firstEndTime - startTime;
		double secondTime = endTime - secondStartTime;
		double totalTime = endTime - startTime;
		System.out.println("First time: " + firstTime + " ms");
		System.out.println("Second time: " + secondTime + " ms");
		System.out.println("Total time: " + totalTime + " ms");
		
	}
}
