package clients.gui.ui;

import controller.IController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7173421221802273308L;
	private final IController controller;
    private final JTextField delimiterField;

    public LoadPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // delimiter input
        this.add(new JLabel("Delimiter:"));
        delimiterField = new JTextField("\\t", 5); // default value: tab, width 5 chars
        this.add(delimiterField);

        // choose ini button
        JButton chooseConfigBtn = new JButton("Choose config.ini");
        chooseConfigBtn.addActionListener(e -> onChooseConfig());
        this.add(chooseConfigBtn);
    }

    private void onChooseConfig() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // start browsing in /src/main/resources/config if it exists
        File defaultDir = new File("src/main/resources/config");
        if (defaultDir.exists()) {
            fc.setCurrentDirectory(defaultDir);
        }

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String delimiter = delimiterField.getText().trim();
            try {
                controller.initializeFromIni(file.getAbsolutePath(), delimiter);
                JOptionPane.showMessageDialog(this,
                        "Configuration loaded successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Load failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
