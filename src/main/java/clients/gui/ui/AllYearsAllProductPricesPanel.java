package clients.gui.ui;

import controller.IController;
import dto.YearDTO;
import dto.MeasurementDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AllYearsAllProductPricesPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8005403345531570250L;
	private final IController controller;
    private final JTable table;
    private final JButton loadBtn;

    public AllYearsAllProductPricesPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        loadBtn = new JButton("Load All Prices");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadBtn);
        this.add(top, BorderLayout.NORTH);

        table = new JTable();
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        List<YearDTO> years = controller.reportAllYearsAllProductPrices();
        if (years.isEmpty()) return;

        // get product aliases from first year
        List<String> productAliases = years.get(0).getMeasurements().stream()
                .map(MeasurementDTO::getProductName)
                .collect(Collectors.toList());

        String[] colNames = new String[productAliases.size()+1];
        colNames[0] = "Year";
        for (int i = 0; i < productAliases.size(); i++) {
            colNames[i+1] = productAliases.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (YearDTO y : years) {
            Object[] row = new Object[colNames.length];
            row[0] = y.getYear();
            for (MeasurementDTO m : y.getMeasurements()) {
                int idx = productAliases.indexOf(m.getProductName());
                if (idx >= 0) row[idx+1] = m.getValue();
            }
            model.addRow(row);
        }
        table.setModel(model);
    }
}
