package irmb.flowsim.model;

public class Triangle implements TwoPointShape {

    private Point center;
    private double width;
    private double height;

    @Override
    public void setFirst(Point point) {
        this.center = point;
    }

    @Override
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

    public Point[] getPoints() {
        Point[] points = new Point[3];
        points[0] = new Point(center.getX(), center.getY());
        points[1] = new Point(center.getX() - width, center.getY() + height);
        points[2] = new Point(center.getX() + width, center.getY() + height);
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
