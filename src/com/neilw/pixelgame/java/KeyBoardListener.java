package com.neilw.pixelgame.java;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener, FocusListener  {

	public boolean[] keys = new boolean[120];
	
	public boolean up() {
		return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	}
	
	public boolean down() {
		return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	}
	
	public boolean left() {
		return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	}
	
	public boolean right() {
		return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}
	
	public void focusLost(FocusEvent arg0) {
		for(int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}

	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	public void focusGained(FocusEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
}
