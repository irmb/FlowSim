package irmb.flowsim.model;

public class Circle implements TwoPointShape {

    private double radius;
    private Point center;

    public Circle() {
        //TODO
    }

    public Circle(double radius, Point center) {
        //TODO
    }

    public void setFirst(Point point) {
        //TODO
    }

    public void setSecond(Point point) {
        //TODO
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void moveBy(double dx, double dy) {
        //TODO
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
