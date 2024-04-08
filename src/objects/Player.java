package objects;

import java.awt.*;

import framework.GamePanel;
import graphics.Assets;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
	GamePanel panel;
	World world;
	Rectangle bounds;
	int x, y, colX, colY, spawnX, spawnY;
	int size = 32;
	double xV, xA, yV, yG, yJ, xMaxV;
	boolean keyLeft, keyRight, keyJump, keyBoost, isJumping, canJump;
	
	Block[][] worldBlocks;
	Virus[][] worldVirus;
	
    private List<Bit> bits;

	public Player(World world, GamePanel panel) {
		this.panel = panel;
		this.world = world;
		this.x = world.getWorldPlayer().x;
		this.y = world.getWorldPlayer().y;
		this.spawnX = x;
		this.spawnY = y;
		this.xA = 1;
		this.yG = 1;
		this.yJ = -20;
		this.canJump= true; 
		this.worldBlocks = world.getWorldBlocks();
		this.worldVirus = world.getWorldViruses();
		bits = new ArrayList<>(8);
		
		bounds = new Rectangle(x, y, size, size);
	}
	
	public void set() {		
		if(keyLeft && keyRight || !keyLeft && !keyRight	) xV *= .8;
		else if(keyLeft) xV -= xA;
		else xV += xA;
		
		if(Math.abs(xV) < 1) xV = 0;

		if(isJumping) xMaxV = 13;
		else xMaxV = 12;
		if(Math.abs(xV) > xMaxV) xV = Math.signum(xV)*xMaxV;

		if(keyBoost) {
			xMaxV = 16;
			yG = 3;
		}if(!keyBoost) {
			xMaxV = 12;
			yG = 1;
		}
		
		if(keyJump && canJump) {
			bounds.y ++;
			for(int i = 0; i < world.getWidth(); i++) {
				for(int j = 0; j < world.getHeight(); j++) {
					if((worldBlocks[i][j]!=null&&worldBlocks[i][j].bounds.intersects(bounds))||((colX==-1)&&keyRight&&!keyLeft)||((colX==1)&&keyLeft&&!keyRight)) {
						yV = yJ;
						canJump = false;
						isJumping = true;
					}
				}
			}
			bounds.y--;
		}

		if(Math.abs(xV) > 1) colX = 0;
		if (!keyJump) canJump = true;
		
		yV += yG;
		
		bounds.x += xV;
		for(int i = 0; i < world.getWidth(); i++) {
			for(int j = 0; j < world.getHeight(); j++) {
				if(worldVirus[i][j] != null && worldVirus[i][j].bounds.intersects(bounds) && worldVirus[i][j].isAlive) restart();
				if(worldBlocks[i][j] != null && worldBlocks[i][j].bounds.intersects(bounds)) {
					bounds.x -= xV;
					while(worldBlocks[i][j] != null && !worldBlocks[i][j].bounds.intersects(bounds))
						bounds.x += Math.signum(xV);
					colX = (int)Math.signum(xV);
					bounds.x -= Math.signum(xV);
					xV = 0;
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
					colY = (int)Math.signum(yV);
					if(colY == 1) isJumping = false;
					bounds.y -= Math.signum(yV);
					yV = 0;
					y = bounds.y;
				}
			}
		}
		
		x += xV;
		y += yV;
		
		bounds.x = x;
		bounds.y = y;

		for (Bit bit : bits) {
			if(!bit.bounds.intersects(bounds)) bit.shoot();
			if(bit.bounds.intersects(bounds)) bit.collect();
            bit.set();
        }

		bits.removeIf(bit -> bit.collected);
	}
	
	public void draw(Graphics2D gtd) {
		if(keyRight) gtd.drawImage(Assets.octet[2], x, y, null);
		else if(keyLeft) gtd.drawImage(Assets.octet[1], x, y, null);
		else gtd.drawImage(Assets.octet[0], x, y, null);
		drawLights(gtd);
		drawBits(gtd);
	}

	public void drawLights(Graphics2D gtd) {
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f / (bits.size()+4)));
		gtd.drawImage(Assets.redlight, x - 416 + size/2, y - 416 + size/2, null);
		gtd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void shoot(double power, int x, int y) {
		if(bits.size() == 7) {
			restart();
			return;
		}
        double direction = Math.atan2(y - 561, x - 975);
		if(power < 2) power = 2;
		if(power > 7) power = 7;
		xV += -Math.cos(direction) * power * (bits.size()+4);
		yV += -Math.sin(direction) * power * (bits.size()+4);
		bits.add(new Bit(world, this.x, this.y, Math.cos(direction) * power, Math.sin(direction) * power));
	}

	public void drawBits(Graphics2D gtd) {
        for (Bit bit : bits) {
            bit.draw(gtd);
        }
	}

	public int bits() {
		return bits.size();
	}

	public int getX() {
		return x;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setVel(int xV, int yV) {
		this.xV = xV;
		this.yV = yV;
	}

	public void restart() {
		bits.clear();
		setVel(0, 0);
		setPos(spawnX, spawnY);
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public GamePanel getPanel() {
		return panel;
	}

	public void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	public boolean isKeyLeft() {
		return keyLeft;
	}

	public void setKeyLeft(boolean keyLeft) {
		this.keyLeft = keyLeft;
	}

	public boolean isKeyRight() {
		return keyRight;
	}

	public void setKeyRight(boolean keyRight) {
		this.keyRight = keyRight;
	}

	public boolean isKeyJump() {
		return keyJump;
	}

	public boolean isKeyBoost() {
		return keyBoost;
	}

	public void setKeyBoost(boolean keyBoost) {
		this.keyBoost = keyBoost;
	}

	public void setKeyJump(boolean keyJump) {
		this.keyJump = keyJump;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public GamePanel getGamePanel() {
		return panel;
	}
}
