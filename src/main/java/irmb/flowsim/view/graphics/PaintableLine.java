package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Line;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

/**
 * Created by Sven on 14.12.2016.
 */
public class PaintableLine extends PaintableShape {

    private final Line line;

    public PaintableLine(Line line) {
        this.line = line;
    }

    @Override
    public void paint(Painter painter) {
        painter.setColor(Color.BLACK);
        painter.paintLine(line.getFirst(), line.getSecond());
        painter.paintPoint(line.getFirst());
        painter.paintPoint(line.getSecond());
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        double maxX = Math.max(line.getFirst().getX(), line.getSecond().getX());
        double minX = Math.min(line.getFirst().getX(), line.getSecond().getX());
        if (point.getX() >= minX - radius && point.getX() <= maxX + radius)
            return getDistanceToLine(line.getFirst(), line.getSecond(), point) <= radius;
        return false;
    }


    @Override
    public Shape getShape() {
        return line;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        Point first = line.getFirst();
        Point second = line.getSecond();
        if (getDistance(point, first) <= radius)
            return first;
        else if (getDistance(point, second) <= radius)
            return second;
        return null;
    }
}