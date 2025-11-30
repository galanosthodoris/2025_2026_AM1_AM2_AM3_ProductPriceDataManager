package clients.gui.ui;

import controller.IController;
import dto.ProductStatsDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductStatsPanel extends JPanel {
	private static final long serialVersionUID = -666429705195780221L;
	private final IController controller;
    private final JTable table;
    private final JButton loadBtn;

    public ProductStatsPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadBtn = new JButton("Load Product Stats");
        top.add(loadBtn);
        this.add(top, BorderLayout.NORTH);

        table = new JTable();
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        List<ProductStatsDTO> stats = controller.computeProductStats();
        String[] colNames = {"Product", "Min", "Average", "Max", "Last"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (ProductStatsDTO s : stats) {
            model.addRow(new Object[]{s.getProduct(), s.getMin(), s.getAverage(), s.getMax(), s.getLastValue()});
        }
        table.setModel(model);
    }
}
