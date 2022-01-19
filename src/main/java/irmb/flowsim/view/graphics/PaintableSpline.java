package irmb.flowsim.view.graphics;

import java.util.LinkedList;
import java.util.List;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.Spline;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

public class PaintableSpline extends PaintableShape {
    
    private Spline spline;
    private CoordinateTransformer trafo;
    private Point selectedPoint;

    public PaintableSpline(Spline spline, CoordinateTransformer trafo) {
        this.spline = spline;
        this.trafo = trafo;
    }

    
    @Override
    public void paint(Painter painter) {
        painter.setColor(Color.BLACK);

        if (spline.getPointList().size() >= 2) {

            List<Point> pointList = spline.getPointList();

            List<Double> gradientListX = spline.getGradientList(spline.splitPointList(pointList, "x"));
            List<Double> gradientListY = spline.getGradientList(spline.splitPointList(pointList, "y"));

            recursivePaint(painter, pointList, gradientListX, gradientListY);

            // Kontrollpunkte zeichnen
            for (int i = 0; i < pointList.size(); i++) {
                Point p = pointList.get(i);
                Point p_view = trafo.transformToPointOnScreen(p);
                int k = 4;
                painter.paintLine(p_view.getX() - k, p_view.getY() - k, p_view.getX() + k, p_view.getY() + k);
                painter.paintLine(p_view.getX() + k, p_view.getY() - k, p_view.getX() - k, p_view.getY() + k);
            }
        }
    }



    private void recursivePaint(Painter painter, List<Point> pointList, List<Double> gradientListX, List<Double> gradientListY) {
        //TODO
    }



    @Override
    public Shape getShape() {
        return this.spline;
    }


    @Override
    public Point getDefinedPoint(Point point, double radius) {
        return null;
    }
    
    
    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        List<Point> pointList = spline.getPointList();
        // Prüft ob Mausklick (x,y) auf einem Kontrollpunkt des Splines liegt
        for (Point p : pointList) {
            if ((Math.abs(point.getX() - p.getX()) < radius) && (Math.abs(point.getY() - p.getY()) < radius)) {
                this.selectedPoint = p;
                return true;
            }
        }
        this.selectedPoint = null;

        // Prüft ob Mausklick (x,y) auf einer Kante der Bezierkurve liegt
        double distance;

        // Kurve zeichnen
        if (pointList.size() >= 2) {
            for (int i = 0; i < pointList.size() - 1; i++) {

                List<Double> gradientListX = spline.getGradientList(spline.splitPointList(pointList, "x"));
                List<Double> gradientListY = spline.getGradientList(spline.splitPointList(pointList, "y"));

                Point p1 = new Point(spline.getPointOnSpline(pointList, gradientListX, gradientListY, i).get(0),
                        spline.getPointOnSpline(pointList, gradientListX, gradientListY, i).get(1));
                Point p2 = new Point(spline.getPointOnSpline(pointList, gradientListX, gradientListY, i).get(0),
                        spline.getPointOnSpline(pointList, gradientListX, gradientListY, i).get(1));

                double xa = p1.getX();
                double ya = p1.getY();
                double xb = p2.getX();
                double yb = p2.getY();
                double t = 2.0 * point.getX() * xb - 2.0 * point.getX() * xa + 2.0 * point.getY() * yb - 2.0 * point.getY() * ya + ya * ya + xa * xa - yb * yb
                        - xb * xb;
                t /= yb * yb - 2.0 * yb * ya + ya * ya + xb * xb - 2.0 * xb * xa + xa * xa;

                if (Math.abs(t) <= 1.) {
                    double xd = xa * (0.5 - 0.5 * t) + xb * (0.5 + 0.5 * t);
                    double yd = ya * (0.5 - 0.5 * t) + yb * (0.5 + 0.5 * t);

                    distance = Math.sqrt((xd - point.getX()) * (xd - point.getX()) + (yd - point.getY()) * (yd - point.getY()));
                } else // D liegt außerhalb Strecke AB
                {
                    double tmpDistance1 = Math.sqrt((xa - point.getX()) * (xa - point.getX()) + (ya - point.getY()) * (ya - point.getY()));
                    double tmpDistance2 = Math.sqrt((xb - point.getX()) * (xb - point.getX()) + (yb - point.getY()) * (yb - point.getY()));

                    distance = Math.min(tmpDistance1, tmpDistance2);
                }
                if (distance <= radius) {
                    return true;
                }
            }
        }

        return false;
    }

}