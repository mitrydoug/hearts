import java.util.ArrayList;

public class BasicPlayer extends HeartsGameAI{

	private ArrayList<Card> hand;
	private boolean queenOut;

	public BasicPlayer(int p){
		super(p);
		queenOut = false;
	}

	public void initRound(ArrayList<Card> h){
		hand = h;
	}

	public Card[] getPass(){
		Card[] pass = new Card[3];

		ArrayList<Card> clubs = new ArrayList<Card>();
		ArrayList<Card> diamonds = new ArrayList<Card>();
		ArrayList<Card> spades = new ArrayList<Card>();
		ArrayList<Card> hearts = new ArrayList<Card>();

		int index = 0;
		Card QS = new Card(Card.QUEEN, Card.SPADES);
		if(hand.contains(QS)){
			pass[index++] = QS;
			hand.remove(QS);
		}

		for(Card c: hand){
			switch(c.suit){
				case Card.CLUBS:
					int i=0;
					while(clubs.size() > i && clubs.get(i).value > c.value){
						i++;
					}
					clubs.add(i, c);
					break;
				case Card.DIAMONDS:
					int i=0;
					while(diamonds.size() > i && diamonds.get(i).value > c.value){
						i++;
					}
					diamonds.add(i, c);
					break;
				case Card.SPADES:
					int i=0;
					while(spades.size() > i && spades.get(i).value > c.value){
						i++;
					}
					spades.add(i, c);
					break;
				case Card.HEARTS:
					int i=0;
					while(hearts.size() > i && hearts.get(i).value > c.value){
						i++;
					}
					hearts.add(i, c);
					break;
			}
		}

		while(index < 3){
			if(clubs.size() > diamonds.size() && clubs.size() > spades.size() && clubs.size() > hearts.size()){
				pass[index++] = clubs.get(0);
				clubs.remove(clubs.get(0));
				hand.remove(pass[index-1]);
			}else if(diamonds.size() > clubs.size() && diamonds.size() > spades.size() && diamonds.size() > hearts.size()){
				pass[index++] = diamonds.get(0);
				diamonds.remove(diamonds.get(0));
				hand.remove(pass[index-1]);
			}else if(spades.size() > clubs.size() && spades.size() > diamonds.size() && diamonds.size() > hearts.size()){
				pass[index++] = spades.get(0);
				spades.remove(spades.get(0));
				hand.remove(pass[index-1]);
			}else if(hearts.size() > clubs.size() && hearts.size() > diamonds.size() && hearts.size() > spades.size()){
				pass[index++] = hearts.get(0);
				hearts.remove(hearts.get(0));
				hand.remove(pass[index-1]);
			}
		}

		return pass;

	}

	public void recievePass(Card[] pass){
		for(Card c: pass){
			hand.add(c);
		}
	}

    public Card getMove(){
    	for(){
    	}
    }

    public void endTrick(){
    }
}