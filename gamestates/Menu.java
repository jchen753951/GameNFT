package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import MainFiles.Game;
import Utils.LoadNSave;
import ui.MenuButton;

import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private MenuButton[] changeSkin = new MenuButton[1];
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundImgPink = LoadNSave.GetSpriteAtlas(LoadNSave.MENU_BACKGROUND_IMG);

    }

    private void loadBackground() {
        backgroundImg = LoadNSave.GetSpriteAtlas(LoadNSave.MENU_BACKGROUND);
        menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
        // these are off, need to go back and redo calculations

        // for Changeskin button
        changeSkin[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (360 * Game.SCALE), 0, Gamestate.CHANGESKIN);

    }

    @Override
    public void update() {
        for (MenuButton mb : buttons){
            mb.update();
        }

    //  -- Extra Button(s), Changeskin
        //  for (MenuButton bb : changeSkin){
        //      bb.update();
        //  }
    }

    @Override
    public void draw(Graphics p) {
        p.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        p.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
       for (MenuButton mb : buttons){
            mb.draw(p);
        }

    //  -- Extra Button(s), Changeskin
        // for (MenuButton bb : changeSkin){
        //     bb.draw(p);
        // }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }

    //  -- Extra Button(s), Changeskin
        // for(MenuButton bb : changeSkin){
        //     if(isIn(e, bb)) {
        //         bb.setMousePressed(true);
        //         break;
        //     }
        // }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e, mb)) {
                if(mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }

    //  -- Extra Button(s), Changeskin
        // for(MenuButton bb : changeSkin){
        //     if(isIn(e, bb)) {
        //         if(bb.isMousePressed())
        //             bb.applyGamestate();
        //         break;
        //     }
        // }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons) {
            mb.resetBools();
        }

    //  -- Extra Button(s), Changeskin
        // for(MenuButton bb : changeSkin) {
        //     bb.resetBools();
        // }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);
        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }

    //  -- Extra Button(s), Changeskin
        // for (MenuButton bb : changeSkin)
        //     bb.setMouseOver(false);
        // for (MenuButton bb : changeSkin)
        //     if (isIn(e, bb)) {
        //         bb.setMouseOver(true);
        //         break;
        //     }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;
            // if the enter key is hit, the game will start

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}