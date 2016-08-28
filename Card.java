public class Card implements Comparable<Card>{

	//these constants represent the suit of the playing card
	public static final int CLUBS    = 0,
							DIAMONDS = 1,
							SPADES   = 2,
							HEARTS   = 3;

	// these constants represent the value of the playing card
	// note: ACE has a high value of 14 in this game
	public static final int TWO   = 2,
							THREE = 3,
							FOUR  = 4,
							FIVE  = 5,
							SIX   = 6,
							SEVEN = 7,
							EIGHT = 8,
							NINE  = 9,
							TEN   = 10,
							JACK  = 11,
							QUEEN = 12,
							KING  = 13,
							ACE   = 14;

	//instance variables
	public final int value;
	public final int suit;

	//constructor simply assigns a value and a suit
	public Card(int value, int suit){
		this.value = value;
		this.suit = suit;
	}

	public boolean equals(Object c){
	    return ((Card)c).suit == suit && ((Card)c).value == value;
	}


	// this is an overwritten method from class Object
	// this method simply converts a card into
	// a basic string representation in the form
	//
	//  "2 of Spades" or "King of Hearts"
	public String toString(){

		String result = "";

		switch(value){
			case TWO: case THREE: case FOUR:  case FIVE:
			case SIX: case SEVEN: case EIGHT: case NINE:
		    case TEN:
				result += value + " "; break;
			case JACK:
				result += "Jack "; break;
			case QUEEN:
				result += "Queen "; break;
			case KING:
				result += "King "; break;
			case ACE:
				result += "Ace "; break;
		}

		switch(suit){
			case CLUBS:    result += "of Clubs"; break;
			case DIAMONDS: result += "of Diamonds"; break;
			case SPADES:   result += "of Spades"; break;
			case HEARTS:   result += "of Hearts"; break;
		}

		return result;
	}

	public int compareTo(Card c){
		return (suit*13+value) - (c.suit*13+c.value);
	}
}