import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HeartsGameGUI extends JFrame{

	private HeartsGamePanel panel;

	private ArrayList<Card> northHand,
							eastHand,
							southHand,
							westHand;

	private Card[] northPass,
				   eastPass,
				   southPass,
				   westPass;

	private Card northPlayed,
				 eastPlayed,
				 southPlayed,
				 westPlayed;

	public int northTotal,
			   eastTotal,
			   southTotal,
			   westTotal,
			   northRound,
			   eastRound,
			   southRound,
			   westRound;

    private BufferedImage[] cards;

    public boolean showNorthHand,
    			   showEastHand,
    			   showSouthHand,
    			   showWestHand;

	public HeartsGameGUI(){
		super("Hearts");

		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		northHand = new ArrayList<Card>();
		eastHand = new ArrayList<Card>();
		southHand = new ArrayList<Card>();
		westHand = new ArrayList<Card>();

		northPass = null;
		eastPass = null;
		southPass = null;
		westPass = null;

		northPlayed = null;
		eastPlayed = null;
		southPlayed = null;
		westPlayed = null;

		northTotal = 0;
		eastTotal = 0;
		southTotal = 0;
		westTotal = 0;

		northRound = 0;
		eastRound = 0;
		southRound = 0;
		westRound = 0;

		showNorthHand = false;
		showEastHand = false;
		showSouthHand = false;
		showWestHand = false;

		initializeCardImages();

		panel = new HeartsGamePanel();
		add(panel);

		setVisible(true);
	}

	private void initializeCardImages(){
		String fileName;
	    BufferedImage verImage;
	    BufferedImage horImage;

	    cards = new BufferedImage[106];

	    for(int suit = Card.CLUBS; suit<=Card.HEARTS; suit++){
	        for(int val=Card.TWO; val<=Card.ACE; val++){
	        	fileName = "";
	            switch(val){
	            	case Card.TWO:   fileName += "2"; break;
	            	case Card.THREE: fileName += "3"; break;
	            	case Card.FOUR:  fileName += "4"; break;
	            	case Card.FIVE:  fileName += "5"; break;
	            	case Card.SIX:   fileName += "6"; break;
	            	case Card.SEVEN: fileName += "7"; break;
	            	case Card.EIGHT: fileName += "8"; break;
	            	case Card.NINE:  fileName += "9"; break;
	            	case Card.TEN:   fileName += "t"; break;
	            	case Card.JACK:  fileName += "j"; break;
	            	case Card.QUEEN: fileName += "q"; break;
	            	case Card.KING:  fileName += "k"; break;
	            	case Card.ACE:   fileName += "a"; break;
	            }

	            switch(suit){
	            	case Card.CLUBS:   fileName += "c"; break;
	            	case Card.DIAMONDS: fileName += "d"; break;
	            	case Card.SPADES:  fileName += "s"; break;
	            	case Card.HEARTS:  fileName += "h"; break;
	            }

				try{
					verImage = ImageIO.read(new File("cards/" + fileName + ".gif"));
					cards[2*(suit*13 + val-2)] = verImage;
				}catch(IOException e){
					System.out.println("Could not find " + fileName + " vertical image");
				}

				try{
					horImage = ImageIO.read(new File("cards/" + fileName + "h.gif"));
					cards[2*(suit*13 + val-2)+1] = horImage;
				}catch(IOException e){
					System.out.println("Could not find " + fileName + " vertical image");
				}
	        }
	    }

		try{
	    	BufferedImage verticalBack = ImageIO.read(new File("cards/backv.gif"));
	    	cards[104] = verticalBack;
		}catch(Exception e){
			System.out.println("Could not find vertical back image");
		}
		try{
			BufferedImage horizontalBack = ImageIO.read(new File("cards/backh.gif"));
			cards[105] = horizontalBack;
		}catch(IOException e){
			System.out.println("Could not find horizontal back image");
		}
	}

	private class HeartsGamePanel extends JPanel{

		public HeartsGamePanel(){
			setBackground(new Color(0,180,0));
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);

			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.setColor(Color.black);
			g.drawString("North", 20, 30);
			g.drawString("East", 485, 30);
			g.drawString("South", 485, 490);
			g.drawString("West", 20, 490);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Total: " + northTotal, 20, 50);
			g.drawString("Round: " + northRound, 20, 65);
			g.drawString("Total: " + eastTotal, 485, 50);
			g.drawString("Round: " + eastRound, 485, 65);
			g.drawString("Total: " + southTotal, 485, 510);
			g.drawString("Round: " + southRound, 485, 525);
			g.drawString("Total: " + westTotal, 20, 510);
			g.drawString("Round: " + westRound, 20, 525);

			Card[] aux;

			// draw north cards
			aux = new Card[northHand.size()];
			for(int i=0; i<aux.length; i++){
				aux[i] = northHand.get(i);
			}
			Arrays.sort(aux);
			for(int i=0; i<aux.length; i++){
				Card c = aux[i];

				int x = 375 - 20*i;
				int	y = 25;

				if(showNorthHand) paintVerticalCard(g, c, x, y);
				else paintVerticalBack(g,x,y);
			}

			// draw east cards
			aux = new Card[eastHand.size()];
			for(int i=0; i<aux.length; i++){
				aux[i] = eastHand.get(i);
			}
			Arrays.sort(aux);
			for(int i=0; i<aux.length; i++){
				Card c = aux[i];

				int x = 475;
				int y = 375 - 20*i;

				if(showEastHand) paintHorizontalCard(g, c, x, y);
				else paintHorizontalBack(g,x,y);
			}

			// draw south cards
			aux = new Card[southHand.size()];
			for(int i=0; i<aux.length; i++){
				aux[i] = southHand.get(i);
			}
			Arrays.sort(aux);
			for(int i=0; i<aux.length; i++){
				Card c = aux[i];

				int x = 150 + 20*i;
				int	y = 450;

				if(showSouthHand) paintVerticalCard(g, c, x, y);
				else paintVerticalBack(g,x,y);
			}

			// draw west cards
			aux = new Card[westHand.size()];
			for(int i=0; i<aux.length; i++){
				aux[i] = westHand.get(i);
			}
			Arrays.sort(aux);
			for(int i=0; i<aux.length; i++){
				Card c = aux[i];

				int x = 25;
				int y = 140 + 20*i;

				if(showWestHand) paintHorizontalCard(g, c, x, y);
				else paintHorizontalBack(g,x,y);
			}

			if(northPass != null){
				for(int i=0; i<3; i++){
					if(showNorthHand) paintVerticalCard(g, northPass[i], 280-20*i, 135);
					else paintVerticalBack(g, 280-20*i, 135);
				}
			}

			if(eastPass != null){
				for(int i=0; i<3; i++){
					if(showEastHand) paintHorizontalCard(g, eastPass[i], 355, 270-20*i);
					else paintHorizontalBack(g, 355, 270-20*i);
				}
			}

			if(southPass != null){
				for(int i=0; i<3; i++){
					if(showSouthHand) paintVerticalCard(g, southPass[i], 240+20*i, 340);
					else paintVerticalBack(g, 240+20*i, 340);
				}
			}

			if(westPass != null){
				for(int i=0; i<3; i++){
					if(showWestHand) paintHorizontalCard(g, westPass[i], 140, 230+20*i);
					else paintHorizontalBack(g, 140, 230+20*i);
				}
			}

			if(northPlayed != null){
				paintVerticalCard(g, northPlayed, 260, 150);
			}

			if(eastPlayed != null){
				paintHorizontalCard(g, eastPlayed, 340, 250);
			}

			if(southPlayed != null){
				paintVerticalCard(g, southPlayed, 260, 325);
			}

			if(westPlayed != null){
				paintHorizontalCard(g, westPlayed, 155, 250);
			}
		}

		public void paintVerticalCard(Graphics g, Card card, int x, int y){
		    g.setColor(Color.white);
		    BufferedImage img = cards[2*(13*card.suit + card.value - 2)];
			g.drawImage(img,x,y,null);
		}

		public void paintHorizontalCard(Graphics g, Card card, int x, int y){
			g.setColor(Color.white);
		    BufferedImage img = cards[2*(13*card.suit + card.value - 2)+1];
			g.drawImage(img,x,y,null);
		}

		public void paintVerticalBack(Graphics g, int x, int y){
			BufferedImage ver = cards[104];
			g.drawImage(ver,x,y,null);
		}

		public void paintHorizontalBack(Graphics g, int x, int y){
			BufferedImage hor = cards[105];
			g.drawImage(hor,x,y,null);
		}
	}

	public void setNorthHand(ArrayList<Card> nh){
		northHand = nh;
	}

	public void setEastHand(ArrayList<Card> eh){
		eastHand = eh;
	}

	public void setSouthHand(ArrayList<Card> sh){
		southHand = sh;
	}

	public void setWestHand(ArrayList<Card> wh){
		westHand = wh;
	}

	public void setNorthPass(Card[] np){
		northPass = np;
	}

	public void setEastPass(Card[] ep){
		eastPass = ep;
	}

	public void setSouthPass(Card[] sp){
		southPass = sp;
	}

	public void setWestPass(Card[] wp){
		westPass = wp;
	}

	public void update(){
		panel.repaint();
	}

	public void setPlayed(int pos, Card c){
		switch(pos){
			case HeartsGameManager.NORTH_PLAYER:
				northPlayed = c;
				break;
			case HeartsGameManager.EAST_PLAYER:
				eastPlayed = c;
				break;
			case HeartsGameManager.SOUTH_PLAYER:
				southPlayed = c;
				break;
			case HeartsGameManager.WEST_PLAYER:
				westPlayed = c;
				break;
		}
	}

	public void setRoundPoints(int pts, int pos){
		switch(pos){
			case HeartsGameManager.NORTH_PLAYER:
				northRound = pts;
				break;
			case HeartsGameManager.EAST_PLAYER:
				eastRound = pts;
				break;
			case HeartsGameManager.SOUTH_PLAYER:
				southRound = pts;
				break;
			case HeartsGameManager.WEST_PLAYER:
				westRound = pts;
				break;
		}
	}

	public void clearTable(){
		northPlayed = eastPlayed = southPlayed = westPlayed = null;
	}
}
