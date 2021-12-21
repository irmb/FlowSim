package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.MultiPointShape;
import irmb.flowsim.model.TwoPointShape;
import irmb.flowsim.presentation.builder.MultiPointShapeBuilder;
import irmb.flowsim.presentation.builder.PaintableShapeBuilder;
import irmb.flowsim.presentation.builder.PaintableTriangleBuilder;
import irmb.flowsim.presentation.builder.TwoPointShapeBuilder;

/**
 * Created by Sven on 14.12.2016.
 */
public class PaintableShapeBuilderFactoryImpl implements PaintableShapeBuilderFactory {
    private ShapeFactory factory;
    private PaintableShapeFactory paintableShapeFactory;

    public PaintableShapeBuilderFactoryImpl(ShapeFactory factory, PaintableShapeFactory paintableShapeFactory) {
        this.factory = factory;
        this.paintableShapeFactory = paintableShapeFactory;
    }

    @Override
    public PaintableShapeBuilder makeShapeBuilder(String type) {
        // TODO
        return switch (type) {
            default -> null;
        };
    }

    @Override
    public String[] getShapeChoices() {
        // TODO
        return new String[0];
    }
}
