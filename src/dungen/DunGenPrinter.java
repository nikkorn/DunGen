package dungen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import dungen.tile.Tile;

/**
 * Prints a dungeon to an image.
 */
public class DunGenPrinter {
	
	/**
	 * Print a dungeon layout to an image on disk.
	 * @param name
	 * @param path
	 * @param dungeon
	 */
	public static void print(String name, String path, Dungeon dungeon) {
		int size   = 400;
		int offset = size / 2;
		
		File outputfile     = new File(path + name + ".png");
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		// Generate each static tile and draw it to our image.
		for (Tile tile : dungeon.getTiles()) {
			// Get the pixel x/y based on the tile position.
			int x = tile.getX() + offset;
			int y = tile.getY() + offset;
			
			// Set the pixel colour at the x/y position.
			image.setRGB(x, y, getTileColour(tile));
		}
		
		// Try to write the image to disk.
		try {
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the colour to use in drawing a tile.
	 * @param tile The tile.
	 * @return The colour to use in drawing a tile.
	 */
	private static int getTileColour(Tile tile) {
		switch (tile.getType()) {
			case EMPTY:
				return createColour(100, 100, 100);
			case ENTRANCE:
				return createColour(150, 150, 250);
			case WALL:
				return createColour(255, 255, 255);
			default:
				return createColour(0, 0, 0);
		}
	}
	
	/**
	 * Create a colour int based on RGB values.
	 * @param red 
	 * @param green
	 * @param blue
	 * @return A colour int based on RGB values.
	 */
	public static int createColour(int red, int green, int blue) {
		return (red << 16) | (green << 8) | blue;
	}
}
