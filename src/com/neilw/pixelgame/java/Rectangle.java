package com.neilw.pixelgame.java;

//1.06
public class Rectangle {

	/**
	 * The x-position, y-position, width, and height of the rectangle
	 */
	public int x,y,w,h;
	
	/**
	 * Pixels of the rectangle.
	 */
	private int[] pixels;
	
	/**
	 * Makes a rectangle.
	 * @param x x-position
	 * @param y y-position
	 * @param w width
	 * @param h height
	 */
	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
	}
	
	/**
	 * Makes a rectangle with the default parameters of (0, 0, 0, 0).
	 */
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * Generates graphics for the rectangle in a specific color.
	 * @param color color for the rectangle
	 */
	public void generateGraphics(int color) {
		pixels = new int[w*h];
		for(int y = 0; y < h; y++) 
			for(int x = 0; x < w; x++) 
				pixels[x+y*w] = color;
	}
	
	/**
	 * Generates graphics for the rectangle with a border width.
	 * @param hBorderWidth Border width of the left/right.
	 * @param vBorderWidth Border width of the top/bottom.
	 * @param color Color.
	 */
	public void generateGraphics(int hBorderWidth, int vBorderWidth, int color) {
		pixels = new int[w*h];
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = Game.alpha;
		}
		
		//Top Border
		for(int y = 0; y < vBorderWidth; y++) {
			for(int x = 0; x < w; x++) {
				pixels[x+y*w] = color;
			}
		}
		//Bottom Border
		for(int y = h - vBorderWidth; y < h; y++) {
			for(int x = 0; x < w; x++) {
				pixels[x+y*w] = color;
			}
		}
		//Left Border
		for(int x = 0; x < hBorderWidth; x++) {
			for(int y = 0; y < h; y++) {
				pixels[x+y*w] = color;
			}
		}
		//Right Border
		for(int x = w - hBorderWidth; x < w; x++) {
			for(int y = 0; y < h; y++) {
				pixels[x+y*w] = color;
			}
		}
		/*
		//Renders a colored rectangle and then overwrites it with black to get the border effect.
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
		for(int y = vBorderWidth; y < h - vBorderWidth; y++) 
			for(int x = hBorderWidth; x < w - hBorderWidth; x++) 
				pixels[x+y*w] = 0;
		*/
	}
	
	/**
	 * Gets the pixels array for the rectangle.
	 * @return pixels[]
	 */
	public int[] getPixels() {
		if(pixels != null)
			return pixels;
		else
			System.out.println("The pixels for this rectangle have not been generated.");
		return null;
	}
}
