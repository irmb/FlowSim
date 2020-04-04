package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.*;
import irmb.flowsim.presentation.factory.MultiPointShapeFactory;

/**
 * Created by Sven on 14.12.2016.
 */
public class ShapeFactoryImpl implements MultiPointShapeFactory {
    @Override
    public MultiPointShape makeShape(String type) {
        switch (type) {
            case "PolyLine":
                return new PolyLine();
            case "Bezier":
                return new BezierCurve();
            default:
                return null;
        }
    }
}
