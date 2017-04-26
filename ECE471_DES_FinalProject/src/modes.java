import java.util.Scanner;

public class modes {
	
	public void intuitiveFunctionName(){
		
		String inputString = new String();
		String outputStringEncrypt = new String();
		String outputStringDecrypt = new String();
		
		
		int[] key = new int[64];
		int method = 0;
		String encryptMethod = "";
		Scanner user_input = new Scanner(System.in);
		
		//get that dank inputString from a file
		//convert that dank inputString to binary
		
		while(true){
			
			System.out.println("Encryption Methods: ECB, CBC, CFB, OFB, CNT");
			System.out.print("Enter 1-5 to select an encrpytion method: ");
			try {
				method = Integer.parseInt(user_input.next());
			} 
			catch(NumberFormatException e){}
			
			if(1 <= method & method <= 5)
				break;
		}
		
		switch(method){
		case 1: 
			encryptECB(inputString, key);
			encryptMethod = "ECB";
			break;
		case 2: 
			encryptCBC(inputString, key);
			encryptMethod = "CBC";
			break;
		case 3: 
			encryptCFB(inputString, key);
			encryptMethod = "CFB";
			break;
		case 4: 
			encryptOFB(inputString, key);
			encryptMethod = "OFB";
			break;
		case 5: 
			encryptCNT(inputString, key);
			encryptMethod = "CNT";
			break;
		}
		
		
		user_input.close();
	}
	
	private int[] encryptECB(String inputString, int[] key){
		int[] outputStringEncrypt = new int[64];
		
		return outputStringEncrypt;
	}

	private int[] encryptCBC(String inputString, int[] key){
		int[] outputStringEncrypt = new int[64];
			
		return outputStringEncrypt;
	}

	private int[] encryptCFB(String inputString, int[] key){
		int[] outputStringEncrypt = new int[64];
		
		return outputStringEncrypt;
	}

	private int[] encryptOFB(String inputString, int[] key){
		int[] outputStringEncrypt = new int[64];
		
		return outputStringEncrypt;
	}

	private int[] encryptCNT(String inputString, int[] key){
		int[] outputStringEncrypt = new int[64];
		
		return outputStringEncrypt;
	}

}
