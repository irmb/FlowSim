package irmb.flowsim.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sven on 14.12.2016.
 */
public class PolyLine implements MultiPointShape {

    private List<Point> pointList = new LinkedList<>();

    public List<Point> getPointList() {
        //TODO
        return Collections.emptyList();
    }

    public void addPoint(Point point) {
        //TODO
    }

    public void setLastPoint(Point point) {
        //TODO
    }

    public void removeLastPoint() {
        //TODO
    }

    public void moveBy(double dx, double dy) {
        //TODO
    }

    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
