package testBench;

import java.io.IOException;
import java.io.PrintWriter;

public class testFileCreator {

	public static void main(String[] args) {
		for(int i = 31; i < 41; i++){
			String test = largeFile();
			try{
			    PrintWriter writer = new PrintWriter("testFiles/test_" + (i) + ".txt", "UTF-8");
			    writer.print(test);
			    writer.close();
			} catch (IOException e) {
			   // do something
			}
		}
		
	}

	public static String smallFile(){
		String outputString = "";
		for(int i = 0; i < (8*10); i++){
			outputString = outputString + Character.toString(getRandChar());
		}
		return outputString;
	}

	public static String medFile(){
		String outputString = "";
		for(int i = 0; i < (8*100); i++){
			outputString = outputString + Character.toString(getRandChar());
		}
		return outputString;
	}
	
	public static String largeFile(){
		String outputString = "";
		for(int i = 0; i < (8*1000); i++){
			outputString = outputString + Character.toString(getRandChar());
		}
		return outputString;
	}
	
	public static char getRandChar(){
		int firstSel = (int)(Math.random() * 101);
		double secondSel = (Math.random());
		if(firstSel > 66){
			//Capitol letters 65 - 90
			return (char)(((int)(secondSel*26)) + 65);

		}
		else if(firstSel > 33){
			//Lower case letters 97 - 122
			return (char)(((int)(secondSel*26)) + 97);				
		}
		else{
			//Number 48 - 57
			return (char)(((int)(secondSel*10)) + 48);
		}
	}
	
}
