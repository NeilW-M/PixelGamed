package com.neilw.pixelgame;

//1.06
public class Rectangle {

	public int x,y,w,h;
	
	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
	}
	
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	
}
