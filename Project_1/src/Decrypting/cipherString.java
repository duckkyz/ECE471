package Decrypting;

import java.util.ArrayList;

public class cipherString {
	public int freq = 0;
	public String string = "";
	public ArrayList<Integer> locations = new ArrayList<Integer>();
	
	public cipherString(){
		
	}
	public cipherString(int freq, String string, int loc){
		this.freq = freq;
		this.string = string;
		locations.add(loc);
	}
	
	public void getDistances(){
		System.out.println("String: " + string);
		for(int i=1;i<locations.size();i++){
			System.out.println("		" + (locations.get(i) - locations.get(i - 1)));
		}
	}
}
