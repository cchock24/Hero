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
        phase = g.getPhase();
        if(phase == 4){
            startGame(gr, g.getP1());
            g.getTurn().draw(gr);
        }
        if(phase == 5){
            startGame(gr, g.getP2());
            g.getTurn().draw(gr);
        }
        if(phase != 5 && phase != 4){
            this.Reset(gr);
            gr.setColor(Color.black);
            if(g.notEnergy()){
                this.noEnergy(gr);
            }
            if (phase == 0) {
                g.getP1().printHand(gr);
                if(g.getshowdict()){
                    this.printDict(gr,g.getP1());
                }
            }
            if (phase == 1) {
                g.getP2().printHand(gr);
                if(g.getshowdict()){
                    this.printDict(gr,g.getP2());
                }
            }
            if (phase == 3) {

            }
            this.printLane(g.getLane(), gr);

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

    public void setEnergyop(Graphics g, Player p) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 540, 110);
    }

    public void setEnergyyou(Graphics g, Player p) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 50, 560);
    }

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

    public void printLane(Card[][] l, Graphics g) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (l[j][i] != null && i == 0) {
                    l[j][i].drawLane(35 + (j * 120), 400, g);
                }
                if (l[j][i] != null && i == 1) {
                    l[j][i].drawLane(35 + (j * 120), 200, g);
                }
            }
        }
    }

    public void checkWin(int n) {
        if (n == 1) {
            win1 = true;
        }
        if (n == 2) {
            win2 = true;
        }
    }

    public void printWin1(Graphics gr) {
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 1 Wins!", 100, 300);
    }

    public void printWIN2(Graphics gr) {
        gr.setColor(Color.white);
        gr.fillRect(0, 0, 600, 800);
        gr.setColor(Color.black);
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 2 Wins!", 100, 300);
    }

    public void printBuf(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(15, 620, 580, 780);
        g.setColor(Color.black);
    }

    public void printDict(Graphics gr, Player p) {
        for (Card c : g.getLane()[0]) {
            if(c != null){
                c.printStats(gr);
            }
        }
        for (Card c : g.getLane()[1]) {
            if(c != null) {
                c.printStats(gr);
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
        Image icon = new ImageIcon("Resources/acetrainerf-gen6.png").getImage();
        PlayerIcon atrainer1 = new PlayerIcon(icon, 10, 20);
        atrainer1.drawYourself(gr, this);
        icon = new ImageIcon("Resources/acetrainerf-gen6xy.png").getImage();
        PlayerIcon atrainer2 = new PlayerIcon(icon, 10, 210);
        atrainer2.drawYourself(gr,this);
        icon = new ImageIcon("Resources/allister.png").getImage();
        PlayerIcon allister = new PlayerIcon(icon, 10, 410);
        allister.drawYourself(gr,this);
        icon = new ImageIcon("Resources/anabel-gen7.png").getImage();
        PlayerIcon anabel = new PlayerIcon(icon, 210, 20);
        anabel.drawYourself(gr,this);
        icon = new ImageIcon("Resources/aromalady.png").getImage();
        PlayerIcon aroma = new PlayerIcon(icon, 210, 210);
        aroma.drawYourself(gr,this);
        icon = new ImageIcon("Resources/artist-gen4.png").getImage();
        PlayerIcon artist = new PlayerIcon(icon, 210, 410);
        artist.drawYourself(gr,this);
        icon = new ImageIcon("Resources/ballguy.png").getImage();
        PlayerIcon ball = new PlayerIcon(icon, 410, 20);
        ball.drawYourself(gr,this);
        icon = new ImageIcon("Resources/bugcatcher-gen4dp.png").getImage();
        PlayerIcon bug = new PlayerIcon(icon, 410, 210);
        bug.drawYourself(gr,this);
        icon = new ImageIcon("Resources/depotagent.png").getImage();
        PlayerIcon dep = new PlayerIcon(icon, 410, 410);
        dep.drawYourself(gr,this);
        icon = new ImageIcon("Resources/ethan-masters.png").getImage();
        PlayerIcon ethan = new PlayerIcon(icon, 410, 600);
        ethan.drawYourself(gr,this);
        icon = new ImageIcon("Resources/gentleman.png").getImage();
        PlayerIcon gent = new PlayerIcon(icon, 210, 600);
        gent.drawYourself(gr,this);
        icon = new ImageIcon("Resources/schoolkid-gen4dp.png").getImage();
        PlayerIcon school = new PlayerIcon(icon, 10, 600);
        school.drawYourself(gr,this);
    }
}
