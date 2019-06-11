package dungen.printing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import dungen.Dungeon;
import dungen.tile.ITileDetails;

/**
 * Prints a dungeon to an image.
 */
public class DungeonPrinter {
	
	/**
	 * Print a dungeon layout to an image on disk.
	 * @param name The image file name.
	 * @param path The image file path.
	 * @param dungeon The dungeon.
	 * @param tileColourPicker The tile colour picker.
	 */
	public static void print(String name, String path, Dungeon dungeon, ITileColourPicker tileColourPicker) {
		int size   = 400;
		int offset = size / 2;
		
		File outputfile     = new File(path + name + ".png");
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		// Generate each static tile and draw it to our image.
		for (ITileDetails tile : dungeon.getTiles()) {
			// Get the pixel x/y based on the tile position.
			int x = tile.getX() + offset;
			int y = tile.getY() + offset;
			
			// Set the pixel colour at the x/y position.
			image.setRGB(x, y, tileColourPicker.getTileColour(tile));
		}
		
		// Try to write the image to disk.
		try {
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a colour integer based on RGB values.
	 * @param red The red value.
	 * @param green The green value.
	 * @param blue The blue value.
	 * @return A colour integer based on RGB values.
	 */
	public static int createColour(int red, int green, int blue) {
		return (red << 16) | (green << 8) | blue;
	}
}
