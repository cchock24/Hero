import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {

    private final int MAX_HAND = 10;
    private final int MAX_DECK = 40;
    private int health;
    private int energy;
    private int block;
    Image icon;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;

    public Player(){
        health = 20;
        energy = 0;
        block = 0;
        hand = new ArrayList<Card>();
        deck = new ArrayList<Card>();
        icon = new ImageIcon().getImage();
        discard = new ArrayList<Card>();
    }

    public boolean checkWin(){
        return (health <= 0);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }
    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void subEnergy(int energy){
        this.energy -= energy;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    //Print Player's Hand
    public void printHand(Graphics g, GameViewer w){
        for(int i = 0; i < hand.size(); i++){
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            hand.get(i).printCard(g, w);
        }
    }
    //Take random card from deck put it in hand if less than 10 cards
    public void addHand(){
        int random = (int)(Math.random()*deck.size());
        if(hand.size() <= 10){
            hand.add(deck.remove(random));
        }
        for(int i = 0; i < hand.size(); i++){
            if(i <= 3){
                hand.get(i).setX(20 + (i * Card.CARD_WIDTH) + i*15);
                hand.get(i).setY(630);
                System.out.println(20 + (i * Card.CARD_WIDTH) + i*15);
            }
            else{
                hand.get(i).setX(20 + ((i - 4) * Card.CARD_WIDTH) + (i-4)*15);
                hand.get(i).setY(700);
                System.out.println(20 + ((i - 4) * Card.CARD_WIDTH) + (i-4)*15);
            }

        }
    }
    public void addHand(Card c){
        hand.add(c);
    }

    //Take Card from hand put in lane
    public Card addLane(int index){
        hand.get(index).setLane(index);
        return hand.remove(index);
    }
    //Make Random Deck
    public void randomDeck(ArrayList<Card> c){
        for(int i = 0; i < 40; i++){
            //Prevent Reference Interlap
            Card card = new Card(c.get((int) (Math.random() * 29)));
            deck.add(card);
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    //Take Damage
    public boolean takeDamage(int damage){
        this.health -= damage;
        if(health <= 0){
            return true;
        }
        return false;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
    //Draw Player Icon
    public void drawIcon(Graphics g, GameViewer w, int player){
        if(player == 0){
            g.drawOval(250,500,100,100);
            g.drawImage(icon, 250, 500, 100, 100, w);
        }
        if(player == 1){
            g.drawOval(250,50,100,100);
            g.drawImage(icon, 250, 50, 100, 100, w);
        }
    }
}
