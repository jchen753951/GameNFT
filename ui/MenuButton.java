package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import Utils.LoadNSave;
import static Utils.Constants.UI.Buttons.*;

public class MenuButton {

    private int xPos, yPos, rowIndex, index;
    // private static final int B_WIDTH = Utils.Constants.UI.Buttons.B_WIDTH;
    // private static final int B_HEIGHT = Utils.Constants.UI.Buttons.B_HEIGHT;
    // private static final int B_WIDTH_DEFAULT = Utils.Constants.UI.Buttons.B_WIDTH_DEFAULT;
    // private static final int B_HEIGHT_DEFAULT = Utils.Constants.UI.Buttons.B_HEIGHT_DEFAULT;

    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private BufferedImage[] moreImgs;
    
    private boolean mouseOver, mousePressed;
    private Rectangle bounds; //hitbox of the box


    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    } 

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs(){
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadNSave.GetSpriteAtlas(LoadNSave.MENU_BUTTONS);
        //BufferedImage temp2 = LoadNSave.GetSprite(LoadNSave.CHANGE_SKIN_BUTTON);
        for(int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT); 
        
            // for(int j = 0; j < imgs.length; j++)
        //     imgs[j] = temp.getSubimage(j * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT); 
        //     //moreImgs[j].??
    }

    public void draw(Graphics p){
       
        p.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update(){

        index = 0;
            if(mouseOver)
                index = 1;
            if(mousePressed)
                index = 2;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }
     
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // public int getX() {
    //     return xPos;
    // }

    // public int getY() {
    //     return yPos;
    // }

    
}
