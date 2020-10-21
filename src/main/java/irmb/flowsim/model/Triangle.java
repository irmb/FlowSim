package irmb.flowsim.model;

import java.util.LinkedList;

public class Triangle implements TwoPointShape {

    private Point center;
    private double width;
    private double height;

    public void setFirst(Point point) {
        this.center = point;
    }

    public void setSecond(Point point) {
        this.width = Math.abs(this.center.getX() - point.getX());
        this.height = Math.abs(this.center.getY() - point.getY());
    }

    public Point getCenter() {
        return this.center;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public LinkedList<Point> getPoints() {
        LinkedList<Point> points = new LinkedList<>();
        points.add(new Point(center.getX(), center.getY()));
        points.add(new Point(center.getX() - width, center.getY() + height));
        points.add(new Point(center.getX() + width, center.getY() + height));
        return points;
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
