package visualiser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONObject;
import dungen.Configuration;
import dungen.DungeonGenerator;
import dungen.Result;

/**
 * The main visualiser frame.
 */
public class VisualiserFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The frame fields.
	 */
	JTextField seedField;
	JTextField nameField;
	JTextField dungeonWidthField;
	JTextField dungeonHeightField;
	JTextField maxRoomSizeField;
	JTextField minRoomSizeField;
	JTextField roomBufferField;
	JTextField roomCountField;
	JTextField corridorWidthField;
	JTextArea patternsField;

	/**
     * Creates a new instance of the VisualiserFrame class.
     */
    public VisualiserFrame() {
        // Create the main panel.
        JPanel mainPanel = new JPanel();
        this.add(mainPanel);

        //##############################################################
        // Create the first panel.
        // This panel holds fields used to populate the generator config.
        //##############################################################
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        mainPanel.add(leftPanel);

        Configuration config = new Configuration();

        // Add the SEED field.
        leftPanel.add(new JLabel("SEED: "));
        seedField = new JTextField(20);
        seedField.setText(Long.toString(config.seed));
        leftPanel.add(seedField);
        
        // Add the NAME field.
        leftPanel.add(new JLabel("NAME: "));
        nameField = new JTextField(20);
        nameField.setText("untitled");
        leftPanel.add(nameField);

        // Add the DUNGEON WIDTH field.
        leftPanel.add(new JLabel("DUNGEON WIDTH: "));
        dungeonWidthField = new JTextField(20);
        dungeonWidthField.setText(Integer.toString(config.width));
        leftPanel.add(dungeonWidthField);

        // Add the DUNGEON HEIGHT field.
        leftPanel.add(new JLabel("DUNGEON HEIGHT: "));
        dungeonHeightField = new JTextField(10);
        dungeonHeightField.setText(Integer.toString(config.height));
        leftPanel.add(dungeonHeightField);
        
        // Add the MIN ROOM SIZE field.
        leftPanel.add(new JLabel("MIN ROOM SIZE: "));
        minRoomSizeField = new JTextField(20);
        minRoomSizeField.setText(Integer.toString(config.minRoomSize));
        leftPanel.add(minRoomSizeField);

        // Add the MAX ROOM SIZE field.
        leftPanel.add(new JLabel("MAX ROOM SIZE: "));
        maxRoomSizeField = new JTextField(20);
        maxRoomSizeField.setText(Integer.toString(config.maxRoomSize));
        leftPanel.add(maxRoomSizeField);

        // Add the ROOM BUFFER field.
        leftPanel.add(new JLabel("ROOM BUFFER: "));
        roomBufferField = new JTextField(20);
        roomBufferField.setText(Integer.toString(config.roomBuffer));
        leftPanel.add(roomBufferField);

        // Add the ROOM COUNT field.
        leftPanel.add(new JLabel("ROOM COUNT: "));
        roomCountField = new JTextField(20);
        roomCountField.setText(Integer.toString(config.rooms));
        leftPanel.add(roomCountField);

        // Add the CORRIDOR WIDTH field.
        leftPanel.add(new JLabel("CORRIDOR WIDTH: "));
        corridorWidthField = new JTextField(20);
        corridorWidthField.setText(Integer.toString(config.corridorWidth));
        leftPanel.add(corridorWidthField);

        leftPanel.add(new JLabel(" "));
        
        // Add the open file button.
        JButton openFileButton = new JButton("Open *.dungen.json file");
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onOpenFileButtonClick();
            }
        });
        leftPanel.add(openFileButton);


        // Add the generate button.
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onGenerateButtonClick(false);
            }
        });
        leftPanel.add(generateButton);
        
        // Add the generate button.
        JButton generateWithNewSeedButton = new JButton("Generate / New Seed");
        generateWithNewSeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onGenerateButtonClick(true);
            }
        });
        leftPanel.add(generateWithNewSeedButton);

        //##############################################################

        //##############################################################
        // Create the second panel.
        //##############################################################
        JPanel centerPanel = new JPanel();
        mainPanel.add(centerPanel);
        
        // Add the DUNGEON WIDTH field.
        centerPanel.add(new JLabel("PATTERNS: "));
        patternsField = new JTextArea(35, 40);
        JScrollPane sp = new JScrollPane(patternsField); 
        patternsField.setText("[]");
        centerPanel.add(sp);



        //##############################################################

        //##############################################################
        // Create the third panel.
        //##############################################################
        JPanel rightPanel = new JPanel();
        mainPanel.add(rightPanel);


        //##############################################################

        this.setTitle("DunGen Editor");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * Handler for clicks of the 'Open File' button.
     */
    private void onOpenFileButtonClick() {
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("DunGen Definition File", "json");
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setFileFilter(filter);
    	fileChooser.setCurrentDirectory(new File("."));
    	int result = fileChooser.showOpenDialog(this);
    	if (result == JFileChooser.APPROVE_OPTION) {
    	    File selectedFile = fileChooser.getSelectedFile();
    	    
    	    try {
    	    	JSONObject configFileJSON = FileUtilities.readJSONObjectFromFile(selectedFile);
    	    	this.nameField.setText(configFileJSON.getString("name"));
    	    	this.dungeonWidthField.setText(Integer.toString(configFileJSON.getInt("width")));
    	    	this.dungeonHeightField.setText(Integer.toString(configFileJSON.getInt("height")));
    	    	this.minRoomSizeField.setText(Integer.toString(configFileJSON.getInt("minRoomSize")));
    	    	this.maxRoomSizeField.setText(Integer.toString(configFileJSON.getInt("maxRoomSize")));
    	    	this.roomCountField.setText(Integer.toString(configFileJSON.getInt("rooms")));
    	    	this.roomBufferField.setText(Integer.toString(configFileJSON.getInt("roomBuffer")));
    	    	this.corridorWidthField.setText(Integer.toString(configFileJSON.getInt("corridorWidth")));
    	    	this.patternsField.setText(configFileJSON.getJSONArray("patterns").toString(4));
    	    	this.setTitle("DunGen Editor - " + configFileJSON.getString("name"));
    	    } catch (Exception e) {
    	    	JOptionPane.showMessageDialog(this, "not a valid json file: " + e.getMessage());
    	    }
    	}
    }

    /**
     * Handler for clicks of the 'Generate!' button.
     */
    private void onGenerateButtonClick(boolean setNewSeed) { 
        // We need a seed, populate the seed field if it is empty.
        if (this.seedField.getText().equals("") || setNewSeed)
        {
        	this.seedField.setText(Long.toString(new Random().nextLong()));
        }
        
        Configuration config = new Configuration();
        config.seed          = Long.parseLong(this.seedField.getText());
        config.width         = Integer.parseInt(this.dungeonWidthField.getText());
        config.height        = Integer.parseInt(this.dungeonHeightField.getText());
        config.minRoomSize   = Integer.parseInt(this.minRoomSizeField.getText());
        config.maxRoomSize   = Integer.parseInt(this.maxRoomSizeField.getText());
        config.rooms         = Integer.parseInt(this.roomCountField.getText());
        config.roomBuffer    = Integer.parseInt(this.roomBufferField.getText());
        config.corridorWidth = Integer.parseInt(this.corridorWidthField.getText());
        
        try {
        	// Generate dungeon!
        	Result result = DungeonGenerator.Generate(config, patternsField.getText());
    		
        	// Show results of the attempt.
    		ResultsFrame.create(Long.toString(result.getConfiguration().seed), result);
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}