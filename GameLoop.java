import java.awt.*;

public class GameLoop extends GameBase {
    //gravity and jump constants
    final static int GRAV_SPEED = 4;
    private static final int JUMP_HEIGHT = 100;

    //player character
    Rect player;

    //initializing counters
    int mvCount = 0;
    int spCount = 0;
    int tmCount = 0;

    //initialize game conditions
    int lvl = 1;
    int tm;
    int recTm;
    int score = -2;
    int hiscore = 0;

    //initial speed of the obstacles
    int speed = 10;


    //game over condition
    boolean gameOver = false;

    //intializing obstacle array
    Obstacle[] obstacles = new Obstacle[2];


    //fonts for drawing title and stats
    Font titleFont = new Font("Courier New", 1, 17);
    Font current;


    @Override
    public void initialize() {
        //initialize player
        player = new Rect(100, 400, 50, 50);

        //create obstacles
        genObstacles();


    }

    @Override
    public void inTheGameLoop() {
        //while the game isn't over
        if(!gameOver) {
            //increment counters
            mvCount++;
            spCount++;
            tmCount++;

            //pull player down
            player.moveDn(GRAV_SPEED);

            //if the speed counter = 600 (10 sec) increase speed and level
            if(spCount == 600){speed += 1; lvl++; spCount = 0;}


            //check if player jumped
            playerJump();

            //move obstacles across the screen
            moveObstacles();

            //check for collisions
            collisionDetect();
        }

        //if game over, press enter to restart
        if(released[EN] && gameOver){restartGame();}



    }

    private void collisionDetect() {
        //if player hits an obstacle, game over
        if(obstacles[0].overlaps(player))  gameOver = true;
        if(obstacles[1].overlaps(player))  gameOver = true;

        //if player hits the floor or ceiling, game over
        if(player.y < 50)                  gameOver = true;
        if(player.y + player.h > 680)      gameOver = true;
    }

    public void restartGame(){
        
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
        tm = tmCount/60;
        super.paint(g);
        player.draw(g);
        drawObstacles(g);
        showStats(g);

    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private void playerJump(){
        if (released[SP]) {
            player.moveUp(JUMP_HEIGHT);
            released[SP] = false;
        }
    }

    public void showStats(Graphics g){
        //display level
        g.drawString("Level " + Integer.toString(lvl), 50, 20);

        current = g.getFont();
        g.setFont(titleFont);
        g.drawString("FlappyBox", 450, 30);
        g.setFont(current);

        //display scores
        g.drawString("Score:   " + Integer.toString(score), 750, 20);
        g.drawString("High Score: " + Integer.toString(hiscore), 750, 40);

        //display times
        g.drawString("Current Time: " + Integer.toString(tm), 850, 20);
        g.drawString("Record Time:  " + Integer.toString(recTm), 850, 40);


        //scorestreaks
        if(lvl == 1 && !gameOver) g.drawString("Amateur hour.", 50, 40);
        if(lvl == 2 && !gameOver) g.drawString("Aw, your first steps.", 50, 40);
        if(lvl == 3 && !gameOver) g.drawString("Not bad.", 50, 40);
        if(lvl == 4 && !gameOver) g.drawString("You're pretty good.", 50, 40);
        if(lvl == 5 && !gameOver) g.drawString("Now we're getting somewhere.", 50, 40);
        if(lvl == 6 && !gameOver) g.drawString("Kickin' it up a notch.", 50, 40);
        if(lvl == 7 && !gameOver) g.drawString("Eyes on the prize.", 50, 40);
        if(lvl == 8 && !gameOver) g.drawString("Zoom, zoom, zoom.",50, 40);
        if(lvl == 9 && !gameOver) g.drawString("It's all uphill from here.", 50, 40);
        if(lvl == 10 && !gameOver) g.drawString("Now THIS is pod racing.", 50, 40);

        //game over 'streak'
        if(gameOver) g.drawString("Womp, womp.", 50, 40);

        //display game over status
        if(gameOver) g.drawString("GAME OVER!", player.x, player.y - 10);

        //record best time and score
        if(tm > recTm) recTm = tm;
        if(score > hiscore) hiscore = score;
    }

    private void genUpperObstacle(){
        int w;
        int h;

        w = getRandomNumberInRange(100,300);
        h = getRandomNumberInRange(200,400);
        obstacles[0] = new UpperObstacle(w, h);

    }

    private void genLowerObstacle(){
        int y;
        int w;
        int h;

        y = getRandomNumberInRange(400,600);
        w = getRandomNumberInRange(100,300);
        h = 680 - y;
        obstacles[1] = new LowerObstacle(y, w, h);

    }

    private void genObstacles(){
        genUpperObstacle();
        genLowerObstacle();
        score += 2;
    }

    private void moveObstacles(){

        if (obstacles[0].x > 0 - obstacles[0].w) {
            obstacles[0].moveLt(speed);
        }

        if (obstacles[0].x < 550) {
            obstacles[1].moveLt(speed);
        }

        if (obstacles[1].x < 0 - obstacles[1].w) {
            genObstacles();
        }

    }

    private void drawObstacles(Graphics g){
        for(int i=0;i<2;i++){
            obstacles[i].draw(g);
        }
        g.drawLine(0,50,1000,50); //ceiling
        g.drawLine(0,680,1000,680); //floor
    }
}
