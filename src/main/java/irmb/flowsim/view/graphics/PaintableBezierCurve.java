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
        painter.setColor(Color.BLACK);
        recursivePaint(painter, bezierCurve.getPointList());

        for (Point point : bezierCurve.getPointList()) {
            Point viewPoint = transformer.transformToPointOnScreen(point);
            painter.paintPoint(viewPoint.getX(), viewPoint.getY());
        }
    }

    private void recursivePaint(Painter painter, List<Point> pointList) {
        // TODO
    }


    // ## old paint method:
    //
    // @Override
    // public void paint(Painter painter) {
    //     List<Point> pointList = bezierCurve.getPointList();
    //     int numOfPoints = 100;
    //     if (pointList.size() >= 2) {
    //         for (int i = 0; i < numOfPoints-1; i++) {
    //             double t1 = (i) / (double) (numOfPoints - 1);
    //             double t2 = (i + 1.0) / (double) (numOfPoints - 1);

    //             Point p1 = bezierCurve.calculatePointWithBernstein(t1);
    //             Point p2 = bezierCurve.calculatePointWithBernstein(t2);

    //             painter.paintLine(new Point(p1.getX(), p1.getY()), new Point(p2.getX(), p2.getY()));
    //         }
    //     }
    // }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        if (getDefinedPoint(point, radius) != null) 
            return true;
        int numPoints = 100;
        for (int i = 0; i < numPoints - 1; i++) {
            double t1, t2;
            t1 = i / (double) (numPoints - 1);
            t2 = (i + 1) / (double) (numPoints - 1);
            first = bezierCurve.calculatePointWithBernstein(t1);
            second = bezierCurve.calculatePointWithBernstein(t2);
            if (isInXBounds(point)) {
                double distanceToLine = getDistanceToLine(first, second, point);
                if (distanceToLine <= radius) return true;
            }
        }
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
