package des;

public class permutation {

	public static int[] initialPermutation(int[] inputText){
		/*
		 * 58	50	42	34	26	18	10	2
		 * 60	52	44	36	28	20	12	4 
		 * 62	54	46	38	30	22	14	6
		 * 64	56	48	40	32	24	16	8
 		 * 57	49	41	33	25	17	9	1
		 * 59	51	43	35	27	19	11	3
		 * 61	53	45	37	29	21	13	5
		 * 63	55	47	39	31	23	15	7
		 */
		int[] permutedText = new int[64];
		
		for(int i = 0; i < 8; i++){
			if(i < 4){
				permutedText[i*8 + 0] = inputText[57 + i*2];
				permutedText[i*8 + 1] = inputText[49 + i*2];
				permutedText[i*8 + 2] = inputText[41 + i*2];
				permutedText[i*8 + 3] = inputText[33 + i*2];
				permutedText[i*8 + 4] = inputText[25 + i*2];
				permutedText[i*8 + 5] = inputText[17 + i*2];
				permutedText[i*8 + 6] = inputText[ 9 + i*2];
				permutedText[i*8 + 7] = inputText[ 1 + i*2];
			}
			else{
				permutedText[i*8 + 0] = inputText[56 + (i-4)*2];
				permutedText[i*8 + 1] = inputText[48 + (i-4)*2];
				permutedText[i*8 + 2] = inputText[40 + (i-4)*2];
				permutedText[i*8 + 3] = inputText[32 + (i-4)*2];
				permutedText[i*8 + 4] = inputText[24 + (i-4)*2];
				permutedText[i*8 + 5] = inputText[16 + (i-4)*2];
				permutedText[i*8 + 6] = inputText[ 8 + (i-4)*2];
				permutedText[i*8 + 7] = inputText[ 0 + (i-4)*2];
			}
		}
		return permutedText;
	}
	
	public static int[] finalPermutation(int[] inputText){
		/*
		 * 40	8	48	16	56	24	64	32
		 * 39	7	47	15	55	23	63	31
		 * 38	6	46	14	54	22	62	30
		 * 37	5	45	13	53	21	61	29
		 * 36	4	44	12	52	20	60	28
		 * 35	3	43	11	51	19	59	27
		 * 34	2	42	10	50	18	58	26
		 * 33	1	41	9	49	17	57	25
		 */
		int[] permutedText = new int[64];
		for(int i = 0; i < 8; i++){
			permutedText[i*8 + 0] = inputText[39 - i];
			permutedText[i*8 + 1] = inputText[ 7 - i];
			permutedText[i*8 + 2] = inputText[47 - i];
			permutedText[i*8 + 3] = inputText[15 - i];
			permutedText[i*8 + 4] = inputText[55 - i];
			permutedText[i*8 + 5] = inputText[23 - i];
			permutedText[i*8 + 6] = inputText[63 - i];
			permutedText[i*8 + 7] = inputText[31 - i];
		}
		return permutedText;
	}
}
