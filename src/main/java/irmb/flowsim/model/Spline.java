package irmb.flowsim.model;

import java.util.List;

import Jama.Matrix;

public class Spline extends PolyLine {

    private Matrix x;
    

    public Point getPointOnSpline(double t) {
        //stelle in bezug zur gesamtlaenge n
        double offset = t * (getPointList().size() - 1);
        /* in welchem spline sind wir gerade? */
        int num = (int) (Math.floor(offset));
        //wie ist der versatz auf den aktuellen spline bezogen?
        double t_new = offset - num;
        //abfangen falls er bei num = n+1 landet...
        //allerletzter punkt gehört spline n...
        if (num == getPointList().size() - 1) {
            num--;
            t_new = 1.0;
        }

        double px = 0.0;
        double py = 0.0;

        for (int i = 0; i < 4; i++) {
            px += (x.get(num * 8 + 2 * i, 0) * Math.pow(t_new, i));
            py += (x.get(num * 8 + 2 * i + 1, 0) * Math.pow(t_new, i));
        }
        return new Point(px, py);
    }


    private void calculateCoefficients() {

        if (getPointList().size() > 2) {
            int numOfSplines = getPointList().size() - 1;
            int numOfCoeff = numOfSplines * 8;

            Matrix A = new Matrix(numOfCoeff, numOfCoeff);
            Matrix b = new Matrix(numOfCoeff, 1); // rechte Seite

            /* schleife fuer jeden spline */
            for (int i = 0; i < numOfSplines; i++) {
                int offset = 8 * i;

                /* erste gleichung - anfangspunkte */
                A.set(offset, offset, 1.0);
                A.set(offset + 1, offset + 1, 1.0);
                b.set(offset, 0, getPointList().get(i).getX());
                b.set(offset + 1, 0, getPointList().get(i).getY());

                /* zweite gleichung - endpunkte */
                for (int j = 0; j < 4; j++) {
                    A.set(offset + 2, offset + 2 * j, 1.0);
                    A.set(offset + 3, offset + 1 + 2 * j, 1.0);
                }
                b.set(offset + 2, 0, getPointList().get(i + 1).getX());
                b.set(offset + 3, 0, getPointList().get(i + 1).getY());

                /* dritte gleichung - stetigkeit 1. abl */
                if (i < numOfSplines - 1) {

                    for (int j = 1; j < 4; j++) {
                        A.set(offset + 4, offset + 2 * j, (double) j);
                        A.set(offset + 5, offset + 1 + 2 * j, (double) j);
                    }

                    b.set(offset + 4, 0, 0.0);
                    b.set(offset + 5, 0, 0.0);

                    A.set(offset + 4, offset + 10, -1.0);
                    A.set(offset + 5, offset + 11, -1.0);
                }

                if (i == numOfSplines - 1) {
                    A.set(offset + 4, 4, 2.0);
                    A.set(offset + 5, 5, 2.0);

                    b.set(offset + 4, 0, 0.0);
                    b.set(offset + 5, 0, 0.0);
                }


                /* vierte gleichung - stetigkeit 2. abl */
                A.set(offset + 6, offset + 4, 1.0);
                A.set(offset + 6, offset + 6, 3.0);
                A.set(offset + 7, offset + 5, 1.0);
                A.set(offset + 7, offset + 7, 3.0);

                b.set(offset + 6, 0, 0.0);
                b.set(offset + 7, 0, 0.0);

                if (i < numOfSplines - 1) {
                    A.set(offset + 6, offset + 12, -1.0);
                    A.set(offset + 7, offset + 13, -1.0);
                }
            }
            x = A.solve(b);
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

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
