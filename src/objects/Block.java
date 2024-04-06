package objects;

import java.awt.*;

import framework.Launcher;
import graphics.Assets;

public class Block {
	
	Rectangle bounds;

	int size = Launcher.getGrid();
	int x, y;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		
		bounds = new Rectangle(x, y, size, size);
	}
	
	public void draw(Graphics2D gtd) {
		gtd.drawImage(Assets.tile[1], x, y, size, size,  null);
	}
}
