package com.neilw.pixelgame.java;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.neilw.pixelgame.java.entities.Player;

public class Game extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static int alpha = 0xFFFF00FF;
	
	private int xZoom = 5;
	private int yZoom = 5;
	
	//The canvas that is being drawn to
	private Canvas canvas = new Canvas();

	//Instance of the RenderHandler class that is used to render everything to the screen
	private RenderHandler renderer;

	//Instance of the SpriteSheet class that handles and loads all of the availible sprites
	private SpriteSheet sheet;
	
	//private Rectangle testRectangle = new Rectangle(125, 125, 150, 150);
	
	private Tiles tiles;
	private Map map;

	private KeyBoardListener keyListener = new KeyBoardListener();
	private MouseEventListener mouseListener = new MouseEventListener(this);
	
	private GameObject[] objects;
	private Player player;
	
	public Game() 
	{
		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		setBounds(0,0, 1840, 1440);

		//Put our frame in the center of the screen.
		setLocationRelativeTo(null);

		//Makes the frame a set size
		setResizable(false);
		
		//Add the graphics component
		add(canvas);

		//Make the frame visible.
		setVisible(true);

		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);

		//Instantiates a RenderHandler object, passing in the width and height of the window
		renderer = new RenderHandler(getWidth(), getHeight());

		//Loads the image for the sprite sheet
		BufferedImage sheetImage = loadImage("/com/neilw/pixelgame/assets/sheets/rogueLike.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
		
		tiles = new Tiles(new File(getClass().getResource("/com/neilw/pixelgame/assets/tiles/Tiles.txt").getFile()), sheet);
		
		map = new Map(new File(getClass().getResource("/com/neilw/pixelgame/assets/maps/MainMap.txt").getFile()), tiles);
		
		//Sets the testImage equal to "GrassTile.png"
		//testImage = loadImage("GrassTile.png");
		//testSprite = sheet.getSprite(0, 3);
		
		//Creates a testRectangle with borderWidth(hWidth, vWidth, Color)
		//testRectangle.generateGraphics(5, 5, 123123);
		
		//Load Objects
		objects = new GameObject[1];
		player = new Player();
		objects[0] = player;
		
		//Attaches the keyboard listeners to the canvas
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
	}
	
	/**
	 * Runs theoretically at 60 frames per second
	 */
	public void update() {
		for(int i = 0; i < objects.length; i++) {
			objects[i].update(this);
		}
	}

	/**
	 * Loads an imported image into something usable by java.
	 * @param path The location of the imported picture.
	 * @return The formatted image, or in case of an error, null.
	 */
	private BufferedImage loadImage(String path) {
		try {	
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			
			return formattedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * The main render method, which implements an instance of RenderHandler to render the screen.
	 * It pulls in the BufferStrategy from the canvas object, 
	 * and then creates a graphics object with the forementioned BufferStrategy.
	 */
	public void render() {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);

			map.render(renderer, xZoom, yZoom);
			
			for(int i = 0; i < objects.length; i++) {
				objects[i].render(renderer, xZoom, yZoom);
			}
			
			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
			renderer.clear();
	}

	/**
	 * The main game loop. 
	 * It controls the speed of the game updating and the screen rendering.
	 * It calls the render method as fast as possible, and the update method about 60 times per second
	 */
	public void run() {
		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}
	}

	public KeyBoardListener getKeyListener() {
		return keyListener;
	}
	
	public MouseEventListener getMouseListener() {
		return mouseListener;
	}
	
	public RenderHandler getRenderer() {
		return renderer;
	}
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}