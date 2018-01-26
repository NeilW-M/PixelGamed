package com.neilw.pixelgame;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static int alpha = 0xFFFF00FF;
	
	/**
	 * The Canvas that contains the BufferStrategy and is painted on
	 */
	private Canvas canvas = new Canvas();
	/**
	 * Instance of the RenderHandler class used to render to the screen
	 */
	private RenderHandler renderer;
	/**
	 * A test image that is defined in the constructor and rendered to the screen
	 */
	//private BufferedImage testImage;
	private Sprite testSprite;
	private SpriteSheet sheet;
	/**
	 * A test rectangle that is defined in the constructor for border width and color
	 */
	private Rectangle testRectangle = new Rectangle(125, 125, 150, 150);

	public Game() 
	{
		//Make our program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of our frame.
		setBounds(0,0, 2000, 1600);

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
		BufferedImage sheetImage = loadImage("rogueLike.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
		
		//Sets the testImage equal to "GrassTile.png"
		//testImage = loadImage("GrassTile.png");
		
		
		testSprite = sheet.getSprite(0, 3);
		
		//Creates a testRectangle with borderWidth(hWidth, vWidth, Color)
		testRectangle.generateGraphics(5, 5, 123123);
	}
	
	public void update() {
		
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

			//renderer.renderImage(testImage, 0, 0, 10, 10);
			renderer.renderSprite(testSprite, 0, 0, 10, 10);
			renderer.renderRectangle(testRectangle, 1, 1);
			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
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

	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}