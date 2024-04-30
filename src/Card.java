import javax.swing.*;
import java.awt.*;

public class Card {
    public boolean canStrike;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 50;
    private String name;
    private int health;
    private int damage;
    private int energy;
    private boolean frozen;
    private boolean decay;
    private boolean charge;
    private boolean canFreeze;
    private boolean hasArmor;
    private boolean st; //Strikethrough
    Image icon;
    private int lane;
    private int playerNum;
    private int origDamage; //To reset after Frozen
    private int x;
    private int y;

    public Card(int h, int d, int e){
        health = h;
        damage = d;
        energy = e;
        icon = new ImageIcon().getImage();
        origDamage = this.damage;
        frozen = false;
    }

    public int getOrigDamage() {
        return origDamage;
    }

    public boolean isHasArmor() {
        return hasArmor;
    }

    public boolean isCanFreeze() {
        return canFreeze;
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

    public boolean isCanStrike() {
        return canStrike;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
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
    public void printCard(Graphics g){
        //Replace with Image Icon
        g.drawRect(x, y, CARD_WIDTH,CARD_HEIGHT);
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        g.drawString("H: " + this.health, x+10, y + 15);
        g.drawString("E: " + this.energy, x+10, y + 30);
        g.drawString("D: " + this.damage, x+10, y + 45);
    }
    // Draw Icon for lane (default square for now)
    public void drawLane(int x, int y, Graphics g){
        this.x = x;
        this.y = y;
        //Replace with Image Icon
        g.drawRect(x, y, 30,30);
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        g.drawString("H: " + this.health, x+10, y+10);
        g.drawString("D: " + this.damage, x+10, y +20);
    }
    public void drawLane(Graphics g){
        g.drawRect(x, y, 30,30);
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        g.drawString("H: " + this.health, x+10, y+10);
        g.drawString("D: " + this.damage, x+10, y +20);
    }

    // Modified from Mr. Blick CS2FinalProjectResources
    public boolean isClicked(int x, int y) {
        if(x >= this.x && x <= this.x+CARD_WIDTH && y >= this.y && y <= this.y+CARD_HEIGHT){
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void printStats(Graphics g){
        if(decay){
            g.drawString("Decay",x,y);
        }
        if(hasArmor){
            g.drawString("Armor",x,y);
        }
        if(canStrike){
            g.drawString("Strikethrough",x,y);
        }
        if(canFreeze){
            g.drawString("Freeze",x,y);
        }
        if(charge){
            g.drawString("Charger",x,y);
        }

    }
}

