package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.*;

/**
 * Created by Sven on 14.12.2016.
 */
public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Shape makeShape(String type) {
        switch (type) {
            case "Line":
                return new Line();
            case "Circle":
                return new Circle();
            case "Triangle":
                return new Triangle();
            case "Rectangle":
                return new Rectangle();
            case "PolyLine":
                return new PolyLine();
            case "Bezier":
                return new BezierCurve();
            case "Spline":
                return new Spline();
            default:
                return null;
        }
    }
}
