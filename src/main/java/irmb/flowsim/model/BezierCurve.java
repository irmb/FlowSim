package irmb.flowsim.model;

import irmb.flowsim.presentation.Painter;

import java.util.ArrayList;
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
        List<Point> subList;
        subList = new ArrayList<>();
        for (int i = 0; i < tempList.size() - 1; i++) {
            Point first = tempList.get(i + 1);
            Point second = tempList.get(i);
            Point subPoint = getSubPoint(t, first, second);
            subList.add(i, subPoint);
        }
        return subList;
    }

    private Point getSubPoint(double t, Point first, Point second) {
        double dx = first.getX() - second.getX();
        double dy = first.getY() - second.getY();
        return new Point((second.getX() + t * dx), (second.getY() + t * dy));
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
