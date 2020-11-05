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
        //TODO
        return false;
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
        //TODO
    }
}
