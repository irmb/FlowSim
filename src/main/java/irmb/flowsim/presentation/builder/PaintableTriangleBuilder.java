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
        setPointByIndex(point);
        pointsAdded++;
    }

    private void setPointByIndex(Point point) {
        if (pointsAdded == 0) triangle.setFirstPoint(point);
        else if (pointsAdded == 1) triangle.setSecondPoint(point);
        else if (pointsAdded == 2) triangle.setThirdPoint(point);
    }

    @Override
    public PaintableShape getShape() {
        return new PaintableTriangle(triangle);
    }

    @Override
    public boolean isObjectFinished() {
        return pointsAdded >= 3;
    }

    @Override
    public void setLastPoint(Point lastPoint) {
        if (pointsAdded == 1) triangle.setFirstPoint(lastPoint);
        else if (pointsAdded == 2) triangle.setSecondPoint(lastPoint);
        else triangle.setThirdPoint(lastPoint);
    }

    @Override
    public void removeLastPoint() {
        if (pointsAdded == 1) triangle.setFirstPoint(null);
        else if (pointsAdded == 2) triangle.setSecondPoint(null);
        else triangle.setThirdPoint(null);
        pointsAdded--;
    }

    @Override
    public boolean isObjectPaintable() {
        return pointsAdded >= 2;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }
}
