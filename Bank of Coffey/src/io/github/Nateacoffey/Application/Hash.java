package io.github.Nateacoffey.Application;

public class Hash {
	public String hashString(String name) {	//hashes password into string
		
		int[][] matrix = {	{19, 36, 18},
							{0, 6, 3},
							{33, 4, 2}};
		String result = "";
		
		
		if(name.length() % 3 == 1) {
			name += "..";
		}else if(name.length() % 3 == 2) {
			name += ".";
		}
		
		int temp = 0;
		
		for(int i = 0; i < name.length(); i += 3) {//skips every 3rd char
			for(int j = 0; j < 3; j++) {	//rows
				for(int k = 0; k < 3; k++) {	//columns
					temp += name.charAt(i) * matrix[k][j]
							+ name.charAt(i + 1) * matrix[k][j]
								+ name.charAt(i + 2) * matrix[k][j];
				}//end for loop
				
				temp = (temp % 93) + 33;

				result += (char) temp;

				temp = 0;
			}//end for loop
		}//end for loop
		
		
		return result;
	}
}
