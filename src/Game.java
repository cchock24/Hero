import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class Game implements MouseListener, MouseMotionListener, ActionListener {
    private static ArrayList<Card> cards;
    private Card clickedCard = null;
    private GameViewer window;
    private Card[][] lane;
    private PlayerIcon[][] profile;
    private Player p1;
    private Player p2;
    private Scanner s;
    private boolean win;
    private Turn Turn;
    private int turn;
    private Timer clock;
    private TimerTask c;
    private int phase;
    private int origx;
    private int origy;
    //Phase 0 P1 Turn | Phase 1 P2 Turn | Phase 2 Attack
    private Turn dict;
    private boolean showdict;
    private boolean noEnergy;
    private boolean invalidSpot;

    public Game() {
        cards = new ArrayList<Card>();
        profile = new PlayerIcon[4][3];
        this.phase = 4;
        s = new Scanner(System.in);
        lane = new Card[5][2];
        p1 = new Player();
        p2 = new Player();
        turn = 0;
        win = false;
        this.clock = new Timer(20, this);
        window = new GameViewer(this);
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);
        Turn = new Turn(535, 552);
        clock.start();
        dict = new Turn(70, 100);
        dict.setString("Stats");
        showdict = false;
        noEnergy = false;
        invalidSpot = false;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    public PlayerIcon[][] getProfile() {
        return profile;
    }

    public void pickProfile() {
        Image icon = new ImageIcon("Resources/acetrainerf-gen6.png").getImage();
        PlayerIcon atrainer1 = new PlayerIcon(icon, 10, 20);
        profile[0][0] = atrainer1;
        icon = new ImageIcon("Resources/acetrainerf-gen6xy.png").getImage();
        PlayerIcon atrainer2 = new PlayerIcon(icon, 10, 210);
        profile[1][0] = atrainer2;
        icon = new ImageIcon("Resources/allister.png").getImage();
        PlayerIcon allister = new PlayerIcon(icon, 0, 410);
        profile[2][0] = allister;
        icon = new ImageIcon("Resources/anabel-gen7.png").getImage();
        PlayerIcon anabel = new PlayerIcon(icon, 210, 20);
        profile[0][1] = anabel;
        icon = new ImageIcon("Resources/aromalady.png").getImage();
        PlayerIcon aroma = new PlayerIcon(icon, 210, 210);
        profile[1][1] = aroma;
        icon = new ImageIcon("Resources/artist-gen4.png").getImage();
        PlayerIcon artist = new PlayerIcon(icon, 180, 410);
        profile[2][1] = artist;
        icon = new ImageIcon("Resources/ballguy.png").getImage();
        PlayerIcon ball = new PlayerIcon(icon, 410, 20);
        profile[0][2] = ball;
        icon = new ImageIcon("Resources/bugcatcher-gen4dp.png").getImage();
        PlayerIcon bug = new PlayerIcon(icon, 410, 210);
        profile[1][2] = bug;
        icon = new ImageIcon("Resources/depotagent.png").getImage();
        PlayerIcon dep = new PlayerIcon(icon, 350, 410);
        profile[2][2] = dep;
        icon = new ImageIcon("Resources/ethan-masters.png").getImage();
        PlayerIcon ethan = new PlayerIcon(icon, 410, 600);
        profile[3][2] = ethan;
        icon = new ImageIcon("Resources/gentleman.png").getImage();
        PlayerIcon gent = new PlayerIcon(icon, 210, 600);
        profile[3][1] = gent;
        icon = new ImageIcon("Resources/schoolkid-gen4dp.png").getImage();
        PlayerIcon school = new PlayerIcon(icon, 10, 600);
        profile[3][0] = school;
    }

    public void startGame() {
        Turn.updateTurn(phase);
        window.repaint();
        p1.randomDeck(cards);
        p2.randomDeck(cards);
    }

    public void run() {
        this.viewFile();
        this.pickProfile();
        this.madeDeck();
        this.startGame();
        window.repaint();
    }

    // Has P1 choose to play cards
    public void p1takeTurn() {

        System.out.println("P1 Draw and Drop a Card to Play");
        System.out.println("Click the Turn Button to End Turn");
    }

    // Has P2 choose to play cards
    public void p2takeTurn() {
        System.out.println("P2 Draw and Drop a Card to Play");
        System.out.println("Click the Turn Button to End Turn");
    }

    // Subtract Energy
    public boolean energyCost(Card c, Player p) {
        int cost = p.getEnergy() - c.getEnergy();
        if (cost >= 0) {
            p.subEnergy(c.getEnergy());
            return true;
        }
        noEnergy = true;
        window.repaint();
        return false;
    }

    // Check if Lane is Empty
    public boolean laneEmpty(int l, int p) {
        if (lane[l][p] != null) {
            System.out.println("Lane already has a card");
            return false;
        }
        return true;
    }

    public Turn getTurn() {
        return Turn;
    }

    //Make Starting Deck
    public void madeDeck() {
        Turn.updateTurn(phase);
        window.repaint();
        madeDeckP1(p1);
        madeDeckP2(p2);
    }

    public void madeDeckP1(Player p1) {

    }

    public void madeDeckP2(Player p1) {

    }

    //Make Cards for Deck
    public void initCards() {

    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public void Turn() {
        turn++;
        p1.setEnergy(turn);
        p2.setEnergy(turn);
        p1.addHand();
        p2.addHand();
        this.checkDecay();
        this.p1Charge();
        this.p2Charge();
    }

    public void addLane(Player p, int l, int index) {
        if (p == p1) {
            lane[l][0] = p.addLane(index);
        }
        if (p == p2) {
            lane[l][1] = p.addLane(index);
        }

    }

    public Card[][] getLane() {
        return lane;
    }

    public void Attack() {
        boolean dead1 = false;
        boolean dead2 = false;
        int num1 = 0;
        int num2 = 0;
        int flane1 = 0;
        int flane2 = 0;
        boolean froze2 = false;
        boolean froze = false;
        for (int j = 0; j < 5; j++) {
            if (lane[j][0] != null) {
                int damage = lane[j][0].getDamage();
                //No Damage is Frozen
                damage = this.checkFrozen(j, 0, damage);
                if (lane[j][1] != null) {
                    if (lane[j][0].isCanFreeze()) {
                        froze = true;
                        flane1 = j;
                    }
                    if(lane[j][0].isDecay()){
                        lane[j][1].setDecaying(true);
                    }
                    dead1 = lane[j][1].takeDamage(damage);
                    if (dead1) {
                        num1 = j;
                    }
                    if(lane[j][0].isCanStrike()){
                        win = p2.takeDamage(damage);
                        if (win) {
                            this.p1Win();
                        }
                    }
                }
                else {
                    win = p2.takeDamage(damage);
                    if (win) {
                        this.p1Win();
                    }
                }
            }
            if (lane[j][1] != null) {
                int damage = lane[j][1].getDamage();
                //No Damage is Frozen
                damage = this.checkFrozen(j, 1, damage);
                if (lane[j][0] != null) {
                    if (lane[j][1].isCanFreeze()) {
                        froze2 = true;
                        flane2 = j;
                    }
                    if(lane[j][1].isDecay()){
                        lane[j][0].setDecaying(true);
                    }
                    dead2 = lane[j][0].takeDamage(damage);
                    if (dead2) {
                        num2 = j;
                    }
                    if(lane[j][1].isCanStrike()){
                        win = p1.takeDamage(damage);
                        if (win) {
                            this.p2Win();
                        }
                    }
                }
                else {
                    win = p1.takeDamage(damage);
                    if (win) {
                        this.p2Win();
                    }
                }
            }
            if (froze) {
                lane[flane1][0].setFrozen(true);
            }
            if (froze2) {
                lane[flane2][1].setFrozen(true);
            }
            if (dead1) {
                lane[num1][1] = null;
            }
            if (dead2) {
                lane[num2][0] = null;
            }
        }
    }

    public void p2Win() {
        System.out.println("Player 2 Wins");
        window.checkWin(2);
        window.repaint();
    }

    public void p1Win() {
        System.out.println("Player 1 Wins");
        window.checkWin(1);
        window.repaint();
    }

    // Checks and implements based on cards special abilites
    //Applies Decay Damage;
    public void checkDecay() {
        for (int i = 0; i < lane.length; i++) {
            for (int j = 0; j < lane[0].length; j++) {
                if (lane[i][j] != null && lane[i][j].isDecaying()) {
                    lane[i][j].takeDamage(1);
                }
            }
        }
    }

    public int checkFrozen(int l, int side, int damage) {
        if (lane[l][side].isFrozen()) {
            damage = 0;
            lane[l][side].setFrozen(false);
        }
        return damage;
    }

    //MouseMotion Methods
    @Override
    public void mousePressed(MouseEvent e) {
        // Check if a Card is Clicked
        if (phase == 0) {
            int x = e.getX();
            int y = e.getY();
            clickedCard = null;
            for (Card c : getP1().getHand()) {
                if (c.isClicked(x, y) && energyCost(c, p1)) {
                    noEnergy = false;
                    invalidSpot = false;
                    clickedCard = c;
                    origx = c.getX();
                    origy = c.getY();
                    getP1().getHand().remove(c);
                }
            }
        }

        if (phase == 1) {
            int x = e.getX();
            int y = e.getY();
            clickedCard = null;
            for (Card c : getP2().getHand()) {
                if (c.isClicked(x, y) && energyCost(c, p2)) {
                    clickedCard = c;
                    getP2().getHand().remove(c);
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Check which player % 0 | Player 1
        if (clickedCard != null) {
            if (phase == 0) {
                int num = this.validSpot(clickedCard.getX(), clickedCard.getY(), 0);
                if (num != 5) {
                    lane[num][0] = clickedCard;
                    window.repaint();
                }
                if (num == 5) {
                    p1.addEnergy(clickedCard.getEnergy());
                    p1.addHand(clickedCard);
                    clickedCard.setY(origy);
                    clickedCard.setX(origx);
                    window.repaint();
                }
            }
            if (phase == 1) {
                int num = this.validSpot(clickedCard.getX(), clickedCard.getY(), 1);
                if (num != 5) {
                    lane[num][1] = clickedCard;
                    window.repaint();
                }
                if (num == 5) {
                    invalidSpot = true;
                    p2.addEnergy(clickedCard.getEnergy());
                    p2.addHand(clickedCard);
                    clickedCard.setY(origy);
                    clickedCard.setX(origx);
                    window.repaint();
                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (phase == 4) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (profile[i][j].isClicked(x, y)) {
                        p1.setIcon(profile[i][j].getIcon());
                    }
                }
            }
        }
        if (phase == 5) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (profile[i][j].isClicked(x, y)) {
                        p2.setIcon(profile[i][j].getIcon());
                    }
                }
            }
        }
        if (Turn.isClicked(x, y)) {
            this.incrementPhase();
            if (phase == 0) {
                phase0();
            }
            if (phase == 1) {
                phase1();
            }
            if (phase == 2) {
                phase2();
            }
            if (phase == 3) {
                phase3();
            }
            if (phase == 4) {
                phase4();
            }
            if (phase == 5) {
                phase5();
            }
        }

        if (dict.isClicked(x, y)) {
            if (showdict) {
                showdict = false;
            } else {
                showdict = true;
            }
            window.repaint();

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    // Modified from Mr. Blick CS2FinalProjectResources
    @Override
    public void mouseDragged(MouseEvent e) {
        // Ask the input event the current location (x and y position on the Frame) of the mouse
        int x = e.getX();
        int y = e.getY();

        // If the card is clicked
        if (clickedCard != null) {
            // Move the Card
            clickedCard.setX(x);
            clickedCard.setY(y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //     window.repaint();
    }

    public int validSpot(int x, int y, int p) {
        if (y > 475) {
            return 5;
        }
        if(p == 0){
            if (x >= 0 && x <= 130) {
                if (lane[0][0] == null) {
                    return 0;
                }
            } else if (x >= 130 && x <= 240) {
                if (lane[1][0] == null) {
                    return 1;
                }
            } else if (x >= 240 && x <= 350) {
                if (lane[2][0] == null) {
                    return 2;
                }
            } else if (x >= 350 && x <= 480) {
                if (lane[3][0] == null) {
                    return 3;
                }
            } else if (x >= 480 && x <= 600) {
                if (lane[4][0] == null) {
                    return 4;
                }
            }
        }
        if(p == 1){
            if (x >= 0 && x <= 130) {
                if (lane[0][1] == null) {
                    return 0;
                }
            } else if (x >= 130 && x <= 240) {
                if (lane[1][1] == null) {
                    return 1;
                }
            } else if (x >= 240 && x <= 350) {
                if (lane[2][1] == null) {
                    return 2;
                }
            } else if (x >= 350 && x <= 480) {
                if (lane[3][1] == null) {
                    return 3;
                }
            } else if (x >= 480 && x <= 600) {
                if (lane[4][1] == null) {
                    return 4;
                }
            }
        }
        return 5;
    }

    public void incrementPhase() {
        if (phase == 0) {
            phase = 3;
        } else if (phase == 3) {
            phase = 1;
        } else if (phase == 2) {
            phase = 0;
        } else if (phase == 4) {
            phase = 5;
        } else if (phase == 5) {
            phase = 0;
        } else {
            phase++;
        }
    }

    public void phase1() {
        Turn.updateTurn(phase);
        invalidSpot = false;
        noEnergy = false;
        this.p2takeTurn();
        window.repaint();
    }

    public void phase2() {
        Turn.updateTurn(phase);
        invalidSpot = false;
        noEnergy = false;
        this.Attack();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 2; j++){
                if(lane[i][j].getHealth() == 0){
                    lane[i][j] = null;
                }
            }
        }
        window.repaint();
    }

    public void phase0() {
        Turn();
        Turn.updateTurn(phase);
        invalidSpot = false;
        noEnergy = false;
        this.p1takeTurn();
        window.repaint();
    }

    public void phase3() {
        Turn.updateTurn(phase);
        invalidSpot = false;
        noEnergy = false;
        window.repaint();
    }

    public int getPhase() {
        return phase;
    }

    public boolean getshowdict() {
        return showdict;
    }

    public Turn getdict() {
        return dict;
    }

    public boolean notEnergy() {
        return noEnergy;
    }

    public void phase4() {
        Turn.updateTurn(phase);
        window.repaint();
    }

    public void phase5() {
        Turn.updateTurn(phase);
        window.repaint();
    }

    //Taken and Edited from GeeksforGeeks
    public void viewFile() {

        // Try block to check for exceptions
        try {
            // Creating object of File class to get file
            // path
            File myObj = new File("HeroCards.txt");

            myObj.createNewFile();

            if (myObj.length() != 0) {
                Scanner myReader = new Scanner(myObj);
                myReader.useDelimiter(",");
                while (myReader.hasNextLine()) {
                    String str = myReader.nextLine();

                    // trim spaces
                    str = str.replaceAll("\\s", "");
                    String[] splitString = str.split(",");
                    Card c = new Card(splitString[1],parseInt(splitString[2]), parseInt(splitString[3]), parseInt(splitString[4]), parseBoolean(splitString[5]), parseBoolean(splitString[6]), parseBoolean(splitString[7]), parseBoolean(splitString[8]), parseBoolean(splitString[9]));
                    cards.add(c);
                }
                myReader.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void p1Charge(){
        for(Card c: p1.getHand()){
            if(c.isCharge()){
                p1.addEnergy(1);
            }
        }
    }
    public void p2Charge(){
        for(Card c: p2.getHand()){
            if(c.isCharge()){
                p2.addEnergy(1);
            }
        }
    }
    public boolean notValidSpot(){
        return invalidSpot;
    }
}
