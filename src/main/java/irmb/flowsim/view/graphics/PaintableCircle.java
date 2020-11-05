package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Circle;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

public class PaintableCircle extends PaintableShape {

    private final Circle circle;

    public PaintableCircle(Circle circle) {
        this.circle = circle;
    }

    public void paint(Painter painter) {
        //TODO
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        //TODO
        return false;
    }

    @Override
    public Shape getShape() {
        return this.circle;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        return null;
    }
}
