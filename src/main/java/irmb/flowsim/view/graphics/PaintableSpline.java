package irmb.flowsim.view.graphics;

import java.util.LinkedList;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Spline;
import irmb.flowsim.presentation.Painter;

public class PaintableSpline extends PaintableShape {

    private Spline spline;

    public PaintableSpline(Spline spline) {
        this.spline = spline;
    }

    @Override
    public void paint(Painter painter) {
        //TODO
    }

    private void recursivePaint(Painter painter, LinkedList<Point> pointList, LinkedList<Double> gradientListX,
    LinkedList<Double> gradientListY) {
        //TODO
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        //TODO
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
}
