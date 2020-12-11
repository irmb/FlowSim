package irmb.flowsim.presentation.builder;

import irmb.flowsim.model.MultiPointShape;
import irmb.flowsim.model.Point;
import irmb.flowsim.presentation.factory.PaintableShapeFactory;
import irmb.flowsim.view.graphics.PaintableShape;

/**
 * Created by sven on 21.03.17.
 */
public class MultiPointShapeBuilder extends PaintableShapeBuilder {


    private final MultiPointShape shape;
    private final PaintableShapeFactory paintableShapeFactory;
    private PaintableShape paintable;

    public MultiPointShapeBuilder(MultiPointShape shape, PaintableShapeFactory paintableShapeFactory) {
        this.shape = shape;
        this.paintableShapeFactory = paintableShapeFactory;
    }

    @Override
    public void addPoint(Point point) {
        // TODO
    }

    @Override
    public PaintableShape getShape() {
        //TODO
        return null;
    }

    @Override
    public boolean isObjectFinished() {
        return false;
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
    public boolean isObjectPaintable() {
        //TODO
        return false;
    }

    @Override
    public boolean isInfinite() {
        return true;
    }
}
