package objects;

import framework.Launcher;
import graphics.Assets;

import java.awt.*;

public class Wall {

	Rectangle bounds;

	int size = Launcher.getGrid();
	int x, y;

	public Wall(int x, int y) {
		this.x = x;
		this.y = y;

		bounds = new Rectangle(x, y, size, size);
	}
	
	public void draw(Graphics2D gtd) {
		gtd.drawImage(Assets.tile[0], x, y, size, size, null);
	}
}
