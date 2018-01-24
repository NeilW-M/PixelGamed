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
		
		camera.x = -100;
		camera.y = -30;
		
		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Renders the array of pixels to the screen
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
		for(int y = 0; y < image.getHeight(); y++) 
			for(int x = 0; x < image.getWidth(); x++) 
				for(int yZoomPosition = 0 ; yZoomPosition < yZoom; yZoomPosition++) 
					for(int xZoomPosition = 0 ; xZoomPosition < xZoom; xZoomPosition++) 
						setPixel(imagePixels[x + y * image.getWidth()], ((x*xZoom) + xZoomPosition + xPosition), ((y*yZoom) + yPosition + yZoomPosition));
	}
	
	private void setPixel(int pixel, int x, int y) {
		if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {	
			int pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
			if(pixels.length > pixelIndex)
				pixels[pixelIndex] = pixel;
		}
	}
	
}