package graphics;

import java.awt.Rectangle;

import framework.Launcher;
import objects.Player;

public class Camera {
	private float x, y;
	Rectangle viewport;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Player player) {
		
		float xt = (Launcher.getWidth()/2f)-player.getX();
		float yt = (Launcher.getHeight()/2f)-player.getY();

		x += (xt - x) * .2;
		y += (yt - y) * .2;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
