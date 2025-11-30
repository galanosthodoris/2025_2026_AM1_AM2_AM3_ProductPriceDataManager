package clients.gui.ui;

import controller.IController;
import dto.ProductHighlightDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductHighlightsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8629297245622906719L;
	private final IController controller;
    private final JTable table;
    private final JTextField productField;
    private final JButton loadBtn;

    public ProductHighlightsPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Product alias:"));
        productField = new JTextField(10);
        top.add(productField);
        loadBtn = new JButton("Load Highlights");
        top.add(loadBtn);
        this.add(top, BorderLayout.NORTH);

        table = new JTable();
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        String alias = productField.getText().trim();
        if (alias.isEmpty()) return;

        List<ProductHighlightDTO> highlights = controller.reportProductHighlights(alias);
        String[] colNames = {"Year", "Headline"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (ProductHighlightDTO h : highlights) {
            model.addRow(new Object[]{h.getYear(), h.getHeadline()});
        }
        table.setModel(model);
    }
}
