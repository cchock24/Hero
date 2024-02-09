import javax.swing.*;
import java.awt.*;

public class Card {

    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 50;
    private int health;
    private int damage;
    private int energy;
    Image icon;
    private int lane;
    private int playerNum;

    public Card(int h, int d, int e){
        health = h;
        damage = d;
        energy = e;
        icon = new ImageIcon().getImage();
    }

    public void Damage(int d){
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void printCard(Graphics g, int x, int y){
        g.drawRect(x, y, CARD_WIDTH,CARD_HEIGHT);
    }
}

