package irmb.flowsim.model;

import java.util.LinkedList;
import java.util.List;

public class Spline extends PolyLine {


    public Spline() {

    }

    
    public List<Double> getGradientList(List<Double> values) {
        List<Double> differences = new LinkedList<Double>();
        List<Double> homogen = new LinkedList<Double>();
        List<Double> gradientPartList, gradientHomoList;
        List<Double> gradientList = new LinkedList<Double>();

        for (int i = 0; i <= getPointList().size() - 2; i++) {
            differences.add(values.get(i + 1) - values.get(i));
            homogen.add(0.0);
        }

        gradientPartList = getListsforCalculation(differences, false);
        gradientHomoList = getListsforCalculation(homogen, true);

        double cL = -gradientPartList.get(gradientPartList.size() - 1)
                / gradientHomoList.get(gradientHomoList.size() - 1);

        for (int i = 0; i <= getPointList().size() - 1; i++) {
            gradientList.add(gradientPartList.get(i) + cL * gradientHomoList.get(i));
        }

        return gradientList;
    }


    public List<Double> getPointOnSpline(List<Point> points, List<Double> gradientsX, List<Double> gradientsY, int i) {
        List<Double> splineValues = new LinkedList<Double>();
        double pX, pY, gradientX, gradientY;

        pX = this.getSplineValues(points.get(i).getX(), points.get(i + 1).getX(), gradientsX.get(i),
                gradientsX.get(i + 1)).get(0);
        gradientX = this.getSplineValues(points.get(i).getX(), points.get(i + 1).getX(), gradientsX.get(i),
                gradientsX.get(i + 1)).get(1);

        pY = this.getSplineValues(points.get(i).getY(), points.get(i + 1).getY(), gradientsY.get(i),
                gradientsY.get(i + 1)).get(0);
        gradientY = this.getSplineValues(points.get(i).getY(), points.get(i + 1).getY(), gradientsY.get(i),
                gradientsY.get(i + 1)).get(1);

        splineValues.add(pX);
        splineValues.add(pY);
        splineValues.add(gradientX);
        splineValues.add(gradientY);

        return splineValues;
    }


    public List<Double> getListsforCalculation(List<Double> coordList, boolean partOfSolution) {
        double g, c, gNew, cNew;
        List<Double> gradientList = new LinkedList<Double>();

        if (partOfSolution) {
            g = 1.;
            c = 0.;
        } else {
            g = 0.;
            c = 0.;
        }

        for (int i = 0; i <= coordList.size() - 1; i++) {
            gradientList.add(g);
            gNew = 3 * coordList.get(i) - 2 * g - 0.5 * c;
            cNew = 6 * (coordList.get(i) - g) - 2 * c;
            g = gNew;
            c = cNew;
        }
        gradientList.add(g);
        gradientList.add(c);
        return gradientList;
    }


    public List<Double> getSplineValues(double beginningCoord, double endingCoord, double beginningGradient,double endingGradient) {
        List<Double> splineValues = new LinkedList<Double>();

        double coord, g;

        coord = 0.5 * beginningCoord + 0.125 * beginningGradient + 0.5 * endingCoord - 0.125 * endingGradient;
        g = -1.5 * beginningCoord - 0.25 * beginningGradient + 1.5 * endingCoord - 0.25 * endingGradient;

        splineValues.add(coord);
        splineValues.add(g);

        return splineValues;
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
