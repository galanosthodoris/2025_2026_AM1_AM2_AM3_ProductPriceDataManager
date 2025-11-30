package clients.gui.ui;

import controller.IController;
import dto.MeasurementDTO;
import dto.ProductDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class ProductViewPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2208467962772099820L;
	@SuppressWarnings("unused")
	private final IController controller;
    private final JTable table;
    private final ProductTableModel tableModel;
    private final SimpleLineChartPanel chartPanel;

    public ProductViewPanel(IController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> productCombo = new JComboBox<>();
        JButton refresh = new JButton("Refresh Products");
        JTextField minYear = new JTextField(6);
        JTextField maxYear = new JTextField(6);
        JButton showBtn = new JButton("Show");

        top.add(new JLabel("Product:"));
        top.add(productCombo);
        top.add(refresh);
        top.add(new JLabel("minYear:")); top.add(minYear);
        top.add(new JLabel("maxYear:")); top.add(maxYear);
        top.add(showBtn);

        refresh.addActionListener(e -> {
            productCombo.removeAllItems();
            controller.listProducts().forEach(p -> productCombo.addItem(p.getName()));
        });

        tableModel = new ProductTableModel();
        table = new JTable(tableModel);
        chartPanel = new SimpleLineChartPanel();

        showBtn.addActionListener(e -> {
            String prod = (String) productCombo.getSelectedItem();
            if (prod == null) { JOptionPane.showMessageDialog(this, "Choose a product (refresh first)"); return; }
            try {
                if (!minYear.getText().trim().isEmpty() || !maxYear.getText().trim().isEmpty()) {
                    int min = minYear.getText().trim().isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(minYear.getText().trim());
                    int max = maxYear.getText().trim().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxYear.getText().trim());
                    ProductDTO dto = controller.filterProductMeasurements(prod, min, max);
                    tableModel.setData(dto.getMeasurements());
                    chartPanel.setData(dto.getMeasurements());
                } else {
                    ProductDTO dto = controller.getProductMeasurements(prod);
                    tableModel.setData(dto.getMeasurements());
                    chartPanel.setData(dto.getMeasurements());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Years must be integers if provided");
            }
        });

        add(top, BorderLayout.NORTH);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table), chartPanel);
        split.setDividerLocation(250);
        add(split, BorderLayout.CENTER);
    }

    private static class ProductTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = -6806455811626765246L;
		private String[] cols = new String[]{"Year", "Value"};
        private Object[][] data = new Object[0][0];

        public void setData(List<MeasurementDTO> list) {
            data = new Object[list.size()][2];
            for (int i=0;i<list.size();i++){
                data[i][0] = list.get(i).getYear();
                data[i][1] = list.get(i).getValue();
            }
            fireTableStructureChanged();
        }

        @Override public int getRowCount() { return data.length; }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r,int c){ return data[r][c]; }
    }
}
