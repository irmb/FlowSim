package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Circle;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

public class PaintableCircle extends PaintableShape {

    private Circle circle;
    private CoordinateTransformer transformer;

    public PaintableCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public void paint(Painter painter, CoordinateTransformer transformer) {
        painter.setColor(Color.BLACK);
        this.transformer = transformer;
        Point center = transformer.transformToPointOnScreen(this.circle.getCenter());
        painter.paintCircle(center.getX(), center.getY(), transformer.scaleToScreenLength(this.circle.getRadius()));
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
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
