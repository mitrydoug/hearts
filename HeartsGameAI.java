import java.util.ArrayList;

public abstract class HeartsGameAI{

	public static final int NORTH = 0,
						    EAST  = 1,
						    SOUTH = 2,
						    WEST  = 3;

	private int playerPos;

	public HeartsGameAI(int position){
		playerPos = position;
	}

	public abstract void initRound(ArrayList<Card> hand);
	public abstract Card[] getPass();
	public abstract void recievePass(Card[] pass);
    public abstract Card getMove();
    public abstract void endTrick();
}