
public class keyMixing {

	public static int[] originalKey = new int[64];
	private static int[] modifiedKey = new int[56];
	
	public static void PC1(){
		PC1Left(originalKey);
		PC1Right(originalKey);
	}
	
	public static void PC1Left(int[] key){
		/* 	Left
		57	49	41	33	25	17	9
		1	58	50	42	34	26	18
		10	2	59	51	43	35	27
		19	11	3	60	52	44	36
		 */
		int[] leftKey = new int[28];
		leftKey[0] 	= key[56]; 	leftKey[1] 	= key[48]; 	leftKey[2] 	= key[40]; 	leftKey[3] 	= key[32]; 	
		leftKey[4] 	= key[24]; 	leftKey[5] 	= key[16];	leftKey[6] 	= key[8]; 	leftKey[7] 	= key[0]; 	
		leftKey[8] 	= key[57]; 	leftKey[9] 	= key[49]; 	leftKey[10] = key[41]; 	leftKey[11] = key[33];
		leftKey[12] = key[25]; 	leftKey[13] = key[17]; 	leftKey[14] = key[9];	leftKey[15] = key[1]; 	
		leftKey[16] = key[58]; 	leftKey[17] = key[50];	leftKey[18] = key[42]; 	leftKey[19] = key[34]; 	
		leftKey[20] = key[26];	leftKey[21] = key[18]; 	leftKey[22] = key[10]; 	leftKey[23] = key[2];
		leftKey[24] = key[59]; 	leftKey[25] = key[51]; 	leftKey[26] = key[43];	leftKey[27] = key[35];
		for(int i = 0; i < 28; i++){
			modifiedKey[i] = leftKey[i];
		}
	}
	
	public static void PC1Right(int[] key){
		/*
		Right
		63	55	47	39	
		31	23	15  7	
		62	54	46	38	
		30	22  14	6	
		61	53	45	37	
		29  21	13	5	
		28	20	12	4
		 */
		int[] rightKey = new int[28];
		rightKey[0] 	= key[62]; 	rightKey[1] 	= key[54]; 	rightKey[2] 	= key[46]; 	rightKey[3] 	= key[38]; 	
		rightKey[4] 	= key[30]; 	rightKey[5] 	= key[22];	rightKey[6] 	= key[14]; 	rightKey[7] 	= key[6]; 	
		rightKey[8] 	= key[61]; 	rightKey[9] 	= key[53]; 	rightKey[10] 	= key[45]; 	rightKey[11] 	= key[37];
		rightKey[12] 	= key[29]; 	rightKey[13] 	= key[21]; 	rightKey[14] 	= key[13];	rightKey[15] 	= key[5]; 	
		rightKey[16] 	= key[60]; 	rightKey[17] 	= key[52];	rightKey[18] 	= key[44]; 	rightKey[19] 	= key[36]; 	
		rightKey[20] 	= key[28];	rightKey[21] 	= key[20]; 	rightKey[22] 	= key[12]; 	rightKey[23] 	= key[4];
		rightKey[24] 	= key[27]; 	rightKey[25] 	= key[19]; 	rightKey[26] 	= key[11];	rightKey[27] 	= key[3];
		for(int i = 0; i < 28; i++){
			modifiedKey[i + 28] = rightKey[i];
		}
	}

	public static int[] PC2(){
		int[] subKey = new int[48];
		/*	14	17	11	
		 * 	24	1	5
			3	28	15	
			6	21	10
			23	19	12	
			4	26	8
			16	7	27	
			20	13	2
			41	52	31	
			37	47	55
			30	40	51	
			45	33	48
			44	49	39	
			56	34	53
			46	42	50	
			36	29	32
			*/
		subKey[0] 	= modifiedKey[13]; 	subKey[1] 	= modifiedKey[16]; 	subKey[2]	= modifiedKey[10];
		subKey[3] 	= modifiedKey[23]; 	subKey[4] 	= modifiedKey[0]; 	subKey[5] 	= modifiedKey[6];
		subKey[6] 	= modifiedKey[2]; 	subKey[7] 	= modifiedKey[27]; 	subKey[8] 	= modifiedKey[14];
		subKey[9] 	= modifiedKey[5]; 	subKey[10] 	= modifiedKey[20]; 	subKey[11] 	= modifiedKey[9];
		subKey[12] 	= modifiedKey[22]; 	subKey[13] 	= modifiedKey[18]; 	subKey[14] 	= modifiedKey[11];
		subKey[15] 	= modifiedKey[3]; 	subKey[16] 	= modifiedKey[25]; 	subKey[17] 	= modifiedKey[7];
		subKey[18] 	= modifiedKey[15]; 	subKey[19] 	= modifiedKey[6]; 	subKey[20] 	= modifiedKey[26];
		subKey[21] 	= modifiedKey[19]; 	subKey[22] 	= modifiedKey[12]; 	subKey[23] 	= modifiedKey[1];
		subKey[24] 	= modifiedKey[40]; 	subKey[25] 	= modifiedKey[51]; 	subKey[26] 	= modifiedKey[30];
		subKey[27] 	= modifiedKey[36]; 	subKey[28] 	= modifiedKey[46]; 	subKey[29] 	= modifiedKey[54];
		subKey[30] 	= modifiedKey[29]; 	subKey[31] 	= modifiedKey[39]; 	subKey[32] 	= modifiedKey[50];
		subKey[33] 	= modifiedKey[44]; 	subKey[34] 	= modifiedKey[32]; 	subKey[35] 	= modifiedKey[47];
		subKey[36] 	= modifiedKey[43]; 	subKey[37] 	= modifiedKey[48]; 	subKey[38] 	= modifiedKey[38];
		subKey[39] 	= modifiedKey[55]; 	subKey[40] 	= modifiedKey[33]; 	subKey[41] 	= modifiedKey[52];
		subKey[42] 	= modifiedKey[45]; 	subKey[43] 	= modifiedKey[41]; 	subKey[44] 	= modifiedKey[49];
		subKey[45] 	= modifiedKey[35]; 	subKey[46] 	= modifiedKey[28]; 	subKey[47] 	= modifiedKey[31];
		return subKey;
	}
	
	public static void rotateKey(int round){
		/*Round number	Number of left rotations
					1	1
					2	1
					3	2
					4	2
					5	2
					6	2
					7	2
					8	2
					9	1
					10	2
					11	2
					12	2
					13	2
					14	2
					15	2
					16	1
		*/
		
		int[] rotatedLeft = new int[28];
		int[] rotatedRight = new int[28];
		for(int i = 0; i < rotatedLeft.length; i++){
			rotatedLeft[i] = modifiedKey[i];
			rotatedRight[i] = modifiedKey[i + 28];
		}
		int iter = 2; 
		if (round == 1 | round == 2 | round == 9 | round == 16){
			//rotate left once
			iter = 1;
		}
		
		for(int k = 0; k < iter; k++){
			for(int i = 0; i < 28; i++){
				if(i == 27){
					modifiedKey[i] = rotatedLeft[0];
					modifiedKey[i + 28] = rotatedRight[0];
				}
				else{
					modifiedKey[i] = rotatedLeft[i + 1];
					modifiedKey[i + 28] = rotatedRight[i + 1];
				}
			}

		}
	}
	
}
