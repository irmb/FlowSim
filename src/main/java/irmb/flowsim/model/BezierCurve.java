package irmb.flowsim.model;

import irmb.flowsim.presentation.Painter;

import java.util.Collections;
import java.util.List;

/**
 * Created by sven on 19.03.17.
 */
public class BezierCurve extends PolyLine {

    public List<Point> calculateCasteljau(List<Point> pointList, double t) {
        //TODO
        return Collections.emptyList();
    }

    private List<Point> getSubPointList(double t, List<Point> tempList) {
        // TODO
        return Collections.emptyList();
    }

    private Point getSubPoint(double t, Point first, Point second) {
        // TODO
        return new Point(0,0);
    }

    public Point calculatePointWithBernstein(double t) {
        //TODO
        return new Point(0,0);
    }

    private double binomialCoefficient(int n, int k) {
        if (n >= k && n >= 0)
            return factorial(n) / (factorial(k) * factorial(n - k));
        return -1;
    }

    private long factorial(int n) {
        long sum = 1;
        for (int i = n; i >= 1; i--)
            sum *= i;
        return sum;
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
