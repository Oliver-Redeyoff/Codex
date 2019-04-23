import java.io.*;
import java.util.ArrayList;

public class CW2Q4{

  static ArrayList<node> nodes = new ArrayList<node>();
  String lastElement = "";
  String firstElement = "";
  int counter = 0;

  public void start(){

    String line;

    try {
			File file = new File("Names/names.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				prepareData(line);
			}
		} catch (Exception e) {
			System.out.println("oh no");
		}

    // test out my code bellow this comment

    printList();

  }

  private void prepareData(String data){

		int index = 0;
		String name;

		for(int i=0 ; i<data.length() ; i++) {

			index = i;
			name = "";

			if(data.charAt(i) != '"' && data.charAt(i) != ','){

				while(data.charAt(index) != '"' && data.charAt(index) != ',') {
					index += 1;
				}

				name = data.substring(i, index);

        if(counter == 0){
          firstElement = name;
          nodes.add(new node(name, 1));
        } else {
          nodes.add(new node(name, (counter-1)^(counter+1) ));
          // System.out.println((counter-1) + "," +  (counter+1)  + " + " + nodes.get(counter).getName());
        }

        counter += 1;
				i = index;

			}
		}
    // set counter to index of last node
    counter -= 1;

    // set link of last element to before last element only
    lastElement = nodes.get(counter).getName();
    nodes.get(counter).setAddress( (counter-1) );
	}

  private void printList(){
    int address = 0;
    int nextIndex = 1;
    int index = 0;
    int previousIndex = 0;
    while(1==1){
      System.out.println(index + " : " + nodes.get(index).getName());
      address = nodes.get(nextIndex).getAddress();
      previousIndex = index;
      index = nextIndex;
      nextIndex = address^previousIndex;
      if(index == 0){
        break;
      }
    }
  }

  private int find(String name){
    int address = 0;
    int nextIndex = 1;
    int index = 0;
    int previousIndex = 0;
    while(1==1){
      if(nodes.get(index).getName().equals(name)){
        return index;
      }
      address = nodes.get(nextIndex).getAddress();
      previousIndex = index;
      index = nextIndex;
      nextIndex = address^previousIndex;
      if(index == 0){
        break;
      }
    }
    return -1;
  }

  private int findPrevious(String name){
    int address = 0;
    int nextIndex = 1;
    int index = 0;
    int previousIndex = 0;
    while(1==1){
      if(nodes.get(index).getName().equals(name)){
        return previousIndex;
      }
      address = nodes.get(nextIndex).getAddress();
      previousIndex = index;
      index = nextIndex;
      nextIndex = address^previousIndex;
      if(index == 0){
        break;
      }
    }
    return -1;
  }

  private int findNext(String name){
    int address = 0;
    int nextIndex = 1;
    int index = 0;
    int previousIndex = 0;
    while(1==1){
      if(nodes.get(index).getName().equals(name)){
        return nextIndex;
      }
      address = nodes.get(nextIndex).getAddress();
      previousIndex = index;
      index = nextIndex;
      nextIndex = address^previousIndex;
      if(index == 0){
        break;
      }
    }
    return -1;
  }

  private void insertAfter(String after, String newObj){
    counter += 1;
    int previousIndex = findPrevious(after);
    int index = find(after);
    int nextIndex = findNext(after);
    // System.out.println(nextIndex);
    // create new node with XOR link between current node and node that was after
    nodes.add(new node(newObj, index^nextIndex));
    // change XOR link of node to link between current node and new node
    nodes.get(index).setAddress(previousIndex^counter);
    // change XOR link of next node to link between new node and the one after it
    nodes.get(nextIndex).setAddress(counter^(nodes.get(nextIndex).getAddress()^index));

  }

  private void insertBefore(String before, String newObj){
    counter += 1;
    int previousIndex = findPrevious(before);
    int index = find(before);
    int nextIndex = findNext(before);
    // create new node with XOR link between previous node and current node
    nodes.add(new node(newObj, previousIndex^index));
    // change XOR link of current node to be between new node a next node node
    nodes.get(index).setAddress(counter^nextIndex);
    // change XOR link of previous node to be between new node and one before it
    nodes.get(previousIndex).setAddress( (nodes.get(previousIndex).getAddress()^index)^counter );
  }

  private String removeAfter(String after){
    int previousIndex = findPrevious(after);
    int index = find(after);
    int nextIndex = findNext(after);
    int nextNextIndex = nodes.get(nextIndex).getAddress()^index;

    String name = nodes.get(nextIndex).getName();

    // set XOR link of current node to be between previous node and next next node
    nodes.get(index).setAddress(previousIndex^nextNextIndex);
    // set XOR link of next next node to be inbetween current node and one after it
    nodes.get(nextNextIndex).setAddress(index^(nodes.get(nextNextIndex).getAddress()^nextIndex) );

    return name;
  }

  private String removeBefore(String before){
    int previousIndex = findPrevious(before);
    int index = find(before);
    int nextIndex = findNext(before);
    int previousPreviousIndex = nodes.get(previousIndex).getAddress()^index;

    String name = nodes.get(previousIndex).getName();

    // set XOR link of previous previous node to be between one before it amd the current one
    nodes.get(previousPreviousIndex).setAddress((nodes.get(previousPreviousIndex).getAddress()^previousIndex)^index);
    // set XOR link of current node to be between the previous previous node and the next one
    nodes.get(index).setAddress(previousPreviousIndex^nextIndex);

    return name;
  }

  public static void main(String[] args){
    new CW2Q4().start();
  }

}
