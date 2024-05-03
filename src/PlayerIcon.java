import javax.swing.*;
import java.awt.*;

public class PlayerIcon {

    private Image icon;
    private int x;
    private int y;

    private int iconLength = 200;
    private int iconWidth = 200;

    public PlayerIcon(Image i, int x, int y){
        icon = i;
        this.x = x;
        this.y = y;
    }

    public Image getIcon(){
        return icon;
    }

    public boolean isClicked(int x, int y){
        if(x >= this.x && x <= this.x+iconWidth && y >= this.y && y <= this.y+iconLength){
            return true;
        }
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void drawYourself(Graphics gr, GameViewer w){
        gr.drawImage(icon, x, y, iconWidth, iconLength, w);
    }
}
