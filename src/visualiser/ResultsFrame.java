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
import dungen.CellEntity;
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
	 * The cell image cache
	 */
	HashMap<String, BufferedImage> cellImages = new HashMap<String, BufferedImage>();


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
        
        // Draw each cell to the display.
        for (int y = 0; y < result.getConfiguration().height; y++) {
        	for (int x = 0; x < result.getConfiguration().width; x++) {
        		// Get the current cell.
        		Cell cell = result.getCells().get(x, y);
        		
        		// Get the cell type.
    			String cellType = cell.getType().toUpperCase();
    			
    			// Get the image for the cell type.
    			BufferedImage cellTypeImage = getCellImage(cellType);
    			
    			// Draw the cell image.
    			g.drawImage(cellTypeImage, x * Constants.RESULTS_FRAME_CELL_SIZE, y * Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, null);

    			// Draw each cell entity.
    			for (CellEntity entity : cell.getEntities()) {
					// Get the image for the cell entity.
					BufferedImage cellEntityImage = getCellImage(entity.getName());

					// Draw the cell entity image.
					g.drawImage(cellEntityImage, x * Constants.RESULTS_FRAME_CELL_SIZE, y * Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, Constants.RESULTS_FRAME_CELL_SIZE, null);
				}
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
	 * Gets the cell image.
	 * @param name The name.
	 * @return
	 */
	private BufferedImage getCellImage(String name) {
		if (this.cellImages.containsKey(name.toUpperCase())) {
			return this.cellImages.get(name.toUpperCase());
		}

		try {
			BufferedImage image = ImageIO.read(new File("images/" + name.toUpperCase() + ".png"));

			this.cellImages.put(name.toUpperCase(), image);

			return image;
		} catch (IOException e) {
			System.out.println("cannot open cell image '" + name.toUpperCase() + "' in images!");
			return getCellImage("UNKNOWN");
		}
	}
}