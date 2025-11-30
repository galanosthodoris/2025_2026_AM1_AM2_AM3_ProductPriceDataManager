package clients.gui.ui;

import javax.swing.*;

import dto.MeasurementDTO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A tiny, simple line chart implementation for educational use. It expects measurements sorted by year.
 */
public class SimpleLineChartPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6247581323996458891L;
	private List<MeasurementDTO> data = new ArrayList<>();

    public SimpleLineChartPanel() { setPreferredSize(new Dimension(600, 300)); }

    public void setData(List<MeasurementDTO> measurements) {
        this.data = new ArrayList<>(measurements);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        // margins
        int left = 50, right = 20, top = 20, bottom = 50;
        g2.drawRect(left, top, w - left - right, h - top - bottom);
        if (data.isEmpty()) {
            g2.drawString("No data", w/2 - 20, h/2);
            return;
        }
        double minVal = Double.POSITIVE_INFINITY, maxVal = Double.NEGATIVE_INFINITY;
        int minYear = Integer.MAX_VALUE, maxYear = Integer.MIN_VALUE;
        for (MeasurementDTO m : data) {
            minVal = Math.min(minVal, m.getValue());
            maxVal = Math.max(maxVal, m.getValue());
            minYear = Math.min(minYear, m.getYear());
            maxYear = Math.max(maxYear, m.getYear());
        }
        if (minVal == maxVal) { minVal -= 1; maxVal += 1; }
        int plotW = w - left - right;
        int plotH = h - top - bottom;

        // axes labels
        g2.drawString(String.format("%.2f", maxVal), 5, top + 10);
        g2.drawString(String.format("%.2f", minVal), 5, top + plotH);

        // draw lines between points
        int n = data.size();
        int prevX = -1, prevY = -1;
        for (int i=0;i<n;i++){
            MeasurementDTO m = data.get(i);
            double xFrac = (n==1) ? 0.5 : (double) i / (n-1);
            int x = left + (int) (xFrac * plotW);
            double vFrac = (m.getValue() - minVal) / (maxVal - minVal);
            int y = top + plotH - (int) (vFrac * plotH);
            g2.fillOval(x-3, y-3, 6, 6);
            g2.drawString(String.valueOf(m.getYear()), x-10, h - 10);
            if (prevX != -1) {
                g2.drawLine(prevX, prevY, x, y);
            }
            prevX = x; prevY = y;
        }
    }
}