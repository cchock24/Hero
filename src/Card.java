import javax.swing.*;
import java.awt.*;

public class Card {
    private String z;
    public boolean canStrike; //Strikethrough
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 50;
    private String name;
    private int health;
    private int damage;
    private int energy;
    private boolean frozen;
    private boolean decay;
    private boolean decaying;
    private boolean charge;
    private boolean canFreeze;
    private boolean hasArmor;
    Image icon;
    private int lane;
    private int playerNum;
    private int origDamage; //To reset after Frozen
    private int x;
    private int y;

    public Card(String i,int h, int d, int e, boolean de, boolean c, boolean f, boolean a, boolean s){
        health = h;
        damage = d;
        energy = e;
        icon = new ImageIcon(i).getImage();
        // icon = new ImageIcon(i).getImage();
        origDamage = this.damage;
        frozen = false;
        decay = de;
        charge = c;
        canFreeze = f;
        hasArmor = a;
        canStrike = s;
    }
    public Card(Card c){
        health = c.getHealth();
        damage = c.getDamage();
        energy = c.getEnergy();
        this.icon = c.icon;
        origDamage = c.getDamage();
        frozen = false;
        decay = c.isDecay();
        charge = c.isCharge();
        canFreeze = c.isCanFreeze();
        canStrike = c.isCanStrike();
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


    public int getLane() {
        return lane;
    }


    public void setLane(int lane) {
        this.lane = lane;
    }

    public boolean isDecaying() {
        return decaying;
    }

    public void setDecaying(boolean decaying) {
        this.decaying = decaying;
    }
    //Take Damage
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
    //Print Itself
    public void printCard(Graphics g, GameViewer w){
        //Replace with Image Icon
        g.drawRect(x, y, CARD_WIDTH,CARD_HEIGHT);
        g.drawImage(icon,x + 45,y,50,CARD_HEIGHT, w);
        g.setFont(new Font("Serif", Font.PLAIN, 15));
        g.drawString("H: " + this.health, x+10, y + 15);
        g.drawString("E: " + this.energy, x+10, y + 30);
        g.drawString("D: " + this.damage, x+10, y + 45);
    }
    // Draw Icon for lane (default square for now)
    public void drawLane(int x, int y, Graphics g, GameViewer w){
        this.x = x;
        this.y = y;
        //Replace with Image Icon
        g.drawRect(x, y, 70,30);
        g.drawImage(icon,x+30,y,30,30,w);
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        g.drawString("H: " + this.health, x+10, y+10);
        g.drawString("D: " + this.damage, x+10, y +20);
    }

    // Modified from Mr. Blick CS2FinalProjectResources
    //Check if clicked
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
    //Print Stats
    public void printStats(Graphics g){
        int stagger = 0;
        if(decay){
            g.drawString("Decay",x,y + stagger);
            stagger += 30;
        }
        if(hasArmor){
            g.drawString("Armor",x,y + stagger);
            stagger += 30;
        }
        if(canStrike){
            g.drawString("Strikethrough",x,y + stagger);
            stagger += 30;
        }
        if(canFreeze){
            g.drawString("Freeze",x,y + stagger);
            stagger += 30;
        }
        if(charge){
            g.drawString("Charger",x,y + stagger);
            stagger += 30;
        }
        if(frozen){
            g.drawString("Frozen",x,y + stagger);
            stagger += 30;
        }
        if(decaying){
            g.drawString("decaying",x,y + stagger);
        }
    }
    //Checks if Card is Dead
    public boolean checkDead(){
        if(health == 0){
            return true;
        }
        return false;
    }


}

