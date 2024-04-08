package objects;

import java.awt.*;

import graphics.Assets;

public class Bit {
	
	World world;
	Rectangle bounds;
	int size = 32;

	int x, y;
	double xV, yV;
	boolean collected;
	boolean shot;
	
	Block[][] worldBlocks;
	Virus[][] worldViruses;
	
	public Bit(World world, int x, int y, double xV, double yV) {

		this.x = x;
		this.y = y;
		this.xV = xV * 10;
		this.yV = yV * 10;
		collected = false;
		shot = false;
		
		this.world = world;
		this.worldBlocks = world.getWorldBlocks();
		this.worldViruses = world.getWorldViruses();
		// System.out.println("Bit created at" + this.x + ", " + this.y + " with V = " + this.xV + ", " +this.yV );
		
		bounds = new Rectangle(x, y, size, size);
	}
	
	public void set() {

		xV *= 0.9;
		yV *= 0.9;

		// System.out.println(xV + ", " + yV);
		
		if(Math.abs(xV) < 1 || Math.abs(yV) < 1) xV = yV = 0;

		bounds.x += xV;
		for(int i = 0; i < world.getWidth(); i++) {
			for(int j = 0; j < world.getHeight(); j++) {
				if(worldViruses[i][j] != null && worldViruses[i][j].bounds.intersects(bounds)) worldViruses[i][j].kill();
				if(worldBlocks[i][j] != null && worldBlocks[i][j].bounds.intersects(bounds)) {
					bounds.x -= xV;
					while(worldBlocks[i][j] != null && !worldBlocks[i][j].bounds.intersects(bounds))
						bounds.x += Math.signum(xV);
					// colX = (int)Math.signum(xV);
					bounds.x -= Math.signum(xV);
					xV = -xV;
					x = bounds.x;
				}
			}
		}

		bounds.y += yV;
		for(int i = 0; i < world.getWidth(); i++) {
			for(int j = 0; j < world.getHeight(); j++) {
				if(worldBlocks[i][j] != null && worldBlocks[i][j].bounds.intersects(bounds)) {
					bounds.y -= yV;
					while(worldBlocks[i][j] != null && !worldBlocks[i][j].bounds.intersects(bounds))
						bounds.y += Math.signum(yV);
					// colY = (int)Math.signum(yV);
					// if(colY == 1) isJumping = false;
					bounds.y -= Math.signum(yV);
					yV = -yV;
					y = bounds.y;
				}
			}
		}
		
		x += xV;
		y += yV;
		
		bounds.x = x;
		bounds.y = y;
	}
	
	public void draw(Graphics2D gtd) {
		gtd.drawImage(Assets.bit, x, y, null);
		// drawLights(gtd);
	}
	public void drawLights(Graphics2D gtd) {
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f));
		gtd.drawImage(Assets.greenlight, x - 208 + size/2, y - 208 + size/2, null);
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void collect() {
		if(!shot) return;
		collected = true;
	}

	public boolean collected() {
		return collected;
	}

	public void shoot() {
		shot = true;
	}

	public boolean shot() {
		return shot;
	}
}
