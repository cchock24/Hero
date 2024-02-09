import java.util.Scanner;

public class Game {

    private GameViewer window;
    private Card[][] lane;
    private Player p1;
    private Player p2;
    private Scanner s;

    private int turn;


    public Game(){
        s = new Scanner(System.in);
        lane = new Card[5][2];
        System.out.println("Player 1 name");
        String n = s.nextLine();
        p1 = new Player(n);
        System.out.println("Player 2 name");
        n = s.nextLine();
        p2 = new Player(n);
        turn = 0;
        window = new GameViewer(this);
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    public void run(){
        window.repaint();
    }
    public void takeTurn(){

    }
    //Print Board
    public void makeBoard(){

    }
    //Make Starting Deck
    public void madeDeck(){

    }
    //Make Cards for Deck
    public void initCards(){

    }
    //If hand less 10 Card add random card from deck to hand
    public void handAdd(){

    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public void Turn(){
        turn++;
    }
}
