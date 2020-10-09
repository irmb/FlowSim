package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Triangle;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;

public class PaintableTriangle extends PaintableShape {

    private Triangle triangle;

    public PaintableTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        return false;
    }

    @Override
    public Shape getShape() {
        return this.triangle;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        return null;
    }

    @Override
    public void paint(Painter painter, CoordinateTransformer transformer) {

    }
}
