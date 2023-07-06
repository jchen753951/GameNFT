package GameLevels;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import MainFiles.Game;
import Utils.LoadNSave;

//import java.nio.Buffer;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game){
        this.game = game;
        importBackgroundSprites();
        levelOne = new Level(LoadNSave.getLevelData()); //draws level data in new level obj
    }

    private void importBackgroundSprites() {
        BufferedImage image = LoadNSave.GetSpriteAtlas(LoadNSave.LevelAtlas);
        levelSprite = new BufferedImage[48];
        for(int j = 0; j < 4; j++){
            for (int i = 0; i < 12; i++){
                int index = j * 12 + i;
                levelSprite[index] = image.getSubimage((int)i*32, (int)j*32, 32, 32);
            }
        }
    }

    public void draw(Graphics p, int LvlOffset){

        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++){
            for(int i = 0; i < levelOne.getLevelData()[0].length; i++){
                int index = levelOne.getSpriteIndex(i, j);
                p.drawImage(levelSprite[index], Game.TILES_SIZE * i - LvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
                    //TileSize imported from Game class. easier than typing "i*32*Scale & j*32*Scale"
            }
        }
    }
    public void update(){

    }

    public Level getCurrentLevel(){
        return levelOne;
    }
}
