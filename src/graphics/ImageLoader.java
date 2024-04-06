package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try (InputStream is = ImageLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.out.println("Resource not found: " + path);
                return null; // Or handle this case as you see fit.
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}

