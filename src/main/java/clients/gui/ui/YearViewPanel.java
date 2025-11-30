package clients.gui.ui;

import controller.IController;
import dto.MeasurementDTO;
import dto.YearDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class YearViewPanel extends JPanel {

	private static final long serialVersionUID = 3076738168156339510L;
	@SuppressWarnings("unused")
	private final IController controller;
    private final JTable table;
    private YearTableModel tableModel;

    public YearViewPanel(IController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField yearField = new JTextField(6);
        JButton showBtn = new JButton("Show Year");
        top.add(new JLabel("Year:"));
        top.add(yearField);
        top.add(showBtn);

        tableModel = new YearTableModel();
        table = new JTable(tableModel);

        showBtn.addActionListener(e -> {
            try {
                int y = Integer.parseInt(yearField.getText().trim());
                YearDTO dto = controller.getYearMeasurements(y);
                tableModel.setData(dto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid integer year");
            }
        });

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // convenience: list available years via controller
        JButton listYears = new JButton("List available years");
        listYears.addActionListener(e -> {
            List<YearDTO> years = controller.listYears();
            String s = years.stream().map(YearDTO::getYear).map(Object::toString).collect(Collectors.joining(", "));
            JOptionPane.showMessageDialog(this, s.isEmpty() ? "No years" : s);
        });
        add(listYears, BorderLayout.SOUTH);
    }

    private static class YearTableModel extends AbstractTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = -8883995882206235789L;
		private String[] columnNames = new String[]{"Product", "Value"};
        private Object[][] data = new Object[0][0];

        public void setData(YearDTO dto) {
            List<MeasurementDTO> list = dto.getMeasurements();
            data = new Object[list.size()][2];
            for (int i=0;i<list.size();i++){
                data[i][0] = list.get(i).getProductName();
                data[i][1] = list.get(i).getValue();
            }
            fireTableStructureChanged();
        }

        @Override public int getRowCount() { return data.length; }
        @Override public int getColumnCount() { return columnNames.length; }
        @Override public String getColumnName(int col) { return columnNames[col]; }
        @Override public Object getValueAt(int row, int col) { return data[row][col]; }
    }

 // add a new method to YearViewPanel
    public void showYear(YearDTO yd) {
        this.removeAll();

        // --- table with all measurements ---
        String[] cols = {"Product", "Value"};
        Object[][] rows = yd.getMeasurements().stream()
                .map(m -> new Object[]{m.getProductName(), m.getValue()})
                .toArray(Object[][]::new);

        JTable table = new JTable(rows, cols);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- top10 + headlines (if present) ---
        if (!yd.getTop10Aliases().isEmpty()) {
            JPanel top10Panel = new JPanel(new GridLayout(0, 1));
            top10Panel.setBorder(BorderFactory.createTitledBorder("Top 10 Commodities"));

            List<String> aliases = yd.getTop10Aliases();
            List<String> headlines = yd.getTop10Headlines();
            for (int i = 0; i < aliases.size(); i++) {
                String alias = aliases.get(i);
                String headline = (i < headlines.size() ? headlines.get(i) : "");
                top10Panel.add(new JLabel(alias + (headline.isEmpty() ? "" : " — " + headline)));
            }
            this.add(top10Panel, BorderLayout.SOUTH);
        }

        this.revalidate();
        this.repaint();
    }
}
