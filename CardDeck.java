import java.util.Random;
import java.security.SecureRandom;
//import java.exception.*;

public class CardDeck{

    //an array that holds all of the cards
	private Card[] cards;

	//holds the index of the top of the deck
	private int topCardIndex;

	//this pseudo random number generator is used to shuffle the deck on occasion
	private SecureRandom png;

	public CardDeck(){

		//initialize the array of Card elements
		cards = new Card[52];

		topCardIndex = 0;

		//initialize the random number generator
		png = new SecureRandom();

		//fill the array to represent the traditional deck of cards
		// 1 Clubs ... ACE Clubs 1 Diamonds ... ACE Diamonds, etc
		for(int suit = Card.CLUBS; suit <= Card.HEARTS; suit++)
			for(int value = Card.TWO; value <= Card.ACE; value++)
				cards[13*suit + value - 2] = new Card(value, suit);
	}

	// this shuffle produces a uniformly random permutation of the
	// deck a.k.a. a perfectly random shuffle
	// based on a shuffling algorithm proved in [Fisher-Yates 1938]
	public void shuffle(){
        int randomPos;
        //for each card in the deck
        for(int i=0; i<52; i++){
            randomPos = getRandomPos(i);
            exchangeCards(i, randomPos);
        }
        topCardIndex = 0;
	}

    //private helper method for shuffle
	//exchanges the cards at positions i and j in the deck
	private void exchangeCards(int i, int j){
	   Card temp = cards[i];
	   cards[i] = cards[j];
	   cards[j] = temp;
	}

    //private helper method for shuffle
	//returns a random integer s.t. 0 <= output <= max
	private int getRandomPos(int max){
        byte[] buffer = new byte[1];
        png.nextBytes(buffer);
        while((buffer[0]&63) > max){
            png.nextBytes(buffer);
        }
        return (int)(buffer[0]&63);
	}

	//deals out the next card from the deck.
	//theoretically, we should not call for the 53rd card
	public Card nextCard(){
	   if(topCardIndex >=52){
	       throw new IndexOutOfBoundsException();
	   }
	   return cards[topCardIndex++];
	}

    // a simple method to print out the deck to the monitor
    // note: only used to debug
    public void display(){
        for(Card c: cards){
            System.out.println(c);
        }
    }
}