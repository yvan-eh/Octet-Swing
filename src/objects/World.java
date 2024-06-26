package objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import framework.Launcher;
import graphics.Camera;

public class World {
	
	private BufferedImage worldImage;
	private int width, height;
	Block[][] worldBlocks;
	Virus[][] worldViruses;
	
	public World(BufferedImage worldImage) {
		this.worldImage = worldImage;
		this.width = worldImage.getWidth();
		this.height = worldImage.getHeight();
		this.initWorldBlocks();
		this.initWorldViruses();
	}
	
	public void draw(Graphics2D gtd, Camera camera) {
		
		int xStart = (int) Math.max(0, -camera.getX() / Launcher.getWidth());
		int xEnd = (int) Math.min(width, (-camera.getX() + Launcher.getWidth()) / Launcher.getGrid() + 1);
		int yStart = (int) Math.max(0, -camera.getY() / Launcher.getWidth());
		int yEnd = (int) Math.min(height, (-camera.getY() + Launcher.getHeight()) / Launcher.getGrid() + 1);
		
		for(int i = xStart; i < xEnd; i++) {
			for(int j = yStart; j < yEnd; j++) {
				if(worldViruses[i][j] != null && worldViruses[i][j].isAlive) worldViruses[i][j].draw(gtd);
			}
		}
		for(int i = xStart; i < xEnd; i++) {
			for(int j = yStart; j < yEnd; j++) {
				if(worldBlocks[i][j] != null)worldBlocks[i][j].draw(gtd);
			}
		}
	}
	
	public void initWorldBlocks(){
		int p, r, g, b;
		Block[][] blocks = new Block[width][height];
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				p = worldImage.getRGB(i, j);
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = (p) & 0xff;
				
				if(r==0 && g==0 &&b==0) blocks[i][j] = new Block(i*Launcher.getGrid(), j*Launcher.getGrid());
			}
		}

		worldBlocks = blocks;
	}

	public Block[][] getWorldBlocks() {
		return worldBlocks;
	}

	public void initWorldViruses() {
		int p, r, g, b;
		Virus[][] viruses = new Virus[width][height];
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				p = worldImage.getRGB(i, j);
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = (p) & 0xff;
				
				if(r==0 && g==255 &&b==0) viruses[i][j] = new Virus(i*Launcher.getGrid(), j*Launcher.getGrid());
			}
		}
		
		worldViruses = viruses;
	}

	public Virus[][] getWorldViruses() {
		return worldViruses;
	}

	public Point getWorldPlayer() {
		int p, r, g, b;
		Point player;
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				p = worldImage.getRGB(i, j);
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = (p) & 0xff;
				
				if(r==255 && g==0 &&b==0) {
					player = new Point(i*Launcher.getGrid(), j*Launcher.getGrid());
					return player;
				}
			}
		}return null;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}