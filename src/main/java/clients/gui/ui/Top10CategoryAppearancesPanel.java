package clients.gui.ui;

import controller.IController;
import dto.Top10AppearanceDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Top10CategoryAppearancesPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5744806426915048871L;
	private final IController controller;
    private final JTable table;
    private final JPanel chartPanel;
    private final JButton loadBtn;

    public Top10CategoryAppearancesPanel(IController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        loadBtn = new JButton("Load Top-10 Category Appearances");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(loadBtn);
        this.add(top, BorderLayout.NORTH);

        table = new JTable();
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        chartPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -9087553412765616157L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintBarChart(g);
            }
        };
        chartPanel.setPreferredSize(new Dimension(400, 200));
        this.add(chartPanel, BorderLayout.SOUTH);

        loadBtn.addActionListener(e -> refresh());
    }

    private List<Top10AppearanceDTO> data;

    private void refresh() {
        data = controller.computeTop10CategoryAppearances();
        // update table
        String[] colNames = {"Product", "Count"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0);
        for (Top10AppearanceDTO t : data) {
            model.addRow(new Object[]{t.getName(), t.getCount()});
        }
        table.setModel(model);
        chartPanel.repaint();
    }

    private void paintBarChart(Graphics g) {
        if (data == null || data.isEmpty()) return;
        int w = chartPanel.getWidth();
        int h = chartPanel.getHeight();
        int barWidth = Math.max(2, w / data.size());
        int maxCount = data.stream().mapToInt(Top10AppearanceDTO::getCount).max().orElse(1);

        for (int i = 0; i < data.size(); i++) {
            Top10AppearanceDTO t = data.get(i);
            int barHeight = (int)((double)t.getCount() / maxCount * (h-20));
            g.setColor(Color.BLUE);
            g.fillRect(i * barWidth, h - barHeight, barWidth - 1, barHeight);
        }
    }
}
