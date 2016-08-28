import java.util.ArrayList;

public class RandomAI{

	public static Card[] getRandomPass(ArrayList<Card> hand){
		Card c1 = hand.get((int)(Math.random()*13));
		Card c2 = hand.get((int)(Math.random()*13));
		while(c2.equals(c1)){
			c2 = hand.get((int)(Math.random()*13));
		}
		Card c3 = hand.get((int)(Math.random()*13));
		while(c3.equals(c1) || c3.equals(c2)){
			c3 = hand.get((int)(Math.random()*13));
		}
		Card[] pass = {c1, c2, c3};
		return pass;
	}

	public static Card getRandomMove(ArrayList<Card> validMoves){
		return validMoves.get((int)(Math.random()*validMoves.size()));
	}
}