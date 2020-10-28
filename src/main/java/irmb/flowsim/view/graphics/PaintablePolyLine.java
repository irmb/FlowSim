package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.PolyLine;
import irmb.flowsim.model.Shape;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.util.List;

/** Created by Sven on 15.12.2016. */
public class PaintablePolyLine extends PaintableShape {

    private final PolyLine polyLine;

    public PaintablePolyLine(PolyLine polyLine) {
        this.polyLine = polyLine;
    }

    @Override
    public void paint(Painter painter) {
        painter.setColor(Color.BLACK);
        List<Point> pointList = polyLine.getPointList();
        for (int i = 0; i < pointList.size() - 1; i++)
            painter.paintLine(pointList.get(i), pointList.get(i + 1));
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        List<Point> pointList = polyLine.getPointList();
        for (int i = 0; i < pointList.size() - 1; i++) {
            Point first = pointList.get(i);
            Point second = pointList.get(i + 1);
            double minX = Math.min(first.getX(), second.getX());
            double maxX = Math.max(first.getX(), second.getX());
            if (point.getX() >= minX - radius && point.getX() <= maxX + radius) {
                double distanceToLine = getDistanceToLine(first, second, point);
                if (distanceToLine <= radius) return true;
            }
        }
        return false;
    }

    @Override
    public Shape getShape() {
        return polyLine;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        List<Point> pointList = polyLine.getPointList();
        for (Point p : pointList) {
            if (getDistance(p, point) <= radius) return p;
        }
        return null;
    }
}
