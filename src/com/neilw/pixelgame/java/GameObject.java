package com.neilw.pixelgame.java;

public interface GameObject {
	
	//Called every time physically possible
	public void render(RenderHandler renderer, int xZoom, int yZoom);
	
	//Called 60 times per second
	public void update(Game game);

}
