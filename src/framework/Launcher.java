package framework;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.WindowConstants;

public class Launcher {
	
	private static int grid = 64;
	private static int width = 1920;
	private static int height = 1080;
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		
		frame.setSize(getWidth(), getHeight());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()/2 - frame.getSize().getWidth()/2),
						(int)(screenSize.getHeight()/2 - frame.getSize().getHeight()/2));
		frame.setResizable(true);
		frame.setTitle("Octet 4");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static int getGrid() {
		return grid;
	}
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}


}
