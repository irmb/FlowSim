package irmb.flowsim.presentation.builder;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.TwoPointShape;
import irmb.flowsim.presentation.factory.PaintableShapeFactory;
import irmb.flowsim.view.graphics.PaintableShape;

/**
 * Created by sven on 18.03.17.
 */
public class TwoPointShapeBuilder extends PaintableShapeBuilder {


    private final TwoPointShape shape;
    private int pointsAdded;
    private final PaintableShape paintableShape;

    public TwoPointShapeBuilder(TwoPointShape shape, PaintableShapeFactory paintableShapeFactory) {
        this.shape = shape;
        paintableShape = paintableShapeFactory.makePaintableShape(shape);
    }

    @Override
    public void addPoint(Point point) {
        if (pointsAdded == 0)
            shape.setFirst(point);
        else if (pointsAdded == 1)
            shape.setSecond(point);
        pointsAdded++;
    }

    @Override
    public PaintableShape getShape() {
        return paintableShape;
    }

    @Override
    public boolean isObjectFinished() {
        return pointsAdded >= 2;
    }

    @Override
    public void setLastPoint(Point lastPoint) {
        if (pointsAdded == 1)
            shape.setFirst(lastPoint);
        else
            shape.setSecond(lastPoint);
    }

    @Override
    public void removeLastPoint() {
        if (pointsAdded == 1) {
            shape.setFirst(null);
            pointsAdded--;
        } else if (pointsAdded == 2) {
            shape.setSecond(null);
            pointsAdded--;
        }
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
