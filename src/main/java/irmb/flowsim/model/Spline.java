package irmb.flowsim.model;

import java.util.List;

import Jama.Matrix;

public class Spline extends PolyLine {

    private Matrix x;


    public Point getPointOnSpline(double t) {
        //stelle in bezug zur gesamtlaenge n
        
        /* in welchem spline sind wir gerade? */
     
        //wie ist der versatz auf den aktuellen spline bezogen?
      
        //abfangen falls er bei num = n+1 landet...
        //allerletzter punkt gehört spline n...

        return new Point(0, 0);
    }


    private void calculateCoefficients() {

        if (getPointList().size() > 2) {
            int numOfSplines = getPointList().size() - 1;
            int numOfCoeff = numOfSplines * 8;

            Matrix A = new Matrix(numOfCoeff, numOfCoeff);
            Matrix b = new Matrix(numOfCoeff, 1); // rechte Seite

            /* schleife fuer jeden spline */
            

                /* erste gleichung - anfangspunkte */
                

                /* zweite gleichung - endpunkte */
            

                /* dritte gleichung - stetigkeit 1. abl */


                /* vierte gleichung - stetigkeit 2. abl */
        }
    }

    @Override
    public void addPoint(Point point) {
        super.addPoint(point);
        calculateCoefficients();
    }

    @Override
    public void removeLastPoint() {
        super.removeLastPoint();
        calculateCoefficients();
    }

    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        calculateCoefficients();
    }

    @Override
    public void setLastPoint(Point point) {
        super.setLastPoint(point);
        calculateCoefficients();
    }
}
