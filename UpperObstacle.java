import java.util.Random;

public class UpperObstacle extends Obstacle {
    Random random = new Random();

    static int y = 0;
    static int x = 1200;


    public UpperObstacle(int w, int h) {
        super(x, y, w, h);
    }
}
