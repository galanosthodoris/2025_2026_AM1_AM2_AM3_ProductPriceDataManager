package clients.gui.ui;

import controller.ControllerFactory;
import controller.IController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3425833718511895010L;
	private final IController controller =  ControllerFactory.createController();

	public MainFrame() {
		super("Produce Price Measurements");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);

		// Layout: left load panel, right tabbed pane with views
		JSplitPane split = new JSplitPane();
		split.setDividerLocation(300);

		LoadPanel loadPanel = new LoadPanel(controller);
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("All Prices", new AllYearsAllProductPricesPanel(controller));
		tabs.addTab("By Year", new YearViewPanel(controller));
		tabs.addTab("By Product", new ProductViewPanel(controller));
		tabs.addTab("Product Highlights", new ProductHighlightsPanel(controller));
		tabs.addTab("Category Highlights", new CategoryHighlightsPanel(controller));
		tabs.addTab("Product Stats", new ProductStatsPanel(controller));
		tabs.addTab("Top-10 Products", new Top10ProductAppearancesPanel(controller));
		tabs.addTab("Top-10 Categories", new Top10CategoryAppearancesPanel(controller));
		getContentPane().add(tabs);
		
		split.setLeftComponent(loadPanel);
		split.setRightComponent(tabs);

		getContentPane().add(split, BorderLayout.CENTER);
	}
}


