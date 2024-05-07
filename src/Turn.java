import java.awt.Graphics;

public class Turn {
    private String turn;
    private int radius;
    private int x;
    private int y;

    public Turn(int x, int y){
        this.x = x;
        this.y = y;
        this.radius = 30;
    }

    public void updateTurn(int phase){
        if(phase == 0){
            this.turn = "P1";
        }
        if(phase == 1){
            this.turn = "P2";
        }
        if(phase == 2){
            this.turn = "A";
        }
        if(phase == 3){
            this.turn = "B";
        }
        if(phase == 4){
            this.turn = "D1";
        }
        if(phase == 5){
            this.turn = "D2";
        }
    }
    // Modified From Mr. Blick CS2FinalProjectResources
    public void draw(Graphics g) {
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.drawString(turn,x-15,y+5);
    }
    // Modified From Mr. Blick CS2FinalProjectResources
    public boolean isClicked(int x, int y) {
        double dx = (this.x - x) * (this.x - x);
        double dy = (this.y - y) * (this.y - y);
        return Math.sqrt(dx + dy) <= radius;
    }

    public void setString(String s){
        turn = s;
    }
}
