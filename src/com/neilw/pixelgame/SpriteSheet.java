package com.neilw.pixelgame;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private int[] pixels;
	private BufferedImage image;
	public final int SIZEX;
	public final int SIZEY;
	private Sprite[] loadedSprites = null;
	private boolean spritesLoaded = false;
	
	private int spriteSizeX;
	
	/**
	 * Creates a SpriteSheet from a BufferedImage
	 * @param sheetImage Image the sheet is created from
	 */
	public SpriteSheet(BufferedImage sheetImage) {
		image = sheetImage;
		SIZEX = sheetImage.getWidth();
		SIZEY = sheetImage.getHeight();
		
		pixels = new int[SIZEX*SIZEY];
		pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
	}
	
	/**
	 * Loads all of the sprites from the sprite sheet
	 * @param spriteSizeX X Dimension of the sprites
	 * @param spriteSizeY Y Dimenstion of the sprites
	 */
	public void loadSprites(int spriteSizeX, int spriteSizeY) {
		this.spriteSizeX = spriteSizeX;
		loadedSprites = new Sprite[(SIZEX / spriteSizeX) * (SIZEY / spriteSizeY)];
		
		//Put +1 after both for-loops to account for a buffer between the actual sprites on the sheet
		int spriteID = 0;
		for(int y = 0; y < SIZEY; y += (1+spriteSizeY)) {
			for(int x = 0; x < SIZEX; x += (1+spriteSizeX)) {
				loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
				spriteID++;
			}
		}
		spritesLoaded = true;
	}
	
	/**
	 * Gets a specific sprite from an already loaded sheet
	 * @param x x Position of the sprite
	 * @param y y Position of the sprite
	 * @return
	 */
	public Sprite getSprite(int x, int y) {
		if(spritesLoaded) {
			int spriteID = x + y * (SIZEX / spriteSizeX - 3); //Putting -3 seems to fix the index error
			if(spriteID < loadedSprites.length) {
				return loadedSprites[spriteID];
			} else {
				System.out.println("The spriteID : " + spriteID + " is out of range: " + loadedSprites.length + ".");
			}
		} else {
			System.out.println("SpriteSheet could not get a loaded sprite.");
		}
		return null;
	}
	
	/**
	 * Gets the pixels of the sheet
	 * @return Pixels of the sheet
	 */
	public int[] getPixels() {
		return pixels;
	}
	
	/**
	 * Gets the image of the sprite sheet
	 * @return Image of the sprite sheet
	 */
	public BufferedImage getImage() {
		return image;
	}
	
}
