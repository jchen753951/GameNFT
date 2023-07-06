package Utils;

import MainFiles.Game;
import java.awt.geom.Rectangle2D;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData){
        if(!isSolid(x, y, lvlData)) //top left
            if(!isSolid(x + width, y + height, lvlData)) //checking bottom right
                if(!isSolid(x + width, y, lvlData)) //top right
                    if(!isSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if(x < 0 || x >= maxWidth)
            return true; //means it is solid, dont move here
        
        if(y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    private static boolean IsTileSolid(int xTile, int yTile, int[][] lvldata) {
        int value = lvldata[yTile][xTile];

        if(value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0){
            // right
            int tilexPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tilexPos + xOffset - 1;
        }
        else{
            // left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        if(airSpeed > 0){
            // falling
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        }
        else{
            // jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData){
        // check pixel below bottom left and bottom right
        if(!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)){
            if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData)){
                return false;
            }
        }
        return true;
    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for(int i = 0; i < xEnd - xStart; i++){
            if(IsTileSolid(xStart + i, y, lvlData))
                return false;
            if(IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
    }
}
