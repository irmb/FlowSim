package irmb.flowsim.view.swing;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.awt.*;

/** Created by Sven on 15.12.2016. */
public class SwingPainter implements Painter {

    private Graphics graphics;
    private CoordinateTransformer transformer;

    public SwingPainter(CoordinateTransformer transformer) {
        this.transformer = transformer;
    }

    public SwingPainter(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void paintLine(double x1, double y1, double x2, double y2) {
        graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    public void paintLine(Point first, Point second) {
        var viewFirst = transformer.transformToPointOnScreen(first);
        var viewSecond = transformer.transformToPointOnScreen(second);
        graphics.drawLine(
                (int) viewFirst.getX(),
                (int) viewFirst.getY(),
                (int) viewSecond.getX(),
                (int) viewSecond.getY());
    }

    @Override
    public void paintRectangle(double x, double y, double width, double height) {
        graphics.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    public void paintRectangle(Point origin, double width, double height) {
        var viewOrigin = transformer.transformToPointOnScreen(origin);
        var viewWidth = transformer.scaleToScreenLength(width);
        var viewHeight = transformer.scaleToScreenLength(height);
        graphics.drawRect(
                (int) viewOrigin.getX(),
                (int) viewOrigin.getY(),
                (int) viewWidth,
                (int) viewHeight);
    }

    @Override
    public void paintCircle(double x, double y, double radius) {
        graphics.drawOval((int) x, (int) y, (int) (radius), (int) (radius));
    }

    public void paintCircle(Point center, double radius) {
        var viewCenter = transformer.transformToPointOnScreen(center);
        var viewRadius = transformer.scaleToScreenLength(radius);
        graphics.drawOval(
                (int) (viewCenter.getX() - viewRadius),
                (int) (viewCenter.getY() - viewRadius),
                (int) viewRadius * 2,
                (int) viewRadius * 2);
    }

    @Override
    public void paintTriangle(double x, double y, double width, double height) {
        Polygon polygon = new Polygon();
        polygon.addPoint((int) x, (int) y);
        polygon.addPoint((int) (x - width), (int) (y + height));
        polygon.addPoint((int) (x + width), (int) (y + height));
        graphics.drawPolygon(polygon);
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

    public void paintPoint(Point point) {
        var viewPoint = transformer.transformToPointOnScreen(point);
        graphics.drawLine(
                (int) viewPoint.getX() - 5,
                (int) viewPoint.getY() - 5,
                (int) viewPoint.getX() + 5,
                (int) viewPoint.getY() + 5);
        graphics.drawLine(
                (int) viewPoint.getX() + 5,
                (int) viewPoint.getY() - 5,
                (int) viewPoint.getX() - 5,
                (int) viewPoint.getY() + 5);
    }

    @Override
    public void paintString(String s, double x, double y) {
        graphics.drawString(s, (int) x, (int) y);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}
