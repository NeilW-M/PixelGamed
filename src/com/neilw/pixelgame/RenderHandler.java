package com.neilw.pixelgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage view;
	private Rectangle camera;
	private int[] pixels;

	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//Creates a 'camera' that controls the view of the screen
		camera = new Rectangle(0, 0, width, height);
		
		camera.x = 0;
		camera.y = 0;
		
		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Renders the array of pixels to the screen
	 * @param graphics Graphics object used to render
	 */
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	/**
	 * Render an image to the array of pixels
	 * @param image
	 * @param xPosition
	 * @param yPosition
	 */
	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}
	
	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}
	
	/**
	 * Renders a rectangle from the Rectangle Class.
	 * @param rectangle Rectangle being rendered.
	 * @param xZoom xZoom
	 * @param yZoom yZoom
	 */
	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
		int[] rectanglePixels = rectangle.getPixels();
		if(rectanglePixels != null) {
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom);
		}
	}
	
	/**
	 * Renders an array of pixels to the image (screen).
	 * @param renderPixels Pixels array being rendered.
	 * @param width Width of the render.
	 * @param height Height of the render.
	 * @param xPosition xPosition of the render.
	 * @param yPosition yPosition of the render.
	 * @param xZoom xZoom of the render.
	 * @param yZoom yZoom of the render.
	 */
	public void renderArray(int[] renderPixels, int width, int height, int xPosition, int yPosition, int xZoom, int yZoom) {
		for(int y = 0; y < height; y++) 
		for(int x = 0; x < width; x++) 
			for(int yZoomPosition = 0 ; yZoomPosition < yZoom; yZoomPosition++) 
				for(int xZoomPosition = 0 ; xZoomPosition < xZoom; xZoomPosition++) 
					setPixel(renderPixels[x + y * width], ((x*xZoom) + xZoomPosition + xPosition), ((y*yZoom) + yPosition + yZoomPosition));
	}
	
	/**
	 * Determines the location of the pixel on the screen.
	 * If the pixel color is equal to 0xFF00DC (Game.alpha) it will not be rendered,
	 * and be show the effect of transparency
	 * @param pixel Pixel that is being set
	 * @param x x-Position
	 * @param y y-Position
	 */
	private void setPixel(int pixel, int x, int y) {
		if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {	
			int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
			if(pixels.length > pixelIndex && pixel != Game.alpha)
				pixels[pixelIndex] = pixel;
		}
	}
	
}