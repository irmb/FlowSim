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
        var center = circle.getCenter();
        var radius = circle.getRadius();
        painter.setColor(Color.BLACK);
        painter.paintCircle(center, radius);
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        var min_distance = circle.getRadius() - radius;
        var max_distance = circle.getRadius() + radius;
        var center = circle.getCenter();
        var dx = center.getX() - point.getX();
        var dy = center.getY() - point.getY();
        var distance = Math.sqrt(dx * dx + dy * dy);

        return (distance >= min_distance && distance <= max_distance);
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
