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
        // TODO
    }

    @Override
    public PaintableShape getShape() {
        // TODO
        return paintableShape;
    }

    @Override
    public boolean isObjectFinished() {
        // TODO
        return false;
    }

    @Override
    public void setLastPoint(Point lastPoint) {
        // TODO
    }

    @Override
    public void removeLastPoint() {
        // TODO
    }

    @Override
    public boolean isObjectPaintable() {
        // TODO
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }
}
