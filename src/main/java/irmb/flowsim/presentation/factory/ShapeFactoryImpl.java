package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.*;

/**
 * Created by Sven on 14.12.2016.
 */
public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Shape makeShape(String type) {
        return switch (type) {
            case "Line" -> new Line();
            case "Circle" -> new Circle();
            case "Triangle" -> new Triangle();
            case "Rectangle" -> new Rectangle();
            case "PolyLine" -> new PolyLine();
            case "Bezier" -> new BezierCurve();
            case "Spline" -> new Spline();
            case "Delaunay" -> new Delaunay();
            default -> null;
        };
    }
}