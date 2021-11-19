package irmb.flowsim.model;

import java.util.LinkedList;
import java.util.List;

public class Triangle implements Shape {

    private Point firstPoint;
    private Point secondPoint;
    private Point thirdPoint;

    public Point getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(Point firstPoint) {
        this.firstPoint = firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
    }

    public Point getThirdPoint() {
        return thirdPoint;
    }

    public void setThirdPoint(Point thirdPoint) {
        this.thirdPoint = thirdPoint;
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
        firstPoint.moveBy(dx, dy);
        secondPoint.moveBy(dx, dy);
        thirdPoint.moveBy(dx, dy);
    }
}
