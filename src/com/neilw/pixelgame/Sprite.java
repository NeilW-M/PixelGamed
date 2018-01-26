package com.neilw.pixelgame;

import java.awt.image.BufferedImage;

public class Sprite {

	/**
	 * Width and height of the sprite
	 */
	private int width, height;
	/**
	 * Pixel array of the sprite
	 */
	private int[] pixels;
	
	/**
	 * Sets the sprite equal to a specified on on a spritesheet.
	 * @param sheet 
	 * @param startX 
	 * @param startY
	 * @param width
	 * @param height
	 */
	public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width*height];
		sheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);
	}
	
	/**
	 * Creates a sprite from a single image
	 * @param image
	 */
	public Sprite(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		
		pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
}
