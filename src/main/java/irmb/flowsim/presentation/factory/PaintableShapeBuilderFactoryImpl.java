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
        return switch (type) {
            case "Line", "Circle", "Rectangle" -> new TwoPointShapeBuilder((TwoPointShape) factory.makeShape(type), paintableShapeFactory);
            case "PolyLine", "Bezier", "Spline" -> new MultiPointShapeBuilder((MultiPointShape) factory.makeShape(type), paintableShapeFactory);
            case "Triangle" -> new PaintableTriangleBuilder();
            default -> null;
        };
    }

    @Override
    public String[] getShapeChoices() {
        return new String[]{"Line", "Circle", "Triangle", "Rectangle", "PolyLine", "Bezier", "Spline"};
    }
}
