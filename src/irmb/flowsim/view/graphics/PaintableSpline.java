package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Spline;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;

public class PaintableSpline extends PaintableShape {

    private Spline spline;

    public PaintableSpline(Spline spline) {
        this.spline = spline;
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        return false;
    }

    @Override
    public Shape getShape() {
        return this.spline;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        return null;
    }

    @Override
    public void paint(Painter painter, CoordinateTransformer transformer) {

    }
}
