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

    public void printHand(Graphics g){
        for(int i = 0; i < hand.size(); i++){
            if(i <= 4){
                hand.get(i).printCard(g,20 + (i * Card.CARD_WIDTH) + i*15, 630);
                g.setFont(new Font("Serif", Font.PLAIN, 15));
                g.drawString(Integer.toString(i), 20 + (i * Card.CARD_WIDTH) + i*15 +45, 695);
            }

            else{
                hand.get(i).printCard(g,20 + ((i - 5) * Card.CARD_WIDTH) + (i-5)*15, 700);
                g.setFont(new Font("Serif", Font.PLAIN, 15));
                g.drawString(Integer.toString(i), 20 + ((i-5) * Card.CARD_WIDTH) + (i-5)*15+45, 765);
            }

        }
    }
    //Take random card from deck put it in hand if less than 10 cards
    public void addHand(){
        int random = (int)(Math.random()*40);
        if(hand.size() <= 10){
            hand.add(deck.remove(random));
        }
    }

    public Card addLane(int index){
        hand.get(index).setLane(index);
        return hand.remove(index);
    }

    public void randomDeck(){
        for(int i = 0; i < 40; i++){
            deck.add(new Card(2,2,2));
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public boolean takeDamage(int damage){
        this.health -= damage;
        if(health <= 0){
            return true;
        }
        return false;
    }
}
