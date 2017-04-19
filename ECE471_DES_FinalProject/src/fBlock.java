
/*
 * The F-function, depicted in Figure 2, operates on half a block (32 bits) at a time and consists of four stages:
 * 1. Expansion: 
 * 		the 32-bit half-block is expanded to 48 bits using the expansion permutation, denoted E in the diagram, by 
 * 		duplicating half of the bits. The output consists of eight 6-bit (8 * 6 = 48 bits) pieces, each containing a 
 * 		copy of 4 corresponding input bits, plus a copy of the immediately adjacent bit from each of the input pieces 
 * 		to either side.
 * 2. Key mixing: 
 * 		the result is combined with a subkey using an XOR operation. Sixteen 48-bit subkeys—one for each round—are 
 * 		derived from the main key using the key schedule (described below).
 * 3. Substitution: 
 * 		after mixing in the subkey, the block is divided into eight 6-bit pieces before processing by the S-boxes, 
 * 		or substitution boxes. Each of the eight S-boxes replaces its six input bits with four output bits according 
 * 		to a non-linear transformation, provided in the form of a lookup table. The S-boxes provide the core of the 
 * 		security of DES—without them, the cipher would be linear, and trivially breakable.
 * 4. Permutation: 
 * 		finally, the 32 outputs from the S-boxes are rearranged according to a fixed permutation, the P-box. This is 
 * 		designed so that, after permutation, each S-box's output bits are spread across four different S boxes in the 
 * 		next round.
 * 
 * 
 * 
 * 
 * 
 */

public class fBlock {
	
	private int inputString[];
	private int subKey[];
	private int expansionBits[][] = new int[8][6];
	private int[] sBlockOutput = new int[8*4];
	private int[] fOutput = new int[8*4];
	
	public fBlock(int[] inputString, int[] subKey){
		this.inputString = inputString;
		this.subKey = subKey;
	}
	
	public int[] process(){
		expansion();
		keyMixing();
		s1Block();
		s2Block();
		s3Block();
		s4Block();
		s5Block();
		s6Block();
		s7Block();
		s8Block();
		postSBlockPerm();
		return fOutput;
	}
	
	public void expansion(){
		for(int i = 0; i < 8; i++){
			if(i == 0){
				expansionBits[i][0] = inputString[31];
			}
			else{
				expansionBits[i][0] = inputString[i*4 + -1];
			}
			expansionBits[i][1] = inputString[i*4 + 0];
			expansionBits[i][2] = inputString[i*4 + 1];
			expansionBits[i][3] = inputString[i*4 + 2];
			expansionBits[i][4] = inputString[i*4 + 3];
			if(i == 7){
				expansionBits[i][5] = inputString[1];
			}
			else{
				expansionBits[i][5] = inputString[i*4 + 4];
			}
		}
	}
	
	public void keyMixing(){
		for(int i = 0; i < 8; i++){	
			expansionBits[i][0] = expansionBits[i][0] ^ subKey[i*6 + 0];
			expansionBits[i][1] = expansionBits[i][1] ^ subKey[i*6 + 1];
			expansionBits[i][2] = expansionBits[i][2] ^ subKey[i*6 + 2];
			expansionBits[i][3] = expansionBits[i][3] ^ subKey[i*6 + 3];
			expansionBits[i][4] = expansionBits[i][4] ^ subKey[i*6 + 4];
			expansionBits[i][5] = expansionBits[i][5] ^ subKey[i*6 + 5];
			
		}
	}
	
