package irmb.flowsim.model;

import java.util.List;

public class Spline implements MultiPointShape {

    @Override
    public void addPoint(Point point) {

    }

    @Override
    public void setLastPoint(Point point) {

    }

    @Override
    public void removeLastPoint() {

    }

    @Override
    public List<Point> getPointList() {
        return null;
    }

    @Override
    public void moveBy(double dx, double dy) {

    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
