package bj;

import javax.swing.*;
import java.awt.*;

import static bj.QLearning.winrate;

public class HeatmapVisualizer extends JPanel {

    private final int width, height;
    private final int[][] decision;

    private static final int MARGIN_LEFT = 70;
    private static final int MARGIN_BOTTOM = 50;
    private static final int MARGIN_TOP = 60;
    private static final int MARGIN_RIGHT = 100;

    private JFrame frame;

    public HeatmapVisualizer(int w, int h) {
        this.width = w;
        this.height = h;
        this.decision = new int[w][h];

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Blackjack Policy - Hit/Stand");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(this);
            frame.setSize(600, 900);
            frame.setVisible(true);
        });
    }

    public void update(double[][][] Q) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                decision[x][y] = (Q[x][y][1] > Q[x][y][0]) ? 1 : 0;
            }
        }
        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int gridWidth = getWidth() - MARGIN_LEFT - MARGIN_RIGHT;
        int gridHeight = getHeight() - MARGIN_TOP - MARGIN_BOTTOM;
        int cellWidth = gridWidth / height;
        int cellHeight = gridHeight / width;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Blackjack Q-Learning Policy", getWidth() / 2 - 100, 25);

        if (winrate != 0.0) {
            g2.setColor(new Color(0, 100, 0));
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            String winrateText = String.format("Win Rate: %.2f%%", winrate * 100);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(winrateText, getWidth() / 2 - fm.stringWidth(winrateText) / 2, 45);
        }

        for (int pIdx = 0; pIdx < width; pIdx++) {
            for (int dIdx = 0; dIdx < height; dIdx++) {
                boolean isHit = decision[pIdx][dIdx] == 1;

                int invertedPIdx = width - 1 - pIdx;

                int x = MARGIN_LEFT + dIdx * cellWidth;
                int y = MARGIN_TOP + invertedPIdx * cellHeight;

                g2.setColor(isHit ? new Color(200, 50, 50) : new Color(50, 150, 50));
                g2.fillRect(x, y, cellWidth, cellHeight);

                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x, y, cellWidth, cellHeight);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 11));
                String text = isHit ? "H" : "S";
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (cellWidth - fm.stringWidth(text)) / 2;
                int textY = y + (cellHeight + fm.getAscent()) / 2 - 2;
                g2.drawString(text, textX, textY);
            }
        }

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int dIdx = 0; dIdx < height; dIdx++) {
            int dealerCard = dIdx + 2;
            String label = (dealerCard == 11) ? "A" : String.valueOf(dealerCard);
            int x = MARGIN_LEFT + dIdx * cellWidth + cellWidth / 2;
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, x - fm.stringWidth(label) / 2, getHeight() - MARGIN_BOTTOM + 15);
        }

        for (int pIdx = 0; pIdx < width; pIdx++) {
            int invertedPIdx = width - 1 - pIdx;

            String label;
            if (pIdx < 18) {
                label = String.valueOf(pIdx + 4);
            } else {
                label = (pIdx - 18 + 12) + "*";
            }
            int y = MARGIN_TOP + invertedPIdx * cellHeight + cellHeight / 2 + 4;
            g2.drawString(label, MARGIN_LEFT - 30, y);
        }

        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Dealer Card", getWidth() / 2 - 50, getHeight() - 10);

        g2.rotate(-Math.PI / 2);
        g2.drawString("Player Score (* = usable Ace)", -getHeight() / 2 - 80, 15);
        g2.rotate(Math.PI / 2);

        int legendX = getWidth() - MARGIN_RIGHT + 10;
        int legendY = MARGIN_TOP + 10;
        g2.setFont(new Font("Arial", Font.BOLD, 11));

        g2.setColor(new Color(200, 50, 50));
        g2.fillRect(legendX, legendY, 20, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("H = Hit", legendX + 25, legendY + 12);

        g2.setColor(new Color(50, 150, 50));
        g2.fillRect(legendX, legendY + 25, 20, 15);
        g2.setColor(Color.BLACK);
        g2.drawString("S = Stand", legendX + 25, legendY + 37);

        int dividerPIdx = width - 18;
        int dividerY = MARGIN_TOP + dividerPIdx * cellHeight - 2;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(MARGIN_LEFT, dividerY, MARGIN_LEFT + gridWidth, dividerY);
    }
}
