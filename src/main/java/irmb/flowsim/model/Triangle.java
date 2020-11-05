package irmb.flowsim.model;

import java.util.LinkedList;
import java.util.List;

public class Triangle implements Shape {

    private Point firstPoint;
    private Point secondPoint;
    private Point thirdPoint;

    public Point getFirstPoint() {
        //TODO
        return new Point(0,0);
    }

    public void setFirstPoint(Point firstPoint) {
        //TODO
    }

    public Point getSecondPoint() {
        //TODO
        return new Point(0,0);
    }

    public void setSecondPoint(Point secondPoint) {
        //TODO
    }

    public Point getThirdPoint() {
        //TODO
        return new Point(0,0);
    }

    public void setThirdPoint(Point thirdPoint) {
        //TODO
    }

    public List<Point> getPointsAsList() {
        List<Point> points = new LinkedList<>();
        if (firstPoint != null) points.add(firstPoint);
        if (secondPoint != null) points.add(secondPoint);
        if (thirdPoint != null) points.add(thirdPoint);
        return points;
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
