package PlayerEntities;

//imports
// import static Utils.Constants.PlayerConstants.AttackOne;
// import static Utils.Constants.PlayerConstants.GetSpriteAmount;
// import static Utils.Constants.PlayerConstants.Idle;
// import static Utils.Constants.PlayerConstants.Running;
import static Utils.Constants.PlayerConstants.*;
import static Utils.HelpMethods.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import MainFiles.Game;
import Utils.LoadNSave;
import gamestates.Playing;

/* Unused Imports
import static Utils.Constants.PlayerConstants.Jumping;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.tools.DocumentationTool.Location;

import java.io.IOException;
import java.io.InputStream;
*/

public class Player extends Entity {

    //initializing variables
    private BufferedImage[][] Animations;

    private int AnimationTick, AnimationIndex, AnimationSpeed = 25;
    private int playerAction = Idle;

    private boolean moving = false, attacking = false, jumping;
    private boolean left, up, right, down;

    private float playerSpeed = 1.0f * Game.SCALE;
    private float xValOffset = 21 * Game.SCALE;
    private float yValOffset = 4 * Game.SCALE;
    //private float playerHeight = 1.5f;

    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //status bar
    private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //attack box
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked = false;
    private Playing playing;

    private int[][] levelData;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y,(int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));

    }

    public void update(){
        updateHealthBar();

        if(currentHealth <= 0){
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePosition();
        //updateHitbox();
        if(attacking)
            checkAttack();
        updateAnimation();
        setNewAnimation();
    }

    private void checkAttack() {
        if(attackChecked || AnimationIndex != 1){
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if(right){
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        }
        else if(left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (Game.SCALE) * 10;
    }

    private void updateHealthBar() {
        healthWidth = (int) (currentHealth / (float) maxHealth) * healthBarWidth;
    }

    //change player size here
    public void render(Graphics p, int LvlOffset){
        p.drawImage(Animations[playerAction][AnimationIndex], (int)(hitbox.x - xValOffset) - LvlOffset + flipX, (int)(hitbox.y - yValOffset), width * flipW, height,  null); // (int)(120), (80)
        //drawHitbox(p);

        //drawAttackBox(p, LvlOffset);
        drawUI(p);
    }

    private void drawAttackBox(Graphics p, int lvlOffsetX) {
        p.setColor(Color.red);
        p.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updatePosition() {
        moving = false;

        if(jumping)
            jump();
            
        // if(!left && !right && !inAir)
        //     return;
        if(!inAir) 
            if((!left && !right) || (left && right))
                return;

        float xSpeed = 0; //temporary storage of speed
        //pass them into "canMoveHere" method and apply the to actual position of player if it returns true

        if(left) {
            xSpeed -= playerSpeed; //makes it so we can move ONLY if we can move in that place.. otherwise no

            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;

            flipX = 0;
            flipW = 1;
        }
        if(!inAir){
            if(!isEntityOnFloor(hitbox, levelData)){
                inAir = true;
            }
        }
        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        }
        else{
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        }
        else{
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if(currentHealth <= 0) {
            currentHealth = 0;
            // gameover
        }
        else if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    private void setNewAnimation() {
        int startNewAnimation = playerAction; //idle at first
        if(moving){
           playerAction = Running;
        }
        else{
           playerAction = Idle;
        }

        if(inAir){
            if(airSpeed < 0)
                playerAction = Jumping;
            else
                playerAction = Falling;
        }

        if(attacking){
            playerAction = Attack;
            if(startNewAnimation != Attack){
                AnimationIndex = 1;
                AnimationTick = 0;
                return;
            }
        }

        if(startNewAnimation != playerAction){
            resetAnimationTickAndIndex();
        }
    }

    private void resetAnimationTickAndIndex() {
        //resets the tick and index so that we get a full cycle of new animations
            AnimationTick = 0;
            AnimationIndex = 0;
    }



    private void updateAnimation() {
        AnimationTick++;

        if(AnimationTick >= AnimationSpeed){
            AnimationTick = 0;
            AnimationIndex++;
            if(AnimationIndex >= GetSpriteAmount(playerAction)) {
               AnimationIndex = 0; //resets at end of row
               attacking = false;
               attackChecked = false;
            }
        }
    }

    private void loadAnimations() {
        BufferedImage image = LoadNSave.GetSpriteAtlas(LoadNSave.PlayerAtlas);

        Animations = new BufferedImage[7][8];
        //y-axis indx = 9 (j); x-axis indx = 6 (i);
        for(int j = 0; j < Animations.length; j++){//checks for outer dimension
            //dependent for loop
            for(int i = 0; i < Animations[j].length; i++){
                //i is counted until 5 (0,1,2,3,4,5)
                Animations[j][i] = image.getSubimage(i * 64,j * 40, 64, 40);
            }
        }

        statusBarImg = LoadNSave.GetSpriteAtlas(LoadNSave.STATUS_BAR);
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public void loadLevelData(int[][] levelData){
        this.levelData = levelData;
        if(!isEntityOnFloor(hitbox, levelData))
            inAir = true;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetBooleans() {
        //resets boolean values to false when window focus is lost
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public Component setLocationRelativeTo() {
       return null;
    }

    public void resetAll() {
        resetBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = Idle;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if(!isEntityOnFloor(hitbox, levelData)){
            inAir = true;
        }
    }

}
