package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Triangle;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.util.List;

public class PaintableTriangle extends PaintableShape {

    private final Triangle triangle;

    public PaintableTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        List<Point> points = this.triangle.getPointsAsList();
        double distance1 = getDistanceToLine(points.get(0), points.get(1), point);
        double distance2 = getDistanceToLine(points.get(1), points.get(2), point);
        double distance3 = getDistanceToLine(points.get(2), points.get(0), point);
        double minDistance = Math.min(Math.min(distance1, distance2), distance3);
        return minDistance <= radius;
    }

    @Override
    public Shape getShape() {
        return this.triangle;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        for (var trianglePoint : triangle.getPointsAsList())
            if (trianglePoint.distanceTo(point) <= radius) return trianglePoint;

        return null;
    }

    @Override
    public void paint(Painter painter) {
        var points = triangle.getPointsAsList();
        if (points.size() < 2) return;
        painter.setColor(Color.BLACK);

        painter.paintLine(points.get(0), points.get(1));
        if (points.size() < 3) return;

        painter.paintLine(points.get(1), points.get(2));
        painter.paintLine(points.get(0), points.get(2));
    }
}
