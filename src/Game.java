import java.util.Scanner;

public class Game {

    private GameViewer window;
    private Card[][] lane;
    private Player p1;
    private Player p2;
    private Scanner s;

    private boolean win;

    private int turn;


    public Game(){
        s = new Scanner(System.in);
        lane = new Card[5][2];
        p1 = new Player();
        p2 = new Player();
        turn = 0;
        win = false;
        window = new GameViewer(this);
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    public void run(){
        while(!win){
            window.repaint();
            p1.randomDeck();
            p2.randomDeck();
            Turn();
            this.p1takeTurn();
            this.p2takeTurn();
            this.Attack();
        }

    }

    // Has P1 choose to play cards
    public void p1takeTurn(){
        System.out.println("Do You want to Play a Card (Insert index) If no Insert -1");
        int move = s.nextInt();
        s.nextLine();
        System.out.println("What Row (0-4)");
        int spot = s.nextInt();
        s.nextLine();
        if(move != -1 && move <= 9){
                if(this.energyCost(p1.getHand().get(move), p1) && laneEmpty(spot, 0)){
                this.addLane(p1, spot, move);
                }
                else{
                    this.p1takeTurn();
                }
        }

    }

    // Has P2 choose to play cards
    public void p2takeTurn(){
        System.out.println("Do You want to Play a Card (Insert index) If no Insert -1");
        int move = s.nextInt();
        s.nextLine();
        System.out.println("What Row (0-4)");
        int spot = s.nextInt();
        s.nextLine();
        if (move != -1 && move <= 9) {
            if(this.energyCost(p2.getHand().get(move), p2) && this.laneEmpty(spot, 1)) {
                this.addLane(p1, spot, move);
                }
            else{
                this.p2takeTurn();
            }
        }
    }

    // Subtract Energy
    public boolean energyCost(Card c, Player p){
        int cost = p.getEnergy() - c.getEnergy();
        if(cost >= 0){
            p.subEnergy(c.getEnergy());
            return true;
        }
        System.out.println("Not Enough Energy");
        return false;
    }
    // Check if Lane is Empty
    public boolean laneEmpty(int l, int p){
        if(lane[l][p] != null){
            System.out.println("Lane already has a card");
            return false;
        }
        return true;
    }

    //Make Starting Deck
    public void madeDeck(){

    }
    //Make Cards for Deck
    public void initCards(){

    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public void Turn(){
        turn++;
        p1.addEnergy(1);
        p2.addEnergy(1);
        p1.addHand();
        p2.addHand();
    }

    public void addLane(Player p, int l, int index){
        if(p == p1){
            lane[l][0] = p.addLane(index);
        }
        if(p == p2){
            lane[l][1] = p.addLane(index);
        }

    }

    public Card[][] getLane() {
        return lane;
    }

    public void Attack(){
            for(int j = 0; j < 5; j++){
                if(lane[j][0] != null){
                    int damage = lane[j][0].getDamage();
                    if(lane[j][1] != null){
                        boolean dead = lane[j][1].takeDamage(damage);
                        if(dead){
                            lane[j][1] = null;
                        }
                    }
                    else{
                        win = p2.takeDamage(damage);
                        if(win){
                            this.p1Win();
                        }
                    }
                }
                if(lane[j][1] != null){
                    int damage = lane[j][1].getDamage();
                    if(lane[j][0] != null){
                        boolean dead = lane[j][0].takeDamage(damage);
                        if(dead){
                            lane[j][0] = null;
                        }
                    }
                    else{
                        win = p1.takeDamage(damage);
                        if(win){
                            this.p2Win();
                        }
                    }
                }
            }
    }

    public void p2Win(){
        System.out.println("Player 2 Wins");
        window.printWin();
    }
    public void p1Win(){
        System.out.println("Player 1 Wins");
        window.printWin();
    }
}
