package GameLevels;

public class Level {
    
    private int[][] lvlData;
    
    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }
    
    //get specific index for sprite array
    public int getSpriteIndex(int x, int y){ //int x/y = position for the array
        return lvlData[y][x];
    }

    public int[][] getLevelData(){
        return lvlData;
    }

}
