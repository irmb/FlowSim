package irmb.flowsim.model;

public class Triangle implements TwoPointShape {

    @Override
    public void setFirst(Point point) {

    }

    @Override
    public void setSecond(Point point) {

    }

    @Override
    public void moveBy(double dx, double dy) {

    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
