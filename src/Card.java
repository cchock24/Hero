import javax.swing.*;
import java.awt.*;

public class Card {

    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 50;
    private int health;
    private int damage;
    private int energy;
    private boolean frozen;
    private boolean deadly;
    private boolean decay;
    private boolean charge;
    private boolean hasArmor;
    private boolean st; //Strikethrough
    Image icon;
    private int lane;
    private int playerNum;
    private int origDamage; //To reset after Frozen

    public Card(int h, int d, int e){
        health = h;
        damage = d;
        energy = e;
        icon = new ImageIcon().getImage();
        origDamage = this.damage;
    }

    public int getOrigDamage() {
        return origDamage;
    }

    public boolean isHasArmor() {
        return hasArmor;
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

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isDeadly() {
        return deadly;
    }

    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }

    public boolean isDecay() {
        return decay;
    }

    public void setDecay(boolean decay) {
        this.decay = decay;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public boolean isSt() {
        return st;
    }

    public void setSt(boolean st) {
        this.st = st;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public boolean takeDamage(int attack){
        //Armor makes it so attacks to one less damage
        if(this.isHasArmor()){
            health -= attack -1;
        }
        else{
            health -= attack;
        }
        if(health <= 0){
            return true;
        }
        return false;
    }

    public void printCard(Graphics g, int x, int y){
        g.drawRect(x, y, CARD_WIDTH,CARD_HEIGHT);
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        g.drawString("H: " + this.health, x+10, y + 15);
        g.drawString("E: " + this.energy, x+10, y + 30);
        g.drawString("D: " + this.damage, x+10, y + 45);
    }
    // Draw Icon for lane (default square for now)
    public void drawLane(int x, int y, Graphics g){
        g.drawRect(x, y, 30,30);
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        g.drawString("H: " + this.health, x+10, y+10);
        g.drawString("D: " + this.damage, x+10, y +20);
    }

}

