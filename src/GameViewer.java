import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame {

    boolean win1 = false;
    boolean win2 = false;
    private final int WINDOWN_WIDTH = 600;
    private final int WINDOOW_HEIGHT = 800;

    Game g;

    Image board;
    int phase = 0;

    public GameViewer(Game g) {
        this.g = g;
        board = new ImageIcon("Resources/Board3.png").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Hero");
        this.setSize(WINDOWN_WIDTH, WINDOOW_HEIGHT);
        this.setVisible(true);
        phase = g.getPhase();
    }

    public void paint(Graphics gr) {
        //Profile Pic Selection
        phase = g.getPhase();
        if(phase == 4){
            startGame(gr, g.getP1());
            g.getTurn().draw(gr);
        }
        if(phase == 5){
            startGame(gr, g.getP2());
            g.getTurn().draw(gr);
        }
        //Normal Game Phase
        if(phase != 5 && phase != 4){
            this.Reset(gr);
            gr.setColor(Color.black);
            g.getP1().drawIcon(gr,this,0);
            g.getP2().drawIcon(gr,this,1);
            if(g.notEnergy()){
                this.noEnergy(gr);
            }
            if(g.notValidSpot()){
                this.invalidSpot(gr);
            }
            //Print P1 Hand
            if (phase == 0) {
                gr.setColor(Color.white);
                gr.fillRect(15,620,560,150);
                gr.setColor(Color.BLACK);
                gr.drawLine(0,475,600,475);
                g.getP1().printHand(gr, this);
                if(g.getshowdict()){
                    this.printDict(gr,g.getP1());
                }
            }
            //Print P2 Hand
            if (phase == 1) {
                gr.setColor(Color.white);
                gr.fillRect(15,620,560,150);
                gr.setColor(Color.BLACK);
                g.getP2().printHand(gr, this);
                if(g.getshowdict()){
                    this.printDict(gr,g.getP2());
                }
            }
            if (phase == 3) {

            }
            this.printLane(g.getLane(), gr);
            //Print Win
            if (win1) {
                this.printWin1(gr);
            }
            if (win2) {
                this.printWIN2(gr);
            }
        }

    }

    //Set opposite side health
    public void setHealthop(Graphics g, Player p) {
        g.setFont(new Font("Serif", Font.PLAIN, 35));
        g.drawString(Integer.toString(p.getHealth()), 430, 110);
    }

    //Set your health
    public void setHealthyou(Graphics gr, Player p) {
        gr.setFont(new Font("Serif", Font.PLAIN, 35));
        gr.drawString(Integer.toString(p.getHealth()), 410, 560);
    }
    //Set opposite side energy
    public void setEnergyop(Graphics g, Player p) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 540, 110);
    }
    //Set your energy
    public void setEnergyyou(Graphics g, Player p) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 50, 560);
    }
    //Resets Screen, Then draws in Board and player stats
    public void Reset(Graphics gr) {
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        gr.drawImage(board, 0, 37, 600, 750, this);
        this.setHealthop(gr, g.getP2());
        this.setHealthyou(gr, g.getP1());
        this.setEnergyop(gr, g.getP2());
        this.setEnergyyou(gr, g.getP1());
        g.getTurn().draw(gr);
        g.getdict().draw(gr);
    }
    //Draw Cards in Lanes
    public void printLane(Card[][] l, Graphics g) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (l[j][i] != null && i == 0) {
                    l[j][i].drawLane(35 + (j * 120), 400, g,this);
                }
                if (l[j][i] != null && i == 1) {
                    l[j][i].drawLane(35 + (j * 120), 200, g,this);
                }
            }
        }
    }
    //Check win
    public void checkWin(int n) {
        if (n == 1) {
            win1 = true;
        }
        if (n == 2) {
            win2 = true;
        }
    }
    //Draw P1 Win
    public void printWin1(Graphics gr) {
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 1 Wins!", 100, 300);
    }
    //Draw P2 Win
    public void printWIN2(Graphics gr) {
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 2 Wins!", 100, 300);
    }
    //Draw Buffer/Blank Image
    public void printBuf(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(15, 620, 580, 780);
        g.setColor(Color.black);
    }
    //Draw Stats
    public void printDict(Graphics gr, Player p) {
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 2; j++){
                if(g.getLane()[i][j] != null){
                    g.getLane()[i][j].printStats(gr);
                }
            }
        }
        for (Card c : p.getHand()) {
            if(c != null){
                c.printStats(gr);
            }
        }
    }
    public void noEnergy(Graphics g){
        g.drawString("Not Enough Energy", 200,500);
    }
    public void invalidSpot(Graphics g){
        g.drawString("Invalid Spot", 200,500);
    }

    //Draw Profile Picking stage
    public void startGame(Graphics gr, Player p){
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        if(p == g.getP1()){
            gr.drawString("P1 pick Character", 160, 100);
        }
        else{
            gr.drawString("P2 pick Character", 160, 100);
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0 ; j < 3; j++){
                g.getProfile()[i][j].drawYourself(gr,this);
            }
        }
    }
}
