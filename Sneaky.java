import java.util.ArrayList;

class Sneaky extends HeartsGameAI{
    public Sneaky(int pos){
        super(0);
    }
    public void initRound(ArrayList<Card> hand){}
    public Card[] getPass(){
        Card[] pass = new Card[3];
        pass[0] = new Card(Card.QUEEN, Card.SPADES);
        pass[1] = new Card(Card.QUEEN, Card.SPADES);
        pass[2] = new Card(Card.QUEEN, Card.SPADES);
        return pass;
    }
    public void recievePass(Card[] pass){}
    public Card getMove(){
        return new Card(Card.QUEEN, Card.SPADES);
    }
    public void endTrick(){}
}