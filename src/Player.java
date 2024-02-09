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
    private String name;
    private ArrayList<Card> discard;

    public Player(String n){
        health = 20;
        energy = 0;
        block = 0;
        hand = new ArrayList<Card>();
        deck = new ArrayList<Card>();
        name = n;
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

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void printHand(Graphics g){
        for(int i = 0; i <hand.size(); i++){
            if(i <= 4){
                hand.get(i).printCard(g,700, 10 + (i * Card.CARD_WIDTH));
            }
            else{
                hand.get(i).printCard(g,800, 10 + (i * Card.CARD_WIDTH));
            }

        }
    }

    public void addHand(Card c){
        hand.add(c);
    }
}
