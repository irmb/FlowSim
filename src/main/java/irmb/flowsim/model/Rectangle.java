package irmb.flowsim.model;

/**
 * Created by Sven on 14.12.2016.
 */
public class Rectangle implements TwoPointShape {

    private Point first;
    private Point second;

    public Rectangle() {
    }

    public void setFirst(Point first) {
        //TODO
    }

    public void setSecond(Point second) {
        //TODO
    }

    public Point getFirst() {
        //TODO
        return new Point(0,0);
    }

    public Point getSecond() {
        //TODO
        return new Point(0,0);
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
