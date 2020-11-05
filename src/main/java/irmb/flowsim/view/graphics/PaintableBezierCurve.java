package irmb.flowsim.view.graphics;

import irmb.flowsim.model.BezierCurve;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.util.List;

/** Created by sven on 19.03.17. */
public class PaintableBezierCurve extends PaintableShape {
    private final BezierCurve bezierCurve;
    private CoordinateTransformer transformer;
    private Point first;
    private Point second;

    public PaintableBezierCurve(BezierCurve bezierCurve, CoordinateTransformer transformer) {
        this.bezierCurve = bezierCurve;
        this.transformer = transformer;
    }

    @Override
    public void paint(Painter painter) {
        //TODO
    }

    private void recursivePaint(Painter painter, List<Point> pointList) {
        //TODO
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        //TODO
        return false;
    }

    private boolean isInXBounds(Point point) {
        double minX = Math.min(first.getX(), second.getX());
        double maxX = Math.max(first.getX(), second.getX());
        return point.getX() >= minX && point.getX() <= maxX;
    }

    @Override
    public Shape getShape() {
        return bezierCurve;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        List<Point> pointList = bezierCurve.getPointList();
        for (Point p : pointList) {
            if (getDistance(p, point) <= radius) return p;
        }
        return null;
    }
}
