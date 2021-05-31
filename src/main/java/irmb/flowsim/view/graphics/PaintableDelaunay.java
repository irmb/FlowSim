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

            Point p1_view = transformer.transformToPointOnScreen(p1);
            Point p2_view = transformer.transformToPointOnScreen(p2);

            // draw line
            painter.paintLine(p1_view, p2_view);
        }

//        int numOfPoints = 100;

//        // Spline zeichnen
//        if (pointList.size() > 2) {
//            for (int i = 0; i < numOfPoints - 1; i++) {
//                g.setColor(color);
//
//                double t1 = (i) / (double) (numOfPoints - 1);
//                double t2 = (i + 1.0) / (double) (numOfPoints - 1);
//
//                Point2D p1 = delaunay.getPointOnSpline(t1);
//                Point2D p2 = delaunay.getPointOnSpline(t2);
//
//                int p1x_view = (int) trafo.transformWorldToViewXCoord(p1.getX(), p1.getY(), true);
//                int p1y_view = (int) trafo.transformWorldToViewYCoord(p1.getX(), p1.getY(), true);
//                int p2x_view = (int) trafo.transformWorldToViewXCoord(p2.getX(), p2.getY(), true);
//                int p2y_view = (int) trafo.transformWorldToViewYCoord(p2.getX(), p2.getY(), true);
//
//                // draw line
//                g.drawLine(p1x_view, p1y_view, p2x_view, p2y_view);
//            }
//        }


        // Kontrollpunkte zeichnen
        for (int i = 0; i < pointList.size(); i++) {
            Point p1 = pointList.get(i);
            Point p1_view = transformer.transformToPointOnScreen(p1);

            // draw point
//            int r = 2;
//            g.setColor(Color.BLACK);
//            g.fillOval(p1x_view - r, p1y_view - r, 2 * r, 2 * r);

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
