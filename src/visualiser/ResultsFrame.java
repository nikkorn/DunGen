package visualiser;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import dungen.Cell;
import dungen.Result;

/**
 * The results frame.
 */
public class ResultsFrame extends JPanel {	
	private static final long serialVersionUID = 1L;
	/**
	 * The results.
	 */
	private Result result;

	/**
	 * Creates a new instance of the ResultsFrame class.
	 * @param result The generated result.
	 */
	private ResultsFrame(Result result) {
		this.result = result;
		int frameWidth  = result.getConfiguration().width * Constants.RESULTS_FRAME_CELL_SIZE;
		int frameHeight = result.getConfiguration().height * Constants.RESULTS_FRAME_CELL_SIZE;
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		this.setVisible(true);
	}
	
	/**
	 * Paint the display.
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
		HashMap<String, BufferedImage> cellTypeImages = new HashMap<String, BufferedImage>();
		try {
			cellTypeImages.put("UNKNOWN_CELL_TYPE", ImageIO.read(new File("cell_images/UNKNOWN_CELL_TYPE.png")));
			cellTypeImages.put("WALL", ImageIO.read(new File("cell_images/WALL.png")));
			cellTypeImages.put("ROOM", ImageIO.read(new File("cell_images/ROOM.png")));
			cellTypeImages.put("CORRIDOR", ImageIO.read(new File("cell_images/CORRIDOR.png")));
		} catch (IOException e) {
			System.out.println("cannot open default cell images in cell_images!");
		}
        
        // Draw each pixel to the display.
        for (int y = 0; y < result.getConfiguration().height; y++) {
        	for (int x = 0; x < result.getConfiguration().width; x++) {
        		// Get the current cell.
        		Cell cell = result.getCells().get(x, y);
        		
        		// Get the cell type.
    			String cellType = cell.getType().toUpperCase();
    			
    			// Get the image for the cell type.
    			BufferedImage cellImage = getCellTypeImage(cellType, cellTypeImages);
    			
    			// Draw the cell image.
    			g.drawImage(cellImage, x * Constants.RESULTS_FRAME_CELL_SIZE, y * Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, null);
            }        	
        }
    }
	
	/**
	 * Creates a ResultsFrame instance wrapped in a JFrame.
	 */
	public static ResultsFrame create(String title, Result result) {
		// Create the ResultsFrame.
		ResultsFrame display = new ResultsFrame(result);
		
		// Create the application jframe in which to show the display. 
		JFrame frame = new JFrame(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(display);
		frame.pack();
		frame.setVisible(true);
		
		return display;
	}
	
	/**
	 * Gets the cell image for a cell image type.
	 * @param type
	 * @param cellTypeImages
	 * @return
	 */
	private BufferedImage getCellTypeImage(String type, HashMap<String, BufferedImage> cellTypeImages) {
		// Do we already have the cell type image cached?
		if (cellTypeImages.containsKey(type)) {
			return cellTypeImages.get(type);
		}
		
		// Grab a file handle for the image, which may not exist.
		File imageFile = new File("cell_images/" + type + ".png");
		
		// If no image exists for the cell type then just return the default unknown cell image.
		if (!imageFile.exists()) {
			System.out.println("cannot find cell image for cell type '" + type + "' in cell_images!");
			cellTypeImages.put(type, cellTypeImages.get("UNKNOWN_CELL_TYPE"));
		} else {
			BufferedImage cellImage;
			try {
				cellImage = ImageIO.read(imageFile);
			} catch (IOException e) {
				System.out.println("cannot open cell image for cell type '" + type + "' in cell_images!");
				cellImage = cellTypeImages.get("UNKNOWN_CELL_TYPE");
			}
			cellTypeImages.put(type, cellImage);
		}
		
		return cellTypeImages.get(type);
	}
}