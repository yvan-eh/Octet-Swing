package framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JFrame;

import graphics.Assets;
import graphics.Camera;
import graphics.ImageLoader;
import objects.Player;
import objects.World;

public class GamePanel extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = -7690411942173474030L;

	Camera camera;
	BufferedImage worldImage;
	World world;
	Player octet;
	Timer gameTimer;
	
	int fps, frames = 0;
	long timer = System.currentTimeMillis();
	private long pressTime; 
	JFrame frame;

	public GamePanel(JFrame frame) {
		this.frame = frame;
		camera = new Camera(0, 0);
		worldImage = ImageLoader.loadImage("/world.png");
		world = new World(worldImage);
		addMouseListener(this);
		octet = new Player(world, this);
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
		Dimension window = frame.getSize();
		Graphics2D gtd = (Graphics2D) g;
		super.paint(gtd);
		gtd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		gtd.drawImage(Assets.darkness, 0, 0, Launcher.getWidth(), Launcher.getHeight(), null);
		gtd.translate((int)camera.getX(), (int)camera.getY());
		octet.draw(gtd);
		world.draw(gtd, camera);
		gtd.translate(-(int)camera.getX(), -(int)camera.getY());
		gtd.setColor(Color.YELLOW);
		gtd.drawString(""+fps, 10, 20);
		gtd.drawImage(Assets.health[octet.bits()], (int)window.getWidth()/2 - Assets.health[octet.bits()].getWidth()/2, (int)window.getHeight() - 16, null);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressTime = System.currentTimeMillis();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		long releaseTime = System.currentTimeMillis(); // Record release time
		long holdDuration = releaseTime - pressTime; // Calculate hold duration
		// System.out.println("Mouse Hold Duration: " + holdDuration + " ms");
		// System.out.println("Mouse Clicked at X: " + e.getX() + " Y: " + e.getY());
		// System.out.println("Octet Located at X: " + octet.getX() + " Y: " + octet.getY());
		octet.shoot(holdDuration/100.0, e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
