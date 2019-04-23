import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class bubble{

	public ArrayList<String> names = new ArrayList<String>();
	//public ArrayList<ArrayList<String>> namesNum = new ArrayList<ArrayList<String>>();
	public char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	public void bubbleSort() {

		String line;
		boolean sorted = false;

		try {
			File file = new File("Names/names.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				prepareData(line);
			}
		} catch (Exception e) {
			System.out.println("oh no");
		}

		while(sorted == false) {

			boolean noSwaps = true;
			boolean compared;
			int counter;

			for(int i=0 ; i<names.size()-1 ; i++) {

				compared = false;
				counter = 0;

				while(compared == false) {

					if(counter >= names.get(i).length()-1) {
						 compared = true;
					}
					else if(counter >= names.get(i+1).length()-1) {
						noSwaps = false;
						swapNames(i, i+1);
						compared = true;
					}
					else if( indexOfLetter(names.get(i).charAt(counter)) > indexOfLetter(names.get(i+1).charAt(counter)) ) {
						noSwaps = false;
						swapNames(i, i+1);
						compared = true;
					}
					else if( indexOfLetter(names.get(i).charAt(counter)) < indexOfLetter(names.get(i+1).charAt(counter)) ) {
						compared = true;
					}
					counter += 1;

				}

			}

			if(noSwaps) {
				sorted = true;
			}

		}

		System.out.println(names);

	}


	private void prepareData(String data){

		int index = 0;
		String name;

		for(int i =0 ; i<data.length() ; i++) {

			index = i;
			name = "";

			if(data.charAt(i) != '"' && data.charAt(i) != ','){

				while(data.charAt(index) != '"' && data.charAt(index) != ',') {
					index += 1;
				}

				name = data.substring(i, index);

				names.add(name);
				i = index;

			}
		}
	}

	private int indexOfLetter(char letter) {
		for(int i=0 ; i<alphabet.length ; i++) {
			if(letter == alphabet[i]) {
				return i;
			}
		}
		return -1;
	}

	private void swapNames(int index1, int index2) {
		String name1 = names.get(index1);
		names.set(index1, names.get(index2));
		names.set(index2, name1);
	}

	public static void main(String[] args){

		new bubble().bubbleSort();

	}

}
