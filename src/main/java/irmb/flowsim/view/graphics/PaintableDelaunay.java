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
        List<Point> pointList = delaunay.getPointList();
        List<Point> edgeList = delaunay.getEdgeList();

        for(int i=0; i<edgeList.size()-1; i++){
            Point p1 = edgeList.get(i);
            i++;
            Point p2 = edgeList.get(i);

            painter.paintLine(p1, p2);
        }

        // Kontrollpunkte zeichnen
        for (int i = 0; i < pointList.size(); i++) {
            Point p1 = pointList.get(i);
            Point p1_view = transformer.transformToPointOnScreen(p1);

            int k = 4;
            painter.setColor(Color.BLACK);
            painter.paintLine(p1_view.getX() - k, p1_view.getY() - k, p1_view.getX() + k, p1_view.getY() + k);
            painter.paintLine(p1_view.getX() + k, p1_view.getY() - k, p1_view.getX() - k, p1_view.getY() + k);
        }
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
