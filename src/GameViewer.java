import javax.swing.*;
import java.awt.*;

public class GameViewer extends JFrame {

    boolean win1 = false;
    boolean win2 = false;
    private final int WINDOWN_WIDTH = 600;
    private final int WINDOOW_HEIGHT = 800;

    Game g;

    Image board;

    public GameViewer(Game g){
        this.g = g;
        board = new ImageIcon("Resources/Board3.png").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Hero");
        this.setSize(WINDOWN_WIDTH, WINDOOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics gr){
        this.Reset(gr);
        g.getP1().printHand(gr);
        this.printLane(g.getLane(), gr);
        if(win1){
            this.printWin1(gr);
        }
        if(win2){
            this.printWIN2(gr);
        }
    }

    //Set opposite side health
    public void setHealthop(Graphics g, Player p){
        g.setFont(new Font("Serif", Font.PLAIN, 35));
        g.drawString(Integer.toString(p.getHealth()), 430,110);
    }

    //Set your health
    public void setHealthyou(Graphics gr, Player p){
        gr.setFont(new Font("Serif", Font.PLAIN, 35));
        gr.drawString(Integer.toString(p.getHealth()), 410, 560);
    }

    public void setEnergyop(Graphics g, Player p){
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 540, 110);
    }

    public void setEnergyyou(Graphics g, Player p){
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(Integer.toString(p.getEnergy()), 50, 560);
    }
    public void Reset(Graphics gr){
        gr.setColor(Color.white);
        gr.fillRect(0,0,600,800);
        gr.setColor(Color.black);
        gr.drawImage(board, 0,37, 600,750, this);
        this.setHealthop(gr, g.getP2());
        this.setHealthyou(gr, g.getP1());
        this.setEnergyop(gr, g.getP2());
        this.setEnergyyou(gr, g.getP1());
    }

    public void printLane(Card[][] l, Graphics g){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 5; j++){
                if(l[j][i] != null && i == 0){
                    l[j][i].drawLane( 35 + (j*120), 400,g);
                }
                if(l[j][i] != null && i == 1){
                    l[j][i].drawLane( 35 + (j*120), 200,g);
                }
            }
        }
    }
    public void checkWin(int n){
        if(n==1){
            win1 = true;
        }
        if(n==2){
            win2 = true;
        }
    }

    public void printWin1(Graphics gr){
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 1 Wins!",100,300);
    }

    public void printWIN2(Graphics gr){
        gr.setFont(new Font("Serif", Font.PLAIN, 50));
        gr.drawString("Player 2 Wins!",100,300);
    }
}
