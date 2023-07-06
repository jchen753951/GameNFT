package PlayerEntities;

import java.awt.Color;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
   
    protected float x, y; 
    protected int width, height;
    protected Rectangle2D.Float hitbox; 
    //if protected, only classes that extend this class can use these variables.
    
    public Entity(float x, float y, int width, int height){  
        this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
        
    }
    protected void drawHitbox(Graphics g, int xLvlOffset){
        //For debugging the hitbox
        g.setColor(Color.pink);
        g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);

    }
    protected void initHitbox(float x, float y, int width, int height) {
        //creating hitbox out of entire sprite image (first)
        hitbox = new Rectangle2D.Float(x,y,width,height);
    }
 
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }


}
 /* cannot create an object of this class...
    we CANNOT do this: Entity enemyOne = new Entity(120, ... , bigman);
    instead, we will EXTEND this class to our player and enemy classes to use
    its variables and properties
    for ex. public class enemy{ Enemy enemyOne = new Enemy(...); }
    and public class player { Player playerOne = new Player(...); }
    Both classes will extend the Entity class and use its properties as their own
    */