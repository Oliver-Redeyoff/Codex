public class node{
  private String name;
  private int address;

  public node(String _name, int _address){
    name = _name;
    address = _address;
  }

  public String getName(){
    return name;
  }

  public int getAddress(){
    return address;
  }

  public void setAddress(int newAddress){
    address = newAddress;
  }
}
