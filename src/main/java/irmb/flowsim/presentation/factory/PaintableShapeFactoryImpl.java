package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.*;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.view.graphics.*;

/** Created by sven on 18.03.17. */
public class PaintableShapeFactoryImpl implements PaintableShapeFactory {

    private final CoordinateTransformer transformer;

    public PaintableShapeFactoryImpl(CoordinateTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public PaintableShape makePaintableShape(Shape shape) {
        // TODO
        return null;
    }
}
