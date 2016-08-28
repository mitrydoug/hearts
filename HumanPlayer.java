import java.util.Scanner;
import java.util.ArrayList;

public class HumanPlayer extends HeartsGameAI{

	ArrayList<Card> hand;
	Scanner scan;

	public HumanPlayer(int pos){
		super(pos);
		scan = new Scanner(System.in);
	}

	public void initRound(ArrayList<Card> h){
		hand = h;
	}

	public void recievePass(Card[] pass){}

	public void endTrick(){}

	public Card[] getPass(){

		Card[] pass = new Card[3];

		int index = 0;
		String rep;
		int suit;
		int value;
		while(index < 3){
			System.out.print("Enter Card " + (index+1) + ": ");
			rep = scan.nextLine();

			switch(rep.charAt(0)){
				case '2':
					value = Card.TWO; break;
				case '3':
					value = Card.THREE; break;
				case '4':
					value = Card.FOUR; break;
				case '5':
					value = Card.FIVE; break;
				case '6':
					value = Card.SIX; break;
				case '7':
					value = Card.SEVEN; break;
				case '8':
					value = Card.EIGHT; break;
				case '9':
					value = Card.NINE; break;
				case 'T':
					value = Card.TEN; break;
				case 'J':
					value = Card.JACK; break;
				case 'Q':
					value = Card.QUEEN; break;
				case 'K':
					value = Card.KING; break;
				case 'A':
					value = Card.ACE; break;
				default:
					value = 0;
			}

			switch(rep.charAt(1)){
				case 'C':
					suit = Card.CLUBS; break;
				case 'D':
					suit = Card.DIAMONDS; break;
				case 'S':
					suit = Card.SPADES; break;
				case 'H':
					suit = Card.HEARTS; break;
				default:
					suit = -1;
			}

			if(value>=Card.TWO && value<=Card.ACE && suit >=0){
				pass[index++] = new Card(value, suit);
			}
		}

		return pass;
	}

	public Card getMove(){
		Card pass = null;

		int index = 0;
		String rep;
		int suit;
		int value;
		while(index < 1){
			System.out.print("Enter Card: ");
			rep = scan.nextLine();

			switch(rep.charAt(0)){
				case '2':
					value = Card.TWO; break;
				case '3':
					value = Card.THREE; break;
				case '4':
					value = Card.FOUR; break;
				case '5':
					value = Card.FIVE; break;
				case '6':
					value = Card.SIX; break;
				case '7':
					value = Card.SEVEN; break;
				case '8':
					value = Card.EIGHT; break;
				case '9':
					value = Card.NINE; break;
				case 'T':
					value = Card.TEN; break;
				case 'J':
					value = Card.JACK; break;
				case 'Q':
					value = Card.QUEEN; break;
				case 'K':
					value = Card.KING; break;
				case 'A':
					value = Card.ACE; break;
				default:
					value = 0;
			}

			switch(rep.charAt(1)){
				case 'C':
					suit = Card.CLUBS; break;
				case 'D':
					suit = Card.DIAMONDS; break;
				case 'S':
					suit = Card.SPADES; break;
				case 'H':
					suit = Card.HEARTS; break;
				default:
					suit = -1;
			}

			if(value>=Card.TWO && value<=Card.ACE && suit >=0){
				pass = new Card(value, suit);
				index++;
			}
		}

		return pass;
	}

}