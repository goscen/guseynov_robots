package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
//Допилить observe и observable
public class View {
    JPanel mainPanel = new JPanel();
    Model model = new Model();

    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    View() {
        JLabel labelX = new JLabel(Double.toString(model.getM_robotPositionX()));
        JLabel labelY = new JLabel(Double.toString(model.getM_robotPositionY()));

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.onModelUpdateEvent();
            }
        }, 0, 10);
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setTargetPosition(e.getPoint());
                mainPanel.repaint();
            }
        });

        mainPanel.setDoubleBuffered(true);
        mainPanel.add(labelX);
        mainPanel.add(labelY);

    }

    public void paint(Graphics g) {
        paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, Model.round(model.getM_robotPositionX()), Model.round(model.getM_robotPositionY()), model.getM_robotDirection());
        drawTarget(g2d, model.getM_targetPositionX(), model.getM_targetPositionY());
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = Model.round(model.getM_robotPositionX());
        int robotCenterY = Model.round(model.getM_robotPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private void onRedrawEvent() {
        EventQueue.invokeLater(this.mainPanel::repaint);
    }

}
