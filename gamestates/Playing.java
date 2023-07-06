package gamestates;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import GameLevels.LevelManager;
import MainFiles.Game;
import PlayerEntities.EnemyManager;
import PlayerEntities.Player;
import Utils.LoadNSave;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import static Utils.Constants.Environment.*;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadNSave.getLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initializeClasses();

        backgroundImg = LoadNSave.GetSpriteAtlas(LoadNSave.PLAYING_BG_IMG);
        bigCloud = LoadNSave.GetSpriteAtlas(LoadNSave.BIG_CLOUDS);
        smallCloud = LoadNSave.GetSpriteAtlas(LoadNSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        }
    }

    private void initializeClasses() {
		levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)(64 * Game.SCALE), (int)(40 * Game.SCALE), this);
		//x: 200 * Scale
		player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    public void windowFocusLost() {
        player.resetBooleans();
        //points to resetBooleans method in player to reset the boolean values
        //have to point here since Game is the only class with a player obj
    }

    public Player getPlayer(){
            return player;
    }

    @Override
    public void update() {
        if(!paused && !gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
        else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if(diff > rightBorder)
            xLvlOffset += diff - rightBorder; 
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0 )
            xLvlOffset = 0;
            
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);

        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if(paused){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
        else if (gameOver){
            gameOverOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    public void resetAll(){
        // reset player, enemy, level, etc
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }

    public void mouseDragged(MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mouseDragged(e);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
            if (e.getButton() == MouseEvent.BUTTON1) {//1 = left, 2 = middle, 3 = right; (on mouse)
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        if(!gameOver){
            if(paused)
                pauseOverlay.mouseMoved(e);
        }
    }

    public void unpauseGame(){
        paused = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else{
            switch(e.getKeyCode()){

                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;

                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;

                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;

                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;

                case KeyEvent.VK_SPACE:
                    player.setJumping(true);
                    break;

                case KeyEvent.VK_BACK_SPACE:
                    paused = !paused;
                    break;
                    //if backspace is pressed we go to menu

                // case KeyEvent.VK_W:
                //     player.setUp(true);
                //     break;

                // case KeyEvent.VK_UP:
                //     player.setUp(true);
                //     break;

                // case KeyEvent.VK_S:
                //     player.setDown(true);
                //     break;

                // case KeyEvent.VK_DOWN:
                //     player.setDown(true);
                //     break;

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver){
            switch(e.getKeyCode()){

                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;

                case KeyEvent.VK_LEFT:
                    player.setLeft(false);
                    break;

                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;

                case KeyEvent.VK_RIGHT:
                    player.setRight(false);
                    break;

                case KeyEvent.VK_SPACE:
                    player.setJumping(false);
                    break;


                // case KeyEvent.VK_W:
                //     player.setUp(false);
                //     break;

                // case KeyEvent.VK_UP:
                //     player.setUp(false);
                //     break;

                // case KeyEvent.VK_S:
                //     player.setDown(false);
                //     break;

                // case KeyEvent.VK_DOWN:
                //     player.setDown(false);
                //     break;
            }
        }
    }
}


