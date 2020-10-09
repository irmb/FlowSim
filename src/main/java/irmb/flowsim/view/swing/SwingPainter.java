package irmb.flowsim.view.swing;

import java.awt.Graphics;

import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

/**
 * Created by Sven on 15.12.2016.
 */
public class SwingPainter implements Painter {

    private Graphics graphics;

    public SwingPainter() {
    }

    public SwingPainter(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void paintLine(double x1, double y1, double x2, double y2) {
        graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void paintRectangle(double x, double y, double width, double height) {
        graphics.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void paintCircle(double x, double y, double radius) {
        System.out.println("X: " + x + ", Y: " + y + ", radius: " + radius);
        graphics.drawOval((int) x, (int) y, (int) (radius), (int) (radius));
    }

    @Override
    public void setColor(Color color) {
        int r, g, b;
        r = color.r;
        g = color.g;
        b = color.b;
        java.awt.Color c = new java.awt.Color(r, g, b);
        graphics.setColor(c);
    }

    @Override
    public void fillRectangle(double x, double y, double width, double height) {
        graphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void paintPoint(double x, double y) {
        graphics.drawLine((int) x - 5, (int) y - 5, (int) x + 5, (int) y + 5);
        graphics.drawLine((int) x + 5, (int) y - 5, (int) x - 5, (int) y + 5);
    }

    @Override
    public void paintString(String s, double x, double y) {
        graphics.drawString(s, (int) x, (int) y);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}