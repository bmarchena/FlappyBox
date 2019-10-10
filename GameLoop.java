/*
    FlappyBox by B. Marchena
*/
import java.awt.*;

public class GameLoop extends GameBase {
    //gravity and jump constants
    private final static int GRAV_SPEED = 5;
    private static final int JUMP_HEIGHT = 110;

    //player character
    private Rect player;

    //initializing counters
    private int spCount = 0;
    private int tmCount = 0;

    //initialize game conditions
    private int lvl = 1;
    private int recTm;
    private int score = -2;
    private int hiscore = 0;

    private String streak = "";

    //initial speed of the obstacles
    private int speed = 10;

    //game over condition
    private boolean gameOver = false;

    //initializing obstacle array
    private Obstacle[] obstacles = new Obstacle[2];

    //fonts for drawing title and stats
    private Font titleFont = new Font("Courier New", 1, 17);


    @Override
    public void initialize() {
        //initialize player
        player = new Rect(100, 400, 30, 30);

        //create obstacles
        genObstacles();
    }

    @Override
    public void inTheGameLoop() {
        //while the game isn't over
        if(!gameOver) {
            //increment counters
            spCount++;
            tmCount++;

            //pull player down
            player.moveDn(GRAV_SPEED);

            //if the speed counter = 600 (7 - 10 sec generally) increase speed and level
            if(spCount == 600){speed++; lvl++; spCount = 0;}



            //check if player jumped
            if(playerJump()) player.moveUp(JUMP_HEIGHT);

            //move obstacles across the screen
            moveObstacles();

            //check for collisions
            detectCollisions();
        }

        //if game over, press enter to restart
        if(released[EN] && gameOver){restartGame();}



    }

    private void detectCollisions() {
        //if player hits an obstacle, game over
        if(obstacles[0].overlaps(player))  gameOver = true;
        if(obstacles[1].overlaps(player))  gameOver = true;

        //if player hits the floor or ceiling, game over
        if(player.y < 50)                  gameOver = true;
        if(player.y + player.h > 680)      gameOver = true;

        //if upper obstacle leaves the screen, generate a new one
        if (obstacles[0].x <= 0 - obstacles[0].w) {
            genUpperObstacle();
        }

        //if lower obstacle leaves the screen, generate a new one
        if (obstacles[1].x < 0 - obstacles[1].w) {
            genLowerObstacle();
        }
    }

    public void restartGame(){
        //reinitialize game conditions
        gameOver = false;
        score = -2;
        speed = 10;
        player.x =100;
        player.y= 400;
        tmCount = 0;
        lvl = 1;
        released[EN] = false;
        genObstacles();
    }

    @Override
    public void paint(Graphics g) {
        //paint all objects and ui
        super.paint(g);
        player.draw(g);
        drawObstacles(g);
        showStats(g);

    }

    private static int getRandomNumberInRange(int min, int max) {
        //used to generate random parameters for obstacles
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private boolean playerJump(){
        //checks if player jumped
        if (released[SP]) {
            released[SP] = false;
            return true;
        }
        return false;
    }

    public void showStats(Graphics g){
        //calculate time for display
        int tm = tmCount / 60;

        //display current level count
        g.drawString("Level " + lvl, 50, 20);

        //setting title in distinct font
        Font current = g.getFont();
        g.setFont(titleFont);
        g.drawString("FlappyBox", 450, 30);
        g.setFont(current);

        //display scores
        g.drawString("Score:   " + score, 730, 20);
        g.drawString("High Score: " + hiscore, 730, 40);

        //display times
        g.drawString("Current Time: " + tm, 850, 20);
        g.drawString("Record Time:  " + recTm, 850, 40);


        //scorestreaks
        if(lvl == 1 && !gameOver) streak = "Amateur hour.";
        if(lvl == 2 && !gameOver) streak = "Your first steps.";
        if(lvl == 3 && !gameOver) streak = "Not bad.";
        if(lvl == 4 && !gameOver) streak = "Now we're getting somewhere.";
        if(lvl == 5 && !gameOver) streak = "You're pretty good.";
        if(lvl == 6 && !gameOver) streak = "Kickin' it up a notch.";
        if(lvl == 7 && !gameOver) streak = "Eyes on the prize.";
        if(lvl == 8 && !gameOver) streak = "Zoom, zoom, zoom.";
        if(lvl == 9 && !gameOver) streak = "It's all uphill from here.";
        if(lvl >= 10 && !gameOver) streak = "Now THIS is podracing.";

        //display scorestreak
        g.drawString(streak,50,40);

        //game over 'streak'
        if(gameOver) streak = "Womp, womp.";

        //display game over status
        if(gameOver) g.drawString("GAME OVER!", player.x, player.y - 10);

        //record best time and score
        if(tm > recTm) recTm = tm;
        if(score > hiscore) hiscore = score;
    }

    private void genUpperObstacle(){
        int w;
        int h;

        w = getRandomNumberInRange(100,300); // min width: 100; max width: 300
        h = getRandomNumberInRange(200,400); // min height: 200; max height: 400
        obstacles[0] = new UpperObstacle(w, h);
        score++;

    }

    private void genLowerObstacle(){
        int y;
        int w;
        int h;

        y = getRandomNumberInRange(400,600); //min y: 400; max y: 600
        w = getRandomNumberInRange(100,300); //min width: 100; max width: 300
        h = 680 - y; //height is set to the remaining length between y and floor
        obstacles[1] = new LowerObstacle(y, w, h);
        score++;

    }

    private void genObstacles(){
        genUpperObstacle();
        genLowerObstacle();
    }

    private void moveObstacles(){
        //if the upper obstacle isn't off the screen, move it
        if (obstacles[0].x > 0 - obstacles[0].w) {
            obstacles[0].moveLt(speed);
        }

        // if the upper obstacle is halfway across OR
        // the lower obstacle is already on screen, move the lower obstacle.
        if (obstacles[0].x < 550 || obstacles[1].x<1000) {
            obstacles[1].moveLt(speed);
        }


    }

    private void drawObstacles(Graphics g){
        //draw each obstacle
        for(int i=0;i<2;i++){
            obstacles[i].draw(g);
        }

        //draw the floor and ceiling
        g.drawLine(0,50,1000,50); //ceiling
        g.drawLine(0,680,1000,680); //floor
    }
}
