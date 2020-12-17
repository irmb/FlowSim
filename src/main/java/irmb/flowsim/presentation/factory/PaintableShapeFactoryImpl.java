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
        if (shape instanceof Line) return new PaintableLine((Line) shape);
        else if (shape instanceof Circle) return new PaintableCircle((Circle) shape);
        else if (shape instanceof Triangle) return new PaintableTriangle((Triangle) shape);
        else if (shape instanceof Rectangle) return new PaintableRectangle((Rectangle) shape);
        else if (shape instanceof BezierCurve)
            return new PaintableBezierCurve((BezierCurve) shape, transformer);
        else if (shape instanceof PolyLine) return new PaintablePolyLine((PolyLine) shape);
        else if (shape instanceof Spline) return new PaintableSpline((Spline) shape);
        else return null;
    }
}