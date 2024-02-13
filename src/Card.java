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

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public boolean takeDamage(int attack){
        health -= attack;
        if(health <= 0){
            return true;
        }
        return false;
    }

    public void printCard(Graphics g, int x, int y){
        g.drawRect(x, y, CARD_WIDTH,CARD_HEIGHT);
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("H: " + this.health, x+10, y + 17);
        g.drawString("E: " + this.energy, x+10, y + 37);
    }
    // Draw Icon for lane (default square for now)
    public void drawLane(int x, int y, Graphics g){
        g.drawRect(x, y, 10,10);
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        g.drawString("H: " + this.health, x, y);
    }
}

