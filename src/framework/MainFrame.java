package framework;

import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 2228924937522267806L;

	public MainFrame() {
		GamePanel panel = new GamePanel(this);
		panel.setLocation(0, 0);
		panel.setSize(this.getSize());
		panel.setBackground(new Color(10, 0, 0));
		panel.setVisible(true);
		this.add(panel);
		
		addKeyListener(new KeyChecker(panel));
	}
	
}
