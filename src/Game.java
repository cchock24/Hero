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
        window.repaint();
        p1.randomDeck();
        p2.randomDeck();
        while(!win){
            window.repaint();
            Turn();

            this.p1takeTurn();
            window.repaint();
            this.p2takeTurn();
            window.repaint();
            this.Attack();
        }

    }

    // Has P1 choose to play cards
    public void p1takeTurn(){
        System.out.println("P1 Do You want to Play a Card (Insert index) If no Insert -1");
        int move = s.nextInt();
        s.nextLine();
        while(move != -1){
            System.out.println("What Row (0-4)");
            int spot = s.nextInt();
            s.nextLine();
            //Talk Mr.Blick how to check if null w/o going into it
            if(move >= 0 && move <= 9 && p1.getHand().get(move) != null){
                if(this.energyCost(p1.getHand().get(move), p1) && laneEmpty(spot, 0)){
                    this.addLane(p1, spot, move);
                    window.repaint();
                }
                else{
                    this.p1takeTurn();
                }
            }
            System.out.println("P1 Do You want to Play a Card (Insert index) If no Insert -1");
            move = s.nextInt();
            s.nextLine();
        }
    }

    // Has P2 choose to play cards
    public void p2takeTurn(){
        System.out.println("P2 Do You want to Play a Card (Insert index) If no Insert -1");
        int move = s.nextInt();
        s.nextLine();
        while(move != -1){
            System.out.println("What Row (0-4)");
            int spot = s.nextInt();
            s.nextLine();
            if (move >= 0 && move <= 9 && p2.getHand().get(move) != null) {
                if(this.energyCost(p2.getHand().get(move), p2) && this.laneEmpty(spot, 1)) {
                    this.addLane(p2, spot, move);
                    window.repaint();
                }
                else{
                    this.p2takeTurn();
                }
            }
            System.out.println("P2 Do You want to Play a Card (Insert index) If no Insert -1");
            move = s.nextInt();
            s.nextLine();
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
        p1.setEnergy(turn);
        p2.setEnergy(turn);
        p1.addHand();
        p2.addHand();
        this.checkDecay();
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
        boolean dead1 = false;
        boolean dead2 = false;
        int num1=0;
        int num2=0;
        for(int j = 0; j < 5; j++){
                if(lane[j][0] != null){
                    int damage = lane[j][0].getDamage();
                    //No Damage is Frozen
                    if(lane[j][0].isFrozen()){
                        damage = 0;
                        lane[j][0].setFrozen(false);
                    }
                    if(lane[j][1] != null){
                        dead1 = lane[j][1].takeDamage(damage);
                        if(dead1){
                            num1 = j;
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
                    //No Damage is Frozen
                    if(lane[j][1].isFrozen()){
                        damage = 0;
                        lane[j][1].setFrozen(false);
                    }
                    if(lane[j][0] != null){
                        dead2 = lane[j][0].takeDamage(damage);
                        if(dead2){
                            num2 = j;
                        }
                    }
                    else{
                        win = p1.takeDamage(damage);
                        if(win){
                            this.p2Win();
                        }
                    }
                }
                if(dead1){
                    lane[num1][1] = null;
                }
                if(dead2){
                    lane[num2][0] = null;
                }
            }
    }

    public void p2Win(){
        System.out.println("Player 2 Wins");
        window.checkWin(2);
        window.repaint();
    }
    public void p1Win(){
        System.out.println("Player 1 Wins");
        window.checkWin(1);
        window.repaint();
    }
    // Checks and implements based on cards special abilites
    //Applies Decay Damage;
    public void checkDecay(){
        for(int i = 0; i < lane.length; i++){
            for(int j = 0; j <lane[0].length; j++){
                if(lane[i][j].isDecay()){
                    lane[i][j].takeDamage(1);
                }
            }
        }
    }
}
