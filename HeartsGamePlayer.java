import java.util.ArrayList;

public class HeartsGamePlayer{

	// This is the brains of the hearts player. HeartsGameAI
	// is an abstract class that defines all the behavior
	// necesary for a hearts AI
	private HeartsGameAI playerAI;

	// this ArrayList represents the player's hand of cards in a
	// round of hearts. An ArrayList is used because a hand will shrink
	// as the round progresses
    private ArrayList<Card> hand;

    // this ArrayList represents cards that a player has collected as
    // a result of winning tricks in a round
    private ArrayList<Card> bag;

	// stores the player score; the lower the better
    private int playerScore;

    // stores the player's position around the table
    private int playerPos;

    // constructor for player
    public HeartsGamePlayer(HeartsGameAI ai, ArrayList<Card> h, int pos){
        playerScore = 0;
        playerPos = pos;
        playerAI = ai;
        hand = h;
        bag = new ArrayList<Card>(52);  //capacity of 52
    }

	// prepares the player for the next round by clearing bag
	// and setting the hand to a new hand
    public void initRound(){
    	bag.clear();
    	ArrayList<Card> copy = makeCopyOfHand(hand);
    	playerAI.initRound(copy);
    }

    // this method must return an array of cards OF SIZE 3 that
    // the player wishes to pass to another player. This method
    // queries its AI to determine the correct cards to pass, and
    // checks that these cards are legal (aka are actually a part
    // of the player's hand. If illegal, the method returns 3 random
    // legal cards.
    public Card[] getPass(){
    	Card[] pass = playerAI.getPass();
    	boolean legal = true;
    	if(pass.length != 3){
    		legal = false;
    	}else{
    		for(Card c: pass){
    			if(!hand.contains(c)){
    				legal = false;
    			}
    		}
            if(pass[0].equals(pass[1]) || pass[0].equals(pass[2]) || pass[1].equals(pass[2])){
                legal = false;
    		}
    	}
    	if(!legal) pass = RandomAI.getRandomPass(hand);
    	for(Card c: pass){
    		hand.remove(c);
    	}
    	return pass;
    }

    //This method is called to recieve passed cards from the
    //appropriate player.
    //precondition: passed.length == 3
    public void recievePass(Card[] passed){
    	for(Card c: passed){
    		hand.add(c);
    	}
        playerAI.recievePass(passed);
    }

    public boolean hasTwoOfClubs(){
        for(Card c: hand){
            if(c.suit == Card.CLUBS && c.value == Card.TWO) return true;
        }
        return false;
    }

    // this method must return a Card that represents a legal move
    // for the current state of the table (HeartsGameManager.table)
    // it will querie the AI for an appropriate card and will check
    // that this card is legal. If illegal, the method return a
    // random legal move from the player
    public Card getMove(){
        ArrayList<Card> legalMoves = getLegalMoves();
    	Card move = playerAI.getMove();
    	if(!legalMoves.contains(move)){
    	   move = RandomAI.getRandomMove(legalMoves);
    	}
    	hand.remove(move);
    	return move;
    }

    private ArrayList<Card> getLegalMoves(){
        int leadPos = playerPos;
        ArrayList<Card> validMoves = new ArrayList<Card>();
        while(HeartsGameManager.getMoveOnTable((leadPos+3)%4) != null && (leadPos+3)%4 != playerPos){
            leadPos = (leadPos+3)%4;
        }

        if(leadPos == playerPos){
            if(HeartsGameManager.getTrick() == 0){
                for(Card c: hand)
                    if(c.value == Card.TWO && c.suit == Card.CLUBS)
                        validMoves.add(c);
            }else if(HeartsGameManager.getHeartsBroken()){
                for(Card c: hand)
                    validMoves.add(c);
            }else{
                boolean hasNonHearts = false;
                for(Card c: hand)
                    if(c.suit != Card.HEARTS){
                        validMoves.add(c);
                        hasNonHearts = true;
                    }
                if(!hasNonHearts)
                    for(Card c: hand)
                        validMoves.add(c);
            }
        }else{
            int leadSuit = HeartsGameManager.getMoveOnTable(leadPos).suit;
            boolean hasLeadSuit=false;
            for(Card c: hand)
                if(c.suit == leadSuit){
                    validMoves.add(c);
                    hasLeadSuit=true;
                }
            if(!hasLeadSuit){
            	if(HeartsGameManager.getTrick() == 0){
                	for(Card c: hand)
                		if(c.suit != Card.HEARTS && !(c.suit == Card.SPADES && c.value == Card.QUEEN))
                			validMoves.add(c);
            	}else{
            		for(Card c: hand)
            			validMoves.add(c);
            	}
            }
        }
        return validMoves;
    }

    // This method allows the player's AI to analize the result of a trick
    // This method is very important because it allows the AI's to track
    // which cards are played after theirs in a trick
    public void endTrick(){
        playerAI.endTrick();
    }

    public void bagCard(Card c){
        bag.add(c);
    }

	// method returns the score of the player so far in a round by
	// counting the value of hearts and the queen of spades if present
    public int getRoundScore(){
    	int inc = 0;
    	Card c;
    	for(int i=0; i<bag.size(); i++){
    		c = bag.get(i);
    		if(c.suit == Card.HEARTS){
    			inc ++;
    		}
    		if(c.suit == Card.SPADES && c.value == Card.QUEEN){
    			inc += 13;
    		}
    	}
    	return inc;
    }

    public int getScore(){return playerScore;}

    public void incrementScore(int inc){
        playerScore += inc;
    }

    private static ArrayList<Card> makeCopyOfHand(ArrayList<Card> hand){
    	ArrayList<Card> copy = new ArrayList<Card>(13);
    	for(Card c: hand){
    		copy.add(c);
    	}
    	return copy;
    }
}