package irmb.flowsim.model;

import java.util.List;

public class Spline implements MultiPointShape {

    @Override
    public void addPoint(Point point) {
        //TODO
    }

    @Override
    public void setLastPoint(Point point) {
        //TODO
    }

    @Override
    public void removeLastPoint() {
        //TODO
    }

    @Override
    public List<Point> getPointList() {
        return null;
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
