package clients.gui;

import javax.swing.SwingUtilities;
import clients.gui.ui.MainFrame;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new clients.gui.ui.MainFrame();
            mf.setVisible(true);
        });
    }
}