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
        return null;
    }

    private List<Point> getSubPointList(double t, List<Point> tempList) {
        //TODO
        return null;
    }

    private Point getSubPoint(double t, Point first, Point second) {
        //TODO
        return null;
    }


    public Point calculatePointWithBernstein(double t) {
        List<Point> pointList = getPointList();
        int size = pointList.size();
        double x = 0, y = 0;
        for (int i = 0; i < size; i++) {
            double c = binomialCoefficient(size - 1, i) * Math.pow(t, i) * Math.pow(1.0 - t, size - 1.0 - i);
            x += c * pointList.get(i).getX();
            y += c * pointList.get(i).getY();
        }
        return new Point(x, y);
    }

    private double binomialCoefficient(int n, int k) {
        if (n >= k && n >= 0)
            return factorial(n) / (double)(factorial(k) * factorial(n - k));
        return -1;
    }

    private long factorial(int n) {
        long sum = 1;
        for (int i = n; i >= 1; i--)
            sum *= i;
        return sum;
    }
}
