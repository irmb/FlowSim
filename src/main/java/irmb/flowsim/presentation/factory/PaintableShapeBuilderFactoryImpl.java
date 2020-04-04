package irmb.flowsim.presentation.factory;

import irmb.flowsim.presentation.builder.MultiPointShapeBuilder;
import irmb.flowsim.presentation.builder.PaintableLineBuilder;
import irmb.flowsim.presentation.builder.PaintableRectangleBuilder;
import irmb.flowsim.presentation.builder.PaintableShapeBuilder;

/**
 * Created by Sven on 14.12.2016.
 */
public class PaintableShapeBuilderFactoryImpl implements PaintableShapeBuilderFactory {
    private MultiPointShapeFactory factory;
    private PaintableShapeFactory paintableShapeFactory;

    public PaintableShapeBuilderFactoryImpl(MultiPointShapeFactory factory, PaintableShapeFactory paintableShapeFactory) {
        this.factory = factory;
        this.paintableShapeFactory = paintableShapeFactory;
    }

    @Override
    public PaintableShapeBuilder makeShapeBuilder(String type) {
        switch (type) {
            case "Line":
                return new PaintableLineBuilder();
            case "Rectangle":
                return new PaintableRectangleBuilder();
            case "PolyLine":
                return new MultiPointShapeBuilder(factory.makeShape(type), paintableShapeFactory);
            case "Bezier":
                return new MultiPointShapeBuilder(factory.makeShape(type), paintableShapeFactory);
            default:
                return null;
        }
    }

    @Override
    public String[] getShapeChoices() {
        return new String[] {"Line", "Rectangle", "PolyLine", "Bezier"};
    }
}
