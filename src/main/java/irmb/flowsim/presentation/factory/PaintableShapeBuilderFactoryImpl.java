package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.MultiPointShape;
import irmb.flowsim.model.TwoPointShape;
import irmb.flowsim.presentation.builder.MultiPointShapeBuilder;
import irmb.flowsim.presentation.builder.PaintableShapeBuilder;
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
        switch (type) {
            case "Line":
            case "Circle":
            case "Triangle":
            case "Rectangle":
                return new TwoPointShapeBuilder((TwoPointShape) factory.makeShape(type), paintableShapeFactory);
            case "PolyLine":
            case "Bezier":
            case "Spline":
                return new MultiPointShapeBuilder((MultiPointShape) factory.makeShape(type), paintableShapeFactory);
            default:
                return null;
        }
    }

    @Override
    public String[] getShapeChoices() {
        return new String[]{"Line", "Circle", "Triangle", "Rectangle", "PolyLine", "Bezier", "Spline"};
    }
}
