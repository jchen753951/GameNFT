package MainFiles;

import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {

	private Window window;
	private Panel panel;
	private Thread gameLoop;

	private final int FPS = 120;
	private final int UPS = 200;

	private Playing playing;
	private Menu menu;

	public final static int DefaultTilesSize = 32;
	public final static float SCALE = 1.5f; //1.5f
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (DefaultTilesSize * SCALE); //TileSize is 64
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // 1664
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // 896

	//long now = System.nanoTime();
	public Game() {
		initializeClasses();
		
		panel = new Panel(this);
		window = new Window(panel);
		panel.requestFocus();
		
		startGame();
	}

	private void initializeClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}	

	public void startGame(){
		gameLoop = new Thread(this); //gameThread
		gameLoop.start();
	}

	public void update(){
		switch(Gamestate.state){
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
			case QUIT:
			case CHANGESKIN:
			default:
				System.exit(0);
				break;
			
		}
	}

	public void render(Graphics p){

		switch(Gamestate.state){
			case MENU:
				menu.draw(p);
				break;
			case PLAYING:
				playing.draw(p);
				break;
			default:
				break;
			
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//game loop

		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS; //time in between updates, not the amt of time update takes

		long prevTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaUpdate = 0;
		double deltaFrames = 0;

		while(true){
		
			long currTime = System.nanoTime();

			deltaUpdate += (currTime - prevTime) / timePerUpdate;
			deltaFrames += (currTime - prevTime) / timePerFrame;
			prevTime = currTime;

			if(deltaFrames >= 1){
				panel.repaint();
				frames++;
				deltaFrames--;
			}

			if(deltaUpdate >= 1){
				update();
				updates++;
				deltaUpdate--;
			}

			if(System.currentTimeMillis() - lastCheck >= 1000){
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS " + frames + "UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING);
			playing.getPlayer().resetBooleans();
	}

	public Menu getMenu(){
		return menu;
	}

	public Playing getPlaying(){
		return playing;
	}
}