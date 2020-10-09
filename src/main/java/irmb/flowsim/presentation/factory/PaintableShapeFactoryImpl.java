package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.BezierCurve;
import irmb.flowsim.model.Circle;
import irmb.flowsim.model.Line;
import irmb.flowsim.model.PolyLine;
import irmb.flowsim.model.Rectangle;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Spline;
import irmb.flowsim.model.Triangle;
import irmb.flowsim.view.graphics.PaintableBezierCurve;
import irmb.flowsim.view.graphics.PaintableCircle;
import irmb.flowsim.view.graphics.PaintableLine;
import irmb.flowsim.view.graphics.PaintablePolyLine;
import irmb.flowsim.view.graphics.PaintableRectangle;
import irmb.flowsim.view.graphics.PaintableShape;
import irmb.flowsim.view.graphics.PaintableSpline;
import irmb.flowsim.view.graphics.PaintableTriangle;

/**
 * Created by sven on 18.03.17.
 */
public class PaintableShapeFactoryImpl implements PaintableShapeFactory {
    @Override
    public PaintableShape makePaintableShape(Shape shape) {
        if (shape instanceof Line)
            return new PaintableLine((Line) shape);
        else if (shape instanceof Circle)
            return new PaintableCircle((Circle) shape);
        else if (shape instanceof Triangle)
            return new PaintableTriangle((Triangle) shape);
        else if (shape instanceof Rectangle)
            return new PaintableRectangle((Rectangle) shape);
        else if (shape instanceof BezierCurve)
            return new PaintableBezierCurve((BezierCurve) shape);
        else if (shape instanceof PolyLine)
            return new PaintablePolyLine((PolyLine) shape);
        else if (shape instanceof Spline)
            return new PaintableSpline((Spline) shape);
        else return null;
    }
}
