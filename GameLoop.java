import java.awt.*;

public class GameLoop extends GameBase {
    Rect testRect;
    int count = 0;

    @Override
    public void initialize() {

        testRect = new Rect(500, 400, 100, 100);


    }

    @Override
    public void inTheGameLoop() {
        count++;
        if(count==4){testRect.moveDn(10); count = 0;}
        if(released[SP]){testRect.moveUp(10); released[SP] = false;}
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        testRect.draw(g);

    }
}
