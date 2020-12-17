package irmb.flowsim.model;

import irmb.flowsim.presentation.Painter;

import java.util.Collections;
import java.util.List;

/**
 * Created by sven on 19.03.17.
 */
public class BezierCurve extends PolyLine {


    public Point calculatePointWithBernstein(double t) {
        //TODO
        return new Point(0,0);
    }

    private double binomialCoefficient(int n, int k) {
        //TODO
    }

    private long factorial(int n) {
        //TODO
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
