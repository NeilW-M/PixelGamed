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
	
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testImage;

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
		
		//Add our graphics component
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		//Create our object for buffer strategy.
		canvas.createBufferStrategy(3);

		renderer = new RenderHandler(getWidth(), getHeight());

		testImage = loadImage("GrassTile.png");
	}
	
	public void update() {
		
	}

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
	
	public void render() {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);

			renderer.renderImage(testImage, 0, 0, 10, 10);
			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
	}

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