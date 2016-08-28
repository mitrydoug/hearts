import java.util.*;

public class HeartsGameManager{

	public static final int NORTH_PLAYER = 0,
					        EAST_PLAYER  = 1,
					        SOUTH_PLAYER = 2,
					        WEST_PLAYER  = 3;

	public static final int PASS_NONE   = 0,
	                        PASS_LEFT   = 1,
	                        PASS_RIGHT  = 2,
	                        PASS_ACROSS = 3;

    private static CardDeck deck;

	private static HeartsGamePlayer northPlayer,
					                eastPlayer,
					                southPlayer,
					                westPlayer;

	private static ArrayList<Card> northHand,
								   eastHand,
								   southHand,
								   westHand;

	//GUI interface
	private static HeartsGameGUI gui;

	//round information
	private static int round;
	private static boolean heartsBroken;
	private static int passDirection;

	//trick information
    private static Card[] table = new Card[4];
    private static int trick;

	public static void main(String[] args){

		gui = new HeartsGameGUI();

		deck = new CardDeck();
		deck.shuffle();

		northHand = new ArrayList<Card>(13);
		eastHand = new ArrayList<Card>(13);
		southHand = new ArrayList<Card>(13);
		westHand = new ArrayList<Card>(13);

		northPlayer = new HeartsGamePlayer(new Sneaky(NORTH_PLAYER), northHand, NORTH_PLAYER);
        eastPlayer  = new HeartsGamePlayer(new Sneaky(EAST_PLAYER),  eastHand,  EAST_PLAYER);
        southPlayer = new HeartsGamePlayer(new HumanPlayer(SOUTH_PLAYER), southHand, SOUTH_PLAYER);
        westPlayer  = new HeartsGamePlayer(new Sneaky(WEST_PLAYER),  westHand,  WEST_PLAYER);

        gui.setNorthHand(northHand);
        gui.setEastHand(eastHand);
        gui.setSouthHand(southHand);
        gui.setWestHand(westHand);

        gui.showSouthHand = true;

		playGame();
	}

	private static void playGame(){
		round = 1;
        passDirection = PASS_LEFT;
		//this loop loops until one of the player's scores passes 100
		do{
			deck.shuffle();
			dealCards();
			passCards();
			playRound();
			manageScore();

			round ++;
			passDirection = (passDirection+1)%4;

		}while(maxPlayerScore() < 100);

		int winner = NORTH_PLAYER;
		for(int player=EAST_PLAYER; player<=WEST_PLAYER; player++){
		    if(getPlayerScore(player) < getPlayerScore(winner))
		        winner = player;
		}
		switch(winner){
		    case NORTH_PLAYER:
		      System.out.println("NORTH WINS");
		      break;
            case EAST_PLAYER:
              System.out.println("EAST WINS");
              break;
            case SOUTH_PLAYER:
              System.out.println("SOUTH WINS");
              break;
            case WEST_PLAYER:
              System.out.println("WEST WINS");
              break;
		}
	}

	private static void dealCards(){
	    northHand.clear();
        eastHand.clear();
        southHand.clear();
        westHand.clear();
        for(int i=0; i<13; i++){
            northHand.add(deck.nextCard());
            eastHand.add(deck.nextCard());
            southHand.add(deck.nextCard());
            westHand.add(deck.nextCard());
        }
        northPlayer.initRound();
        eastPlayer.initRound();
        southPlayer.initRound();
        westPlayer.initRound();
        gui.update();
		pause(500);
	}

	private static void passCards(){
        Card[] northPass;
        Card[] eastPass;
        Card[] southPass;
        Card[] westPass;
        switch(round%4){
            case PASS_NONE:
                //no passing on this round
                break;
            case PASS_LEFT:
                northPass = northPlayer.getPass();
                eastPass  = eastPlayer.getPass();
                southPass = southPlayer.getPass();
                westPass  = westPlayer.getPass();

				gui.setNorthPass(northPass);
				gui.setEastPass(eastPass);
				gui.setSouthPass(southPass);
				gui.setWestPass(westPass);
				gui.update();
				pause(500);

                northPlayer.recievePass(westPass);
                eastPlayer.recievePass(northPass);
                southPlayer.recievePass(eastPass);
                westPlayer.recievePass(southPass);
                break;
            case PASS_RIGHT:
                northPass = northPlayer.getPass();
                eastPass  = eastPlayer.getPass();
                southPass = southPlayer.getPass();
                westPass  = westPlayer.getPass();

                gui.setNorthPass(northPass);
				gui.setEastPass(eastPass);
				gui.setSouthPass(southPass);
				gui.setWestPass(westPass);
				gui.update();
				pause(500);

                northPlayer.recievePass(eastPass);
                eastPlayer.recievePass(southPass);
                southPlayer.recievePass(westPass);
                westPlayer.recievePass(northPass);
                break;
            case PASS_ACROSS:
                northPass = northPlayer.getPass();
                eastPass  = eastPlayer.getPass();
                southPass = southPlayer.getPass();
                westPass  = westPlayer.getPass();

                gui.setNorthPass(northPass);
				gui.setEastPass(eastPass);
				gui.setSouthPass(southPass);
				gui.setWestPass(westPass);
				gui.update();
				pause(500);

                northPlayer.recievePass(southPass);
                eastPlayer.recievePass(westPass);
                southPlayer.recievePass(northPass);
                westPlayer.recievePass(eastPass);
                break;
        }
        gui.setNorthPass(null);
        gui.setEastPass(null);
        gui.setSouthPass(null);
        gui.setWestPass(null);
        gui.update();
        pause(2000);
	}

