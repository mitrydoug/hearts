import java.util.*;

public class Test{
    public static void main(String[] args){
        ArrayList<Card> cards = new ArrayList<Card>();
        Card c1 = new Card(Card.TWO, Card.CLUBS);
        Card c2 = new Card(Card.TWO, Card.CLUBS);
        cards.add(c1);
        System.out.println(cards.contains(c2) + " " + c1.equals(c2));
    }
}