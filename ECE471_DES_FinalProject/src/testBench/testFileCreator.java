package testBench;

public class testFileCreator {

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			String test = smallFile();
			System.out.println(test);
		}
	}

	public static String smallFile(){
		String outputString = "";
		for(int i = 0; i < (8*10); i++){
			int firstSel = (int)(Math.random() * 101);
			double secondSel = (Math.random());
			if(firstSel > 66){
				//Capitol letters 65 - 90
				outputString = outputString + (char)(((int)(secondSel*26)) + 65);

			}
			else if(firstSel > 33){
				//Lower case letters 97 - 122
				outputString = outputString + (char)(((int)(secondSel*26)) + 97);				
			}
			else{
				//Number 48 - 57
				outputString = outputString + (char)(((int)(secondSel*10)) + 48);
			}
		}
		return outputString;
	}
	
}
