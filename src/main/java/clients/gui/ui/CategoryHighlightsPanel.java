package clients.gui.ui;

import controller.IController;
import dto.CategoryHighlightDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryHighlightsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 330000719540580820L;
	private final IController controller;
    private final JTable table;
    private final JTextField categoryField;
    private final JButton loadBtn;

    public CategoryHighlightsPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Category:"));
        categoryField = new JTextField(10);
        top.add(categoryField);
        loadBtn = new JButton("Load Highlights");
        top.add(loadBtn);
        this.add(top, BorderLayout.NORTH);

        table = new JTable();
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        String category = categoryField.getText().trim();
        if (category.isEmpty()) return;

        List<CategoryHighlightDTO> highlights = controller.reportCategoryHighlights(category);
        String[] colNames = {"Year", "Product", "Headline"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (CategoryHighlightDTO h : highlights) {
            model.addRow(new Object[]{h.getYear(), h.getProduct(), h.getHeadline()});
        }
        table.setModel(model);
    }
}
