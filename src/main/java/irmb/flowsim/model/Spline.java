package irmb.flowsim.model;

import java.util.LinkedList;
import java.util.List;

public class Spline extends PolyLine {


    public List<Double> getPointOnSpline(List<Point> points, List<Double> gradientsX, List<Double> gradientsY, int i) {
        //TODO
        return null;
    }
    
    public List<Double> getGradientList(List<Double> values) {
        //TODO
        return null;
    }


    public List<Double> getListsforCalculation(List<Double> coordList, boolean partOfSolution) {
        //TODO
        return null;
    }


    public List<Double> getSplineValues(double beginningCoord, double endingCoord, double beginningGradient,double endingGradient) {
        //TODO
        return null;
    }


    public List<Double> splitPointList(List<Point> points, String coord) {

        List<Double> values = new LinkedList<Double>();

        if (coord == "x") {
            for (int i = 0; i <= points.size() - 1; i++) {
                values.add(points.get(i).getX());
            }
        } else if (coord == "y") {
            for (int i = 0; i <= points.size() - 1; i++) {
                values.add(points.get(i).getY());
            }
        }

        return values;
    }
}
