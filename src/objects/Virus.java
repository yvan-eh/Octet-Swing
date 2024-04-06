package objects;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import graphics.Assets;

public class Virus {
	
	Shape bounds;
	int size = 32;

	int x, y;
	
	public Virus(int x, int y) {
		this.x = x;
		this.y = y;
		
		bounds = new Ellipse2D.Double(x + size/2f, y + size/2f, size, size);
	}
	
	public void set() {
		
	}
	
	public void draw(Graphics2D gtd) {
		gtd.drawImage(Assets.virusFace, x, y, null);
	}
	public void drawLights(Graphics2D gtd) {
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f));
		gtd.drawImage(Assets.greenlight, x - 416 + size/2, y - 416 + size/2, null);
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
