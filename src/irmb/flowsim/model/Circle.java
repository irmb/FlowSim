package irmb.flowsim.model;

public class Circle implements TwoPointShape {

    private double radius;
    private Point center;

    public Circle() {
        this.radius = 0.0;
        this.center = new Point(0.0, 0.0);
    }

    public Circle(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void setFirst(Point point) {
        this.center.setX(point.getX());
        this.center.setY(point.getY());
    }

    @Override
    public void setSecond(Point point) {
        this.radius = Math.sqrt(Math.pow(center.getX() - point.getX(), 2) + Math.pow(center.getY() - point.getY(), 2));
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void moveBy(double dx, double dy) {
        this.center.setX(this.center.getX() + dx);
        this.center.setY(this.center.getY() + dy);
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