	public void s1Block(){
		/*
		 * S1	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
		 * 0yyyy0	14	4	13	1	2	15	11	8	3	10	6	12	5	9	0	7
		 * 0yyyy1	0	15	7	4	14	2	13	1	10	6	12	11	9	5	3	8
		 * 1yyyy0	4	1	14	8	13	6	2	11	15	12	9	7	3	10	5	0
		 * 1yyyy1	15	12	8	2	4	9	1	7	5	11	3	14	10	0	6	13
		 */
		int temp = 0;
		int midVals = (expansionBits[0][4]*8) + (expansionBits[0][3]*4) + 
						(expansionBits[0][2]*2) + (expansionBits[0][1]*1);
		switch(expansionBits[0][0]){
		case 0:
			switch(expansionBits[0][5]){
			case 0: 
				if(midVals == 0){
					temp = 14;
				}
				else if(midVals == 1){
					temp = 4;
				}
				else if(midVals == 2){
					temp = 13;
				}
				else if(midVals == 3){
					temp = 1;
				}
				else if(midVals == 4){
					temp = 2;
				}
				else if(midVals == 5){
					temp = 15;
				}
				else if(midVals == 6){
					temp = 11;
				}
				else if(midVals == 7){
					temp = 8;
				}
				else if(midVals == 8){
					temp = 3;
				}
				else if(midVals == 9){
					temp = 10;
				}
				else if(midVals == 10){
					temp = 6;
				}
				else if(midVals == 11){
					temp = 12;
				}
				else if(midVals == 12){
					temp = 5;
				}
				else if(midVals == 13){
					temp = 9;
				}
				else if(midVals == 14){
					temp = 0;
				}
				else if(midVals == 15){
					temp = 7;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 0;
				}
				else if(midVals == 1){
					temp = 15;
				}
				else if(midVals == 2){
					temp = 7;
				}
				else if(midVals == 3){
					temp = 4;
				}
				else if(midVals == 4){
					temp = 14;
				}
				else if(midVals == 5){
					temp = 2;
				}
				else if(midVals == 6){
					temp = 13;
				}
				else if(midVals == 7){
					temp = 1;
				}
				else if(midVals == 8){
					temp = 10;
				}
				else if(midVals == 9){
					temp = 6;
				}
				else if(midVals == 10){
					temp = 12;
				}
				else if(midVals == 11){
					temp = 11;
				}
				else if(midVals == 12){
					temp = 9;
				}
				else if(midVals == 13){
					temp = 5;
				}
				else if(midVals == 14){
					temp = 3;
				}
				else if(midVals == 15){
					temp = 8;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[0][5]){
			case 0: 
				if(midVals == 0){
					temp = 4;
				}
				else if(midVals == 1){
					temp = 1;
				}
				else if(midVals == 2){
					temp = 14;
				}
				else if(midVals == 3){
					temp = 8;
				}
				else if(midVals == 4){
					temp = 13;
				}
				else if(midVals == 5){
					temp = 6;
				}
				else if(midVals == 6){
					temp = 2;
				}
				else if(midVals == 7){
					temp = 11;
				}
				else if(midVals == 8){
					temp = 15;
				}
				else if(midVals == 9){
					temp = 12;
				}
				else if(midVals == 10){
					temp = 9;
				}
				else if(midVals == 11){
					temp = 7;
				}
				else if(midVals == 12){
					temp = 3;
				}
				else if(midVals == 13){
					temp = 10;
				}
				else if(midVals == 14){
					temp = 5;
				}
				else if(midVals == 15){
					temp = 0;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 15;
				}
				else if(midVals == 1){
					temp = 12;
				}
				else if(midVals == 2){
					temp = 8;
				}
				else if(midVals == 3){
					temp = 2;
				}
				else if(midVals == 4){
					temp = 4;
				}
				else if(midVals == 5){
					temp = 9;
				}
				else if(midVals == 6){
					temp = 1;
				}
				else if(midVals == 7){
					temp = 7;
				}
				else if(midVals == 8){
					temp = 5;
				}
				else if(midVals == 9){
					temp = 11;
				}
				else if(midVals == 10){
					temp = 3;
				}
				else if(midVals == 11){
					temp = 14;
				}
				else if(midVals == 12){
					temp = 10;
				}
				else if(midVals == 13){
					temp = 0;
				}
				else if(midVals == 14){
					temp = 6;
				}
				else if(midVals == 15){
					temp = 13;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - 1] = temp/8;
		temp = temp - (8*(temp/8));
		sBlockOutput[8*4 - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 1! temp = " + temp);
		}
	}
	
	public void s2Block(){
		/*
		 S2	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	15	1	8	14	6	11	3	4	9	7	2	13	12	0	5	10
			0yyyy1	3	13	4	7	15	2	8	14	12	0	1	10	6	9	11	5
			1yyyy0	0	14	7	11	10	4	13	1	5	8	12	6	9	3	2	15
			1yyyy1	13	8	10	1	3	15	4	2	11	6	7	12	0	5	14	9
		 */
		int temp = 0;
		int midVals = (expansionBits[1][4]*8) + (expansionBits[1][3]*4) + 
						(expansionBits[1][2]*2) + (expansionBits[1][1]*1);
		switch(expansionBits[1][0]){
		case 0:
			switch(expansionBits[1][5]){
			case 0: 
				if(midVals == 0){
					temp = 15;
				}
				else if(midVals == 1){
					temp = 1;
				}
				else if(midVals == 2){
					temp = 8;
				}
				else if(midVals == 3){
					temp = 14;
				}
				else if(midVals == 4){
					temp = 6;
				}
				else if(midVals == 5){
					temp = 11;
				}
				else if(midVals == 6){
					temp = 3;
				}
				else if(midVals == 7){
					temp = 4;
				}
				else if(midVals == 8){
					temp = 9;
				}
				else if(midVals == 9){
					temp = 7;
				}
				else if(midVals == 10){
					temp = 2;
				}
				else if(midVals == 11){
					temp = 13;
				}
				else if(midVals == 12){
					temp = 12;
				}
				else if(midVals == 13){
					temp = 0;
				}
				else if(midVals == 14){
					temp = 5;
				}
				else if(midVals == 15){
					temp = 10;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 3;
				}
				else if(midVals == 1){
					temp = 13;
				}
				else if(midVals == 2){
					temp = 4;
				}
				else if(midVals == 3){
					temp = 7;
				}
				else if(midVals == 4){
					temp = 15;
				}
				else if(midVals == 5){
					temp = 2;
				}
				else if(midVals == 6){
					temp = 8;
				}
				else if(midVals == 7){
					temp = 14;
				}
				else if(midVals == 8){
					temp = 12;
				}
				else if(midVals == 9){
					temp = 0;
				}
				else if(midVals == 10){
					temp = 1;
				}
				else if(midVals == 11){
					temp = 10;
				}
				else if(midVals == 12){
					temp = 6;
				}
				else if(midVals == 13){
					temp = 9;
				}
				else if(midVals == 14){
					temp = 11;
				}
				else if(midVals == 15){
					temp = 5;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[1][5]){
			case 0: 
				if(midVals == 0){
					temp = 0;
				}
				else if(midVals == 1){
					temp = 14;
				}
				else if(midVals == 2){
					temp = 7;
				}
				else if(midVals == 3){
					temp = 11;
				}
				else if(midVals == 4){
					temp = 10;
				}
				else if(midVals == 5){
					temp = 4;
				}
				else if(midVals == 6){
					temp = 13;
				}
				else if(midVals == 7){
					temp = 1;
				}
				else if(midVals == 8){
					temp = 5;
				}
				else if(midVals == 9){
					temp = 8;
				}
				else if(midVals == 10){
					temp = 12;
				}
				else if(midVals == 11){
					temp = 6;
				}
				else if(midVals == 12){
					temp = 9;
				}
				else if(midVals == 13){
					temp = 3;
				}
				else if(midVals == 14){
					temp = 2;
				}
				else if(midVals == 15){
					temp = 15;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 8;
				}
				else if(midVals == 2){
					temp = 10;
				}
				else if(midVals == 3){
					temp = 1;
				}
				else if(midVals == 4){
					temp = 3;
				}
				else if(midVals == 5){
					temp = 15;
				}
				else if(midVals == 6){
					temp = 4;
				}
				else if(midVals == 7){
					temp = 2;
				}
				else if(midVals == 8){
					temp = 11;
				}
				else if(midVals == 9){
					temp = 6;
				}
				else if(midVals == 10){
					temp = 7;
				}
				else if(midVals == 11){
					temp = 12;
				}
				else if(midVals == 12){
					temp = 0;
				}
				else if(midVals == 13){
					temp = 5;
				}
				else if(midVals == 14){
					temp = 14;
				}
				else if(midVals == 15){
					temp = 9;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*1) - 1] = temp/8;
		temp = temp - (8*(temp/8));
		sBlockOutput[8*4 - (4*1) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*1) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*1) - 4] = temp/1;
		temp = temp - (1*(temp/1));

		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 2! temp = " + temp);
		}
	}

	public void s3Block(){
		/*
		S3	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	10	0	9	14	6	3	15	5	1	13	12	7	11	4	2	8
			0yyyy1	13	7	0	9	3	4	6	10	2	8	5	14	12	11	15	1
			1yyyy0	13	6	4	9	8	15	3	0	11	1	2	12	5	10	14	7
			1yyyy1	1	10	13	0	6	9	8	7	4	15	14	3	11	5	2	12
		 */
		int temp = 0;
		int midVals = (expansionBits[2][4]*8) + (expansionBits[2][3]*4) + 
						(expansionBits[2][2]*2) + (expansionBits[2][1]*1);
		switch(expansionBits[2][0]){
		case 0:
			switch(expansionBits[2][5]){
			case 0: 
				if(midVals == 0){
					temp = 10;
				}
				else if(midVals == 1){
					temp = 0;
				}
				else if(midVals == 2){
					temp = 9;
				}
				else if(midVals == 3){
					temp = 14;
				}
				else if(midVals == 4){
					temp = 6;
				}
				else if(midVals == 5){
					temp = 3;
				}
				else if(midVals == 6){
					temp = 15;
				}
				else if(midVals == 7){
					temp = 5;
				}
				else if(midVals == 8){
					temp = 1;
				}
				else if(midVals == 9){
					temp = 13;
				}
				else if(midVals == 10){
					temp = 12;
				}
				else if(midVals == 11){
					temp = 7;
				}
				else if(midVals == 12){
					temp = 11;
				}
				else if(midVals == 13){
					temp = 4;
				}
				else if(midVals == 14){
					temp = 2;
				}
				else if(midVals == 15){
					temp = 8;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 7;
				}
				else if(midVals == 2){
					temp = 0;
				}
				else if(midVals == 3){
					temp = 9;
				}
				else if(midVals == 4){
					temp = 3;
				}
				else if(midVals == 5){
					temp = 4;
				}
				else if(midVals == 6){
					temp = 6;
				}
				else if(midVals == 7){
					temp = 10;
				}
				else if(midVals == 8){
					temp = 2;
				}
				else if(midVals == 9){
					temp = 8;
				}
				else if(midVals == 10){
					temp = 5;
				}
				else if(midVals == 11){
					temp = 14;
				}
				else if(midVals == 12){
					temp = 12;
				}
				else if(midVals == 13){
					temp = 11;
				}
				else if(midVals == 14){
					temp = 15;
				}
				else if(midVals == 15){
					temp = 1;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[2][5]){
			case 0: 
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 6;
				}
				else if(midVals == 2){
					temp = 4;
				}
				else if(midVals == 3){
					temp = 9;
				}
				else if(midVals == 4){
					temp = 8;
				}
				else if(midVals == 5){
					temp = 15;
				}
				else if(midVals == 6){
					temp = 3;
				}
				else if(midVals == 7){
					temp = 0;
				}
				else if(midVals == 8){
					temp = 11;
				}
				else if(midVals == 9){
					temp = 1;
				}
				else if(midVals == 10){
					temp = 2;
				}
				else if(midVals == 11){
					temp = 12;
				}
				else if(midVals == 12){
					temp = 5;
				}
				else if(midVals == 13){
					temp = 10;
				}
				else if(midVals == 14){
					temp = 14;
				}
				else if(midVals == 15){
					temp = 7;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 1;
				}
				else if(midVals == 1){
					temp = 10;
				}
				else if(midVals == 2){
					temp = 13;
				}
				else if(midVals == 3){
					temp = 0;
				}
				else if(midVals == 4){
					temp = 6;
				}
				else if(midVals == 5){
					temp = 9;
				}
				else if(midVals == 6){
					temp = 8;
				}
				else if(midVals == 7){
					temp = 7;
				}
				else if(midVals == 8){
					temp = 4;
				}
				else if(midVals == 9){
					temp = 15;
				}
				else if(midVals == 10){
					temp = 14;
				}
				else if(midVals == 11){
					temp = 3;
				}
				else if(midVals == 12){
					temp = 11;
				}
				else if(midVals == 13){
					temp = 5;
				}
				else if(midVals == 14){
					temp = 2;
				}
				else if(midVals == 15){
					temp = 12;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*2) - 1] = temp/8;
		temp = temp - (8*(temp/8));

		sBlockOutput[8*4 - (4*2) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*2) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*2) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 3! temp = " + temp);
		}
	}
	
	public void s4Block(){
		/*
		S4	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	7	13	14	3	0	6	9	10	1	2	8	5	11	12	4	15
			0yyyy1	13	8	11	5	6	15	0	3	4	7	2	12	1	10	14	9
			1yyyy0	10	6	9	0	12	11	7	13	15	1	3	14	5	2	8	4
			1yyyy1	3	15	0	6	10	1	13	8	9	4	5	11	12	7	2	14
		 */
		int temp = 0;
		int midVals = (expansionBits[3][4]*8) + (expansionBits[3][3]*4) + 
						(expansionBits[3][2]*2) + (expansionBits[3][1]*1);
		switch(expansionBits[3][0]){
		case 0:
			switch(expansionBits[3][5]){
			case 0: 
				if(midVals == 0){
					temp = 7;
				}
				else if(midVals == 1){
					temp = 13;
				}
				else if(midVals == 2){
					temp = 14;
				}
				else if(midVals == 3){
					temp = 3;
				}
				else if(midVals == 4){
					temp = 0;
				}
				else if(midVals == 5){
					temp = 6;
				}
				else if(midVals == 6){
					temp = 9;
				}
				else if(midVals == 7){
					temp = 10;
				}
				else if(midVals == 8){
					temp = 1;
				}
				else if(midVals == 9){
					temp = 2;
				}
				else if(midVals == 10){
					temp = 8;
				}
				else if(midVals == 11){
					temp = 5;
				}
				else if(midVals == 12){
					temp = 11;
				}
				else if(midVals == 13){
					temp = 12;
				}
				else if(midVals == 14){
					temp = 4;
				}
				else if(midVals == 15){
					temp = 15;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 8;
				}
				else if(midVals == 2){
					temp = 11;
				}
				else if(midVals == 3){
					temp = 5;
				}
				else if(midVals == 4){
					temp = 6;
				}
				else if(midVals == 5){
					temp = 15;
				}
				else if(midVals == 6){
					temp = 0;
				}
				else if(midVals == 7){
					temp = 3;
				}
				else if(midVals == 8){
					temp = 4;
				}
				else if(midVals == 9){
					temp = 7;
				}
				else if(midVals == 10){
					temp = 2;
				}
				else if(midVals == 11){
					temp = 13;
				}
				else if(midVals == 12){
					temp = 1;
				}
				else if(midVals == 13){
					temp = 10;
				}
				else if(midVals == 14){
					temp = 14;
				}
				else if(midVals == 15){
					temp = 9;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[3][5]){
			case 0: 
				if(midVals == 0){
					temp = 10;
				}
				else if(midVals == 1){
					temp = 6;
				}
				else if(midVals == 2){
					temp = 9;
				}
				else if(midVals == 3){
					temp = 0;
				}
				else if(midVals == 4){
					temp = 12;
				}
				else if(midVals == 5){
					temp = 11;
				}
				else if(midVals == 6){
					temp = 7;
				}
				else if(midVals == 7){
					temp = 13;
				}
				else if(midVals == 8){
					temp = 15;
				}
				else if(midVals == 9){
					temp = 1;
				}
				else if(midVals == 10){
					temp = 3;
				}
				else if(midVals == 11){
					temp = 14;
				}
				else if(midVals == 12){
					temp = 5;
				}
				else if(midVals == 13){
					temp = 2;
				}
				else if(midVals == 14){
					temp = 8;
				}
				else if(midVals == 15){
					temp = 4;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 3;
				}
				else if(midVals == 1){
					temp = 15;
				}
				else if(midVals == 2){
					temp = 0;
				}
				else if(midVals == 3){
					temp = 6;
				}
				else if(midVals == 4){
					temp = 10;
				}
				else if(midVals == 5){
					temp = 1;
				}
				else if(midVals == 6){
					temp = 13;
				}
				else if(midVals == 7){
					temp = 8;
				}
				else if(midVals == 8){
					temp = 9;
				}
				else if(midVals == 9){
					temp = 4;
				}
				else if(midVals == 10){
					temp = 5;
				}
				else if(midVals == 11){
					temp = 11;
				}
				else if(midVals == 12){
					temp = 12;
				}
				else if(midVals == 13){
					temp = 7;
				}
				else if(midVals == 14){
					temp = 2;
				}
				else if(midVals == 15){
					temp = 14;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*3) - 1] = temp/8;
		temp = temp - (8*(temp/8));

		sBlockOutput[8*4 - (4*3) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*3) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*3) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 4! temp = " + temp);
		}
	}
	
	public void s5Block(){
		/*
		S5	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	2	12	4	1	7	10	11	6	8	5	3	15	13	0	14	9
			0yyyy1	14	11	2	12	4	7	13	1	5	0	15	10	3	9	8	6
			1yyyy0	4	2	1	11	10	13	7	8	15	9	12	5	6	3	0	14
			1yyyy1	11	8	12	7	1	14	2	13	6	15	0	9	10	4	5	3
		 */
		int temp = 0;
		int midVals = (expansionBits[4][4]*8) + (expansionBits[4][3]*4) + 
						(expansionBits[4][2]*2) + (expansionBits[4][1]*1);
		switch(expansionBits[4][0]){
		case 0:
			switch(expansionBits[4][5]){
			case 0: 
				if(midVals == 0){
					temp = 2;
				}
				else if(midVals == 1){
					temp = 12;
				}
				else if(midVals == 2){
					temp = 4;
				}
				else if(midVals == 3){
					temp = 1;
				}
				else if(midVals == 4){
					temp = 7;
				}
				else if(midVals == 5){
					temp = 10;
				}
				else if(midVals == 6){
					temp = 11;
				}
				else if(midVals == 7){
					temp = 6;
				}
				else if(midVals == 8){
					temp = 8;
				}
				else if(midVals == 9){
					temp = 5;
				}
				else if(midVals == 10){
					temp = 3;
				}
				else if(midVals == 11){
					temp = 15;
				}
				else if(midVals == 12){
					temp = 13;
				}
				else if(midVals == 13){
					temp = 0;
				}
				else if(midVals == 14){
					temp = 14;
				}
				else if(midVals == 15){
					temp = 9;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 14;
				}
				else if(midVals == 1){
					temp = 11;
				}
				else if(midVals == 2){
					temp = 2;
				}
				else if(midVals == 3){
					temp = 12;
				}
				else if(midVals == 4){
					temp = 4;
				}
				else if(midVals == 5){
					temp = 7;
				}
				else if(midVals == 6){
					temp = 13;
				}
				else if(midVals == 7){
					temp = 1;
				}
				else if(midVals == 8){
					temp = 5;
				}
				else if(midVals == 9){
					temp = 0;
				}
				else if(midVals == 10){
					temp = 15;
				}
				else if(midVals == 11){
					temp = 10;
				}
				else if(midVals == 12){
					temp = 3;
				}
				else if(midVals == 13){
					temp = 9;
				}
				else if(midVals == 14){
					temp = 8;
				}
				else if(midVals == 15){
					temp = 6;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[4][5]){
			case 0: 
				if(midVals == 0){
					temp = 4;
				}
				else if(midVals == 1){
					temp = 2;
				}
				else if(midVals == 2){
					temp = 1;
				}
				else if(midVals == 3){
					temp = 11;
				}
				else if(midVals == 4){
					temp = 10;
				}
				else if(midVals == 5){
					temp = 13;
				}
				else if(midVals == 6){
					temp = 7;
				}
				else if(midVals == 7){
					temp = 8;
				}
				else if(midVals == 8){
					temp = 15;
				}
				else if(midVals == 9){
					temp = 9;
				}
				else if(midVals == 10){
					temp = 12;
				}
				else if(midVals == 11){
					temp = 5;
				}
				else if(midVals == 12){
					temp = 6;
				}
				else if(midVals == 13){
					temp = 3;
				}
				else if(midVals == 14){
					temp = 0;
				}
				else if(midVals == 15){
					temp = 14;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 11;
				}
				else if(midVals == 1){
					temp = 8;
				}
				else if(midVals == 2){
					temp = 12;
				}
				else if(midVals == 3){
					temp = 7;
				}
				else if(midVals == 4){
					temp = 1;
				}
				else if(midVals == 5){
					temp = 14;
				}
				else if(midVals == 6){
					temp = 2;
				}
				else if(midVals == 7){
					temp = 13;
				}
				else if(midVals == 8){
					temp = 6;
				}
				else if(midVals == 9){
					temp = 15;
				}
				else if(midVals == 10){
					temp = 0;
				}
				else if(midVals == 11){
					temp = 9;
				}
				else if(midVals == 12){
					temp = 10;
				}
				else if(midVals == 13){
					temp = 4;
				}
				else if(midVals == 14){
					temp = 5;
				}
				else if(midVals == 15){
					temp = 3;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*4) - 1] = temp/8;
		temp = temp - (8*(temp/8));

		sBlockOutput[8*4 - (4*4) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*4) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*4) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 5! temp = " + temp);
		}
	}

	public void s6Block(){
		/*
		S6	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	12	1	10	15	9	2	6	8	0	13	3	4	14	7	5	11
			0yyyy1	10	15	4	2	7	12	9	5	6	1	13	14	0	11	3	8
			1yyyy0	9	14	15	5	2	8	12	3	7	0	4	10	1	13	11	6
			1yyyy1	4	3	2	12	9	5	15	10	11	14	1	7	6	0	8	13
		 */
		int temp = 0;
		int midVals = (expansionBits[5][4]*8) + (expansionBits[5][3]*4) + 
						(expansionBits[5][2]*2) + (expansionBits[5][1]*1);
		switch(expansionBits[5][0]){
		case 0:
			switch(expansionBits[5][5]){
			case 0: 
				if(midVals == 0){
					temp = 12;
				}
				else if(midVals == 1){
					temp = 1;
				}
				else if(midVals == 2){
					temp = 10;
				}
				else if(midVals == 3){
					temp = 15;
				}
				else if(midVals == 4){
					temp = 9;
				}
				else if(midVals == 5){
					temp = 2;
				}
				else if(midVals == 6){
					temp = 6;
				}
				else if(midVals == 7){
					temp = 8;
				}
				else if(midVals == 8){
					temp = 0;
				}
				else if(midVals == 9){
					temp = 13;
				}
				else if(midVals == 10){
					temp = 3;
				}
				else if(midVals == 11){
					temp = 4;
				}
				else if(midVals == 12){
					temp = 14;
				}
				else if(midVals == 13){
					temp = 7;
				}
				else if(midVals == 14){
					temp = 5;
				}
				else if(midVals == 15){
					temp = 11;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 10;
				}
				else if(midVals == 1){
					temp = 15;
				}
				else if(midVals == 2){
					temp = 4;
				}
				else if(midVals == 3){
					temp = 2;
				}
				else if(midVals == 4){
					temp = 7;
				}
				else if(midVals == 5){
					temp = 12;
				}
				else if(midVals == 6){
					temp = 9;
				}
				else if(midVals == 7){
					temp = 5;
				}
				else if(midVals == 8){
					temp = 6;
				}
				else if(midVals == 9){
					temp = 1;
				}
				else if(midVals == 10){
					temp = 13;
				}
				else if(midVals == 11){
					temp = 14;
				}
				else if(midVals == 12){
					temp = 0;
				}
				else if(midVals == 13){
					temp = 11;
				}
				else if(midVals == 14){
					temp = 3;
				}
				else if(midVals == 15){
					temp = 8;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[5][5]){
			case 0: 
				if(midVals == 0){
					temp = 9;
				}
				else if(midVals == 1){
					temp = 14;
				}
				else if(midVals == 2){
					temp = 15;
				}
				else if(midVals == 3){
					temp = 5;
				}
				else if(midVals == 4){
					temp = 2;
				}
				else if(midVals == 5){
					temp = 8;
				}
				else if(midVals == 6){
					temp = 12;
				}
				else if(midVals == 7){
					temp = 3;
				}
				else if(midVals == 8){
					temp = 7;
				}
				else if(midVals == 9){
					temp = 0;
				}
				else if(midVals == 10){
					temp = 4;
				}
				else if(midVals == 11){
					temp = 10;
				}
				else if(midVals == 12){
					temp = 1;
				}
				else if(midVals == 13){
					temp = 13;
				}
				else if(midVals == 14){
					temp = 11;
				}
				else if(midVals == 15){
					temp = 6;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 4;
				}
				else if(midVals == 1){
					temp = 3;
				}
				else if(midVals == 2){
					temp = 2;
				}
				else if(midVals == 3){
					temp = 12;
				}
				else if(midVals == 4){
					temp = 9;
				}
				else if(midVals == 5){
					temp = 5;
				}
				else if(midVals == 6){
					temp = 15;
				}
				else if(midVals == 7){
					temp = 10;
				}
				else if(midVals == 8){
					temp = 11;
				}
				else if(midVals == 9){
					temp = 14;
				}
				else if(midVals == 10){
					temp = 1;
				}
				else if(midVals == 11){
					temp = 7;
				}
				else if(midVals == 12){
					temp = 6;
				}
				else if(midVals == 13){
					temp = 0;
				}
				else if(midVals == 14){
					temp = 8;
				}
				else if(midVals == 15){
					temp = 13;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*5) - 1] = temp/8;
		temp = temp - (8*(temp/8));

		sBlockOutput[8*4 - (4*5) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*5) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*5) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 6! temp = " + temp);
		}
	}

	public void s7Block(){
		/*
		S7	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	4	11	2	14	15	0	8	13	3	12	9	7	5	10	6	1
			0yyyy1	13	0	11	7	4	9	1	10	14	3	5	12	2	15	8	6
			1yyyy0	1	4	11	13	12	3	7	14	10	15	6	8	0	5	9	2
			1yyyy1	6	11	13	8	1	4	10	7	9	5	0	15	14	2	3	12
		 */
		int temp = 0;
		int midVals = (expansionBits[6][4]*8) + (expansionBits[6][3]*4) + 
						(expansionBits[6][2]*2) + (expansionBits[6][1]*1);
		switch(expansionBits[6][0]){
		case 0:
			switch(expansionBits[6][5]){
			case 0: 
				if(midVals == 0){
					temp = 4;
				}
				else if(midVals == 1){
					temp = 11;
				}
				else if(midVals == 2){
					temp = 2;
				}
				else if(midVals == 3){
					temp = 14;
				}
				else if(midVals == 4){
					temp = 15;
				}
				else if(midVals == 5){
					temp = 0;
				}
				else if(midVals == 6){
					temp = 8;
				}
				else if(midVals == 7){
					temp = 13;
				}
				else if(midVals == 8){
					temp = 3;
				}
				else if(midVals == 9){
					temp = 12;
				}
				else if(midVals == 10){
					temp = 9;
				}
				else if(midVals == 11){
					temp = 7;
				}
				else if(midVals == 12){
					temp = 5;
				}
				else if(midVals == 13){
					temp = 10;
				}
				else if(midVals == 14){
					temp = 6;
				}
				else if(midVals == 15){
					temp = 1;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 0;
				}
				else if(midVals == 2){
					temp = 11;
				}
				else if(midVals == 3){
					temp = 7;
				}
				else if(midVals == 4){
					temp = 4;
				}
				else if(midVals == 5){
					temp = 9;
				}
				else if(midVals == 6){
					temp = 1;
				}
				else if(midVals == 7){
					temp = 10;
				}
				else if(midVals == 8){
					temp = 14;
				}
				else if(midVals == 9){
					temp = 3;
				}
				else if(midVals == 10){
					temp = 5;
				}
				else if(midVals == 11){
					temp = 12;
				}
				else if(midVals == 12){
					temp = 2;
				}
				else if(midVals == 13){
					temp = 15;
				}
				else if(midVals == 14){
					temp = 8;
				}
				else if(midVals == 15){
					temp = 6;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[6][5]){
			case 0: 
				if(midVals == 0){
					temp = 1;
				}
				else if(midVals == 1){
					temp = 4;
				}
				else if(midVals == 2){
					temp = 11;
				}
				else if(midVals == 3){
					temp = 13;
				}
				else if(midVals == 4){
					temp = 12;
				}
				else if(midVals == 5){
					temp = 3;
				}
				else if(midVals == 6){
					temp = 7;
				}
				else if(midVals == 7){
					temp = 14;
				}
				else if(midVals == 8){
					temp = 10;
				}
				else if(midVals == 9){
					temp = 15;
				}
				else if(midVals == 10){
					temp = 6;
				}
				else if(midVals == 11){
					temp = 8;
				}
				else if(midVals == 12){
					temp = 0;
				}
				else if(midVals == 13){
					temp = 5;
				}
				else if(midVals == 14){
					temp = 9;
				}
				else if(midVals == 15){
					temp = 2;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 6;
				}
				else if(midVals == 1){
					temp = 11;
				}
				else if(midVals == 2){
					temp = 13;
				}
				else if(midVals == 3){
					temp = 8;
				}
				else if(midVals == 4){
					temp = 1;
				}
				else if(midVals == 5){
					temp = 4;
				}
				else if(midVals == 6){
					temp = 10;
				}
				else if(midVals == 7){
					temp = 7;
				}
				else if(midVals == 8){
					temp = 9;
				}
				else if(midVals == 9){
					temp = 5;
				}
				else if(midVals == 10){
					temp = 0;
				}
				else if(midVals == 11){
					temp = 15;
				}
				else if(midVals == 12){
					temp = 14;
				}
				else if(midVals == 13){
					temp = 2;
				}
				else if(midVals == 14){
					temp = 3;
				}
				else if(midVals == 15){
					temp = 12;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*6) - 1] = temp/8;
		temp = temp - (8*(temp/8));

		sBlockOutput[8*4 - (4*6) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*6) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*6) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 7! temp = " + temp);
		}
	}

	public void s8Block(){
		/*
		S8	x0000x	x0001x	x0010x	x0011x	x0100x	x0101x	x0110x	x0111x	x1000x	x1001x	x1010x	x1011x	x1100x	x1101x	x1110x	x1111x
			0yyyy0	13	2	8	4	6	15	11	1	10	9	3	14	5	0	12	7
			0yyyy1	1	15	13	8	10	3	7	4	12	5	6	11	0	14	9	2
			1yyyy0	7	11	4	1	9	12	14	2	0	6	10	13	15	3	5	8
			1yyyy1	2	1	14	7	4	10	8	13	15	12	9	0	3	5	6	11

		 */
		int temp = 0;
		int midVals = (expansionBits[7][4]*8) + (expansionBits[7][3]*4) + 
						(expansionBits[7][2]*2) + (expansionBits[7][1]*1);
		switch(expansionBits[7][0]){
		case 0:
			switch(expansionBits[7][5]){
			case 0: 
				if(midVals == 0){
					temp = 13;
				}
				else if(midVals == 1){
					temp = 2;
				}
				else if(midVals == 2){
					temp = 8;
				}
				else if(midVals == 3){
					temp = 4;
				}
				else if(midVals == 4){
					temp = 6;
				}
				else if(midVals == 5){
					temp = 15;
				}
				else if(midVals == 6){
					temp = 11;
				}
				else if(midVals == 7){
					temp = 1;
				}
				else if(midVals == 8){
					temp = 10;
				}
				else if(midVals == 9){
					temp = 9;
				}
				else if(midVals == 10){
					temp = 3;
				}
				else if(midVals == 11){
					temp = 14;
				}
				else if(midVals == 12){
					temp = 5;
				}
				else if(midVals == 13){
					temp = 0;
				}
				else if(midVals == 14){
					temp = 12;
				}
				else if(midVals == 15){
					temp = 7;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 1;
				}
				else if(midVals == 1){
					temp = 15;
				}
				else if(midVals == 2){
					temp = 13;
				}
				else if(midVals == 3){
					temp = 8;
				}
				else if(midVals == 4){
					temp = 10;
				}
				else if(midVals == 5){
					temp = 3;
				}
				else if(midVals == 6){
					temp = 7;
				}
				else if(midVals == 7){
					temp = 4;
				}
				else if(midVals == 8){
					temp = 12;
				}
				else if(midVals == 9){
					temp = 5;
				}
				else if(midVals == 10){
					temp = 6;
				}
				else if(midVals == 11){
					temp = 11;
				}
				else if(midVals == 12){
					temp = 0;
				}
				else if(midVals == 13){
					temp = 14;
				}
				else if(midVals == 14){
					temp = 9;
				}
				else if(midVals == 15){
					temp = 2;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
			}
			break;
		}
		break;
			
		case 1:
			switch(expansionBits[7][5]){
			case 0: 
				if(midVals == 0){
					temp = 7;
				}
				else if(midVals == 1){
					temp = 11;
				}
				else if(midVals == 2){
					temp = 4;
				}
				else if(midVals == 3){
					temp = 1;
				}
				else if(midVals == 4){
					temp = 9;
				}
				else if(midVals == 5){
					temp = 12;
				}
				else if(midVals == 6){
					temp = 14;
				}
				else if(midVals == 7){
					temp = 2;
				}
				else if(midVals == 8){
					temp = 0;
				}
				else if(midVals == 9){
					temp = 6;
				}
				else if(midVals == 10){
					temp = 10;
				}
				else if(midVals == 11){
					temp = 13;
				}
				else if(midVals == 12){
					temp = 15;
				}
				else if(midVals == 13){
					temp = 3;
				}
				else if(midVals == 14){
					temp = 5;
				}
				else if(midVals == 15){
					temp = 8;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
		
			case 1:
				if(midVals == 0){
					temp = 2;
				}
				else if(midVals == 1){
					temp = 1;
				}
				else if(midVals == 2){
					temp = 14;
				}
				else if(midVals == 3){
					temp = 7;
				}
				else if(midVals == 4){
					temp = 4;
				}
				else if(midVals == 5){
					temp = 10;
				}
				else if(midVals == 6){
					temp = 8;
				}
				else if(midVals == 7){
					temp = 13;
				}
				else if(midVals == 8){
					temp = 15;
				}
				else if(midVals == 9){
					temp = 12;
				}
				else if(midVals == 10){
					temp = 9;
				}
				else if(midVals == 11){
					temp = 0;
				}
				else if(midVals == 12){
					temp = 3;
				}
				else if(midVals == 13){
					temp = 5;
				}
				else if(midVals == 14){
					temp = 6;
				}
				else if(midVals == 15){
					temp = 11;
				}
				else{
					System.out.println("ERROR! midVals should not exceed 15! midVals = " + midVals);
				}
				break;
			}
			break;
		}
		
		sBlockOutput[8*4 - (4*7) - 1] = temp/8;
		temp = temp - (8*(temp/8));
		sBlockOutput[8*4 - (4*7) - 2] = temp/4;
		temp = temp - (4*(temp/4));
		sBlockOutput[8*4 - (4*7) - 3] = temp/2;
		temp = temp - (2*(temp/2));
		sBlockOutput[8*4 - (4*7) - 4] = temp/1;
		temp = temp - (1*(temp/1));
		
		if(temp != 0){
			System.out.println("ERROR! temp should equal 0 after output of S block 8! temp = " + temp);
		}
	}

	public void postSBlockPerm(){
		/*
		 * 16	7	20	21	29	12	28	17
			1	15	23	26	5	18	31	10
			2	8	24	14	32	27	3	9
		   19	13	30	6	22	11	4	25
		 */
		fOutput[0] = sBlockOutput[15];
		fOutput[1] = sBlockOutput[6];
		fOutput[2] = sBlockOutput[19];
		fOutput[3] = sBlockOutput[20];
		fOutput[4] = sBlockOutput[28];
		fOutput[5] = sBlockOutput[11];
		fOutput[6] = sBlockOutput[27];
		fOutput[7] = sBlockOutput[16];
		fOutput[8] = sBlockOutput[0];
		fOutput[9] = sBlockOutput[14];
		fOutput[10] = sBlockOutput[22];
		fOutput[11] = sBlockOutput[25];
		fOutput[12] = sBlockOutput[4];
		fOutput[13] = sBlockOutput[17];
		fOutput[14] = sBlockOutput[30];
		fOutput[15] = sBlockOutput[9];
		fOutput[16] = sBlockOutput[1];
		fOutput[17] = sBlockOutput[7];
		fOutput[18] = sBlockOutput[23];
		fOutput[19] = sBlockOutput[13];
		fOutput[20] = sBlockOutput[31];
		fOutput[21] = sBlockOutput[26];
		fOutput[22] = sBlockOutput[2];
		fOutput[23] = sBlockOutput[8];
		fOutput[24] = sBlockOutput[18];
		fOutput[25] = sBlockOutput[12];
		fOutput[26] = sBlockOutput[29];
		fOutput[27] = sBlockOutput[5];
		fOutput[28] = sBlockOutput[21];
		fOutput[29] = sBlockOutput[10];
		fOutput[30] = sBlockOutput[3];
		fOutput[31] = sBlockOutput[24];
	
	}
	
}
