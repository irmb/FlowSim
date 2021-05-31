package irmb.flowsim.view.graphics;

import irmb.flowsim.model.BezierCurve;
import irmb.flowsim.model.Delaunay;
import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.util.LinkedList;
import java.util.List;

public class PaintableDelaunay extends PaintableShape {
    private final Delaunay delaunay;
    private CoordinateTransformer transformer;

    public PaintableDelaunay(Delaunay delaunay, CoordinateTransformer transformer) {
        this.delaunay = delaunay;
        this.transformer = transformer;
    }

    @Override
    public void paint(Painter painter) {
        painter.setColor(Color.BLACK);
    }

    @Override
    public Shape getShape() {
        return delaunay;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        List<Point> pointList = delaunay.getPointList();
        for (Point p : pointList) {
            if (getDistance(p, point) <= radius) return p;
        }
        return null;
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        return false;
    }
    
}
