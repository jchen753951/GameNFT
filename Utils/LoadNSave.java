package Utils;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import MainFiles.Game;
import PlayerEntities.Crabby;
import static Utils.Constants.EnemyConstants.CRABBY;

//import java.awt.*;

public class LoadNSave {

    public static final String PlayerAtlas = "player_sprites.png";
    public static final String LevelAtlas = "outside_sprites.png";
    //public static final String LevelOneData = "level_one_data.png";
    public static final String LevelOneData = "level_one_data_long.png"; 
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String CHANGE_SKIN_BUTTON = "TestButton.png"; //Input file name here once created
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    public static final String STATUS_BAR = "health_power_bar.png";

    public static BufferedImage GetSpriteAtlas(String filename){

        BufferedImage image = null;
        InputStream is = LoadNSave.class.getResourceAsStream("/Resources/" + filename);
        //since using a static method, have to call the class instead of method

        try{
            image = ImageIO.read(is);

        /*
        Animations = new BufferedImage[9][6];
        y-axis indx = 9 (j); x-axis indx = 6 (i);

        for(int j = 0; j < Animations.length; j++){//checks for outer dimension
            -- nested for loop --
            for(int i = 0; i < Animations[j].length; i++){
                -- i is counted until 5 (0,1,2,3,4,5) --
                Animations[j][i] = image.getSubimage(i*64,j*40, 64, 40);
            }
        }
        */

        }
        catch(IOException e){
            e.printStackTrace();

        } finally {

            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return image;
    }

    public static ArrayList<Crabby> GetCrabs(){
        BufferedImage img = GetSpriteAtlas(LevelOneData);
        ArrayList<Crabby> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen(); //returning the red value

                //avoid array out of bounds error:
                if (value == CRABBY){
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }

        return list;
    }

    public static int[][] getLevelData(){

        //Drawing the level data:
        BufferedImage image = GetSpriteAtlas(LevelOneData);
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++){
            for(int i = 0; i < image.getWidth(); i++){
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed(); //returning the red value

                //avoid array out of bounds error:
                if (value >= 48){
                    value = 0;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }

}