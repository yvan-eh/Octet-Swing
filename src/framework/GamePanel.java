package framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import graphics.Assets;
import graphics.Camera;
import graphics.ImageLoader;
import objects.Player;
import objects.World;

public class GamePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7690411942173474030L;

	Camera camera;
	BufferedImage worldImage;
	World world;
	Player octet;
	Timer gameTimer;
	
	int fps, frames = 0;
	long timer = System.currentTimeMillis();

	
	public GamePanel() {
		
		camera = new Camera(0, 0);
		worldImage = ImageLoader.loadImage("/world.png");
		world = new World(worldImage);
		octet = new Player(world.getWorldPlayer().x, world.getWorldPlayer().y, world, this);
		Assets.init();
		


		
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask(){
			public void run() {
				camera.set(octet);
				octet.set();
				repaint();
				
				frames++;
				if(System.currentTimeMillis() - timer > 1000){
					fps = frames;
					timer += 1000;
					frames = 0;
				}
			}
		}, 0, 15);
	}
	
	public void paint(Graphics g) {
		Graphics2D gtd = (Graphics2D) g;
		super.paint(gtd);
		gtd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		gtd.drawImage(Assets.darkness, 0, 0, Launcher.getWidth(), Launcher.getHeight(), null);
		gtd.translate((int)camera.getX(), (int)camera.getY());
//		//
		octet.draw(gtd);
		world.draw(gtd, camera);
//		//
		gtd.translate(-(int)camera.getX(), -(int)camera.getY());
		gtd.setColor(Color.YELLOW);
		gtd.drawString(""+fps, 10, 20);
		gtd.dispose();
	}


	public void keyPressed(int e) {
		if(e == KeyEvent.VK_A || e == KeyEvent.VK_LEFT) octet.setKeyLeft(true);
		if(e == KeyEvent.VK_D || e == KeyEvent.VK_RIGHT) octet.setKeyRight(true);
		if(e == KeyEvent.VK_SPACE || e == KeyEvent.VK_UP) octet.setKeyJump(true);
		if(e == KeyEvent.VK_SHIFT) octet.setKeyBoost(true);
	}

	public void keyReleased(int e) {
		if(e == KeyEvent.VK_A || e == KeyEvent.VK_LEFT) octet.setKeyLeft(false);
		if(e == KeyEvent.VK_D || e == KeyEvent.VK_RIGHT) octet.setKeyRight(false);
		if(e == KeyEvent.VK_SPACE || e == KeyEvent.VK_UP) octet.setKeyJump(false);
		if(e == KeyEvent.VK_SHIFT) octet.setKeyBoost(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
