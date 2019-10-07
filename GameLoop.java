import java.awt.*;
import java.util.Random;

public class GameLoop extends GameBase {
    Rect player;
    int mvCount = 0;
    int spCount = 0;
    int tmCount = 0;
    int speed = 10;

    boolean gameOver = false;

    Random random = new Random();

    Obstacle obstacle1;
    Obstacle obstacle2;
    Obstacle[] obstacles = new Obstacle[2];

    @Override
    public void initialize() {

        player = new Rect(100, 400, 100, 100);

        genObstacles();


    }

    @Override
    public void inTheGameLoop() {



        if(!gameOver) {
            mvCount++;
            spCount++;
            tmCount++;

            if(spCount == 900){speed += 2;}

            if (mvCount == 2) {
                player.moveDn(5);
                mvCount = 0;
            }
            if (released[SP]) {
                player.moveUp(50);
                released[SP] = false;
            }

            moveObstacles();

            if (obstacle2.x < 0 - obstacle2.w) {
                genObstacles();
            }

            collisionDetect();
        }

        if(released[EN]){gameOver = false; genObstacles(); player.x =100; player.y= 400; tmCount = 0; released[EN] = false;}



    }

    private void collisionDetect() {


        if(obstacle1.overlaps(player)) {gameOver = true; System.out.println("COLLIDED OB1");}
        if(obstacle2.overlaps(player)) {gameOver = true; System.out.println("COLLIDED OB2");}

        if(player.y + player.h > 699) gameOver=true;



    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        player.draw(g);
        drawObstacles(g);
        g.drawString("Current Time: " + Integer.toString(tmCount/60), 800, 50);

        if(gameOver) g.drawString("GAME OVER!", player.x, player.y - 10);

    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private void genObstacles(){
        int y;
        int w;
        int h;

        w = getRandomNumberInRange(100,300);
        h = getRandomNumberInRange(200,400);
        obstacle1 = new UpperObstacle(w, h);


        y = getRandomNumberInRange(400,600);
        w = getRandomNumberInRange(100,300);
        h = 800 - y;
        obstacle2 = new LowerObstacle(y, w, h);

        obstacles[0] = obstacle1;
        obstacles[1] = obstacle2;

    }

    private void moveObstacles(){
        if (obstacle1.x > 0 - obstacle1.w) {
            obstacle1.moveLt(speed);
        }

        if (obstacle1.x < 500) {
            obstacle2.moveLt(speed);
        }
    }

    private void drawObstacles(Graphics g){
        for(int i=0;i<2;i++){
            obstacles[i].draw(g);
        }
    }
}
