package irmb.flowsim.presentation.builder;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Triangle;
import irmb.flowsim.view.graphics.PaintableShape;
import irmb.flowsim.view.graphics.PaintableTriangle;

public class PaintableTriangleBuilder extends PaintableShapeBuilder {

    private final Triangle triangle = new Triangle();
    private int pointsAdded;

    @Override
    public void addPoint(Point point) {
        //TODO
    }

    private void setPointByIndex(Point point) {
        //TODO
    }

    @Override
    public PaintableShape getShape() {
        //TODO
        return null;
    }

    @Override
    public boolean isObjectFinished() {
        //TODO
        return false;
    }

    @Override
    public void setLastPoint(Point lastPoint) {
        //TODO
    }

    @Override
    public void removeLastPoint() {
        //TODO
    }

    @Override
    public boolean isObjectPaintable() {
        //TODO
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }
}
