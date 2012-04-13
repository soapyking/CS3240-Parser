
public class Driver {

  public static void main(String[] args) {
    System.out.println("Working");
    Token t = new Token("tasty", "Nestea", "keyword");

    System.out.println(t.name);
    System.out.println(t.attribute);
    System.out.println(t.type);

  }
}
