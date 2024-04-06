package graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage[] octet = new BufferedImage[3];
	public static BufferedImage[] tile = new BufferedImage[2];
	public static BufferedImage virusFace, virusBody,
								redlight, greenlight;

	public static void init(){
		
		SpriteSheet sprites = new SpriteSheet(ImageLoader.loadImage("/sprites.png"));
		SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/tiles.png"));
		SpriteSheet filter = new SpriteSheet(ImageLoader.loadImage("/redlight.png"));
		SpriteSheet filter2 = new SpriteSheet(ImageLoader.loadImage("/greenlight.png"));
//		int i = 0;
//		while(i < octet.length) {
//			octet[i]
//		}
		octet[0] = sprites.crop(0, 0, width, height);
		octet[1] = sprites.crop(width, 0, width, height);
		octet[2] = sprites.crop(width * 2, 0, width, height);
		virusFace = sprites.crop(0, height, width * 2, height * 2);
		virusBody = sprites.crop(width * 2, height * 2, width * 2, height * 2);
		tile[0] = tiles.crop(0, 0 , width*2, height*2);
		tile[1] = tiles.crop(width*2, 0, width*2, height*2);
		redlight = filter.crop(0, 0, 832, 832);
		greenlight = filter2.crop(0, 0, 832, 832);
	}
}
