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

    public void paint(Painter painter, CoordinateTransformer transformer) {
        Point center = circle.getCenter();
        double radius = circle.getRadius();
        double min_x = transformer.transformToPointOnScreen(new Point(center.getX() - radius, center.getY() - radius)).getX();
        double max_x = transformer.transformToPointOnScreen(new Point(center.getX() + radius, center.getY() + radius)).getX();
        double view_radius = 0.5*(max_x - min_x);
        Point view = transformer.transformToPointOnScreen(center);
        painter.setColor(Color.BLACK);
        painter.paintCircle(view.getX()-view_radius, view.getY()-view_radius, 2*view_radius);
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        double min_distance = this.circle.getRadius() - radius;
        double max_distance = this.circle.getRadius() + radius;
        Point center = this.circle.getCenter();
        double distance = Math.sqrt((center.getX() - point.getX()) * (center.getX() - point.getX()) + (center.getY() - point.getY()) * (center.getY() - point.getY()));
        System.out.println(min_distance + " <= " + distance + " <= " + max_distance);
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