	private static void playRound(){
		//System.out.println("round: " + round);

        heartsBroken = false;

        HeartsGamePlayer[] players = {northPlayer, eastPlayer, southPlayer, westPlayer};

        int startPos = -1;
        if(northPlayer.hasTwoOfClubs())      startPos = NORTH_PLAYER;
        else if(eastPlayer.hasTwoOfClubs())  startPos = EAST_PLAYER;
        else if(southPlayer.hasTwoOfClubs()) startPos = SOUTH_PLAYER;
        else if(westPlayer.hasTwoOfClubs())  startPos = WEST_PLAYER;

        HeartsGamePlayer dominantPlayer;
        int dominantPos;
        Card dominantCard;
        Card play;

        for(trick = 0; trick < 13; trick++){

            //System.out.println("Trick: " + trick);

            dominantPos = startPos;
            dominantCard = play = players[startPos].getMove();
            table[startPos] = play;
            if(play.suit == Card.HEARTS)heartsBroken = true;
            //System.out.println(play);
            gui.setPlayed(startPos, play);
            gui.update();
            pause(500);

            play = players[(startPos+1)%4].getMove();
            table[(startPos+1)%4] = play;
            if(play.suit == dominantCard.suit && play.value > dominantCard.value){
                dominantCard = play;
                dominantPos = (startPos+1)%4;
            }
            if(play.suit == Card.HEARTS)heartsBroken = true;
            //System.out.println(play);
            gui.setPlayed((startPos+1)%4, play);
            gui.update();
            pause(500);

            play = players[(startPos+2)%4].getMove();
            table[(startPos+2)%4] = play;
            if(play.suit == dominantCard.suit && play.value > dominantCard.value){
                dominantCard = play;
                dominantPos = (startPos+2)%4;
            }
            if(play.suit == Card.HEARTS)heartsBroken = true;
            //System.out.println(play);
            gui.setPlayed((startPos+2)%4, play);
            gui.update();
            pause(500);

            play = players[(startPos+3)%4].getMove();
            table[(startPos+3)%4] = play;
            if(play.suit == dominantCard.suit && play.value > dominantCard.value){
                dominantCard = play;
                dominantPos = (startPos+3)%4;
            }
            if(play.suit == Card.HEARTS)heartsBroken = true;
            //System.out.println(play);
            gui.setPlayed((startPos+3)%4, play);
            gui.update();
            pause(2000);

            northPlayer.endTrick();
            eastPlayer.endTrick();
            southPlayer.endTrick();
            westPlayer.endTrick();

            gui.clearTable();

            dominantPlayer = players[dominantPos];
            for(int i=0; i<4; i++){
                dominantPlayer.bagCard(table[i]);
                table[i] = null;
            }

            gui.setRoundPoints(dominantPlayer.getRoundScore(), dominantPos);

            startPos = dominantPos;
        }
	}

	private static void manageScore(){
	    if(northPlayer.getRoundScore()       == 26){
            eastPlayer.incrementScore(26);
            southPlayer.incrementScore(26);
            westPlayer.incrementScore(26);
	    }else if(eastPlayer.getRoundScore()  == 26){
            southPlayer.incrementScore(26);
            westPlayer.incrementScore(26);
            northPlayer.incrementScore(26);
        }else if(southPlayer.getRoundScore() == 26){
            westPlayer.incrementScore(26);
            northPlayer.incrementScore(26);
            eastPlayer.incrementScore(26);
        }else if(westPlayer.getRoundScore()  == 26){
            northPlayer.incrementScore(26);
            eastPlayer.incrementScore(26);
            southPlayer.incrementScore(26);
        }else{
            northPlayer.incrementScore(northPlayer.getRoundScore());
            eastPlayer.incrementScore(eastPlayer.getRoundScore());
            southPlayer.incrementScore(southPlayer.getRoundScore());
            westPlayer.incrementScore(westPlayer.getRoundScore());
        }

        gui.northTotal = northPlayer.getScore();
        gui.eastTotal = eastPlayer.getScore();
        gui.southTotal = southPlayer.getScore();
        gui.westTotal = westPlayer.getScore();

        gui.northRound = 0;
        gui.eastRound = 0;
        gui.southRound = 0;
        gui.westRound = 0;

	}

	private static int maxPlayerScore(){
		int max = 0;
		max = Math.max(max, northPlayer.getScore());
		max = Math.max(max, eastPlayer.getScore());
		max = Math.max(max, southPlayer.getScore());
		max = Math.max(max, westPlayer.getScore());
		return max;
	}

	public static int getRound(){return round;}
	public static int getTrick(){return trick;}

	public static int getPlayerScore(int position){
		switch(position){
			case NORTH_PLAYER:
				return northPlayer.getScore();
			case EAST_PLAYER:
				return eastPlayer.getScore();
			case SOUTH_PLAYER:
				return southPlayer.getScore();
			case WEST_PLAYER:
				return westPlayer.getScore();
			default:
				return -1;
		}
	}

	public static Card getMoveOnTable(int playerPos){
		if(playerPos>=0 && playerPos < 4) return table[playerPos];
		else return null;
	}

	public static boolean getHeartsBroken(){return heartsBroken;}

	public static void pause(){
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}

	public static void pause(int millis){
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < millis){
		}
	}
}