package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;

/**
 * Created by sven on 23.03.17.
 */
public class IsolineGridNodeStyle extends GridNodeStyle {

    private final ColorFactory colorFactory;
    private boolean firstRun = true;
    private double min;
    private double max;

    public IsolineGridNodeStyle(ColorFactory colorFactory, CoordinateTransformer transformer) {
        super(-1, transformer);
        this.colorFactory = colorFactory;
    }

    @Override
    public void paintGridNode(Painter painter) {

        getInitialMinMax();
        double currentMin = min;
        double currentMax = max;

        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;

        double deltaH = Math.abs(currentMin - currentMax) / 20.0;
        double h0 = currentMin;

        int size = grid.getHorizontalNodes() * grid.getVerticalNodes();
        int x, y;
        for (int i = 0; i < size; i++) {
            x = i % grid.getHorizontalNodes();
            y = i / grid.getHorizontalNodes();

            if (x == grid.getHorizontalNodes() - 1 || y == grid.getVerticalNodes() - 1)
                continue;

            if(grid.isSolid(x,y))
                continue;

            adjustMinMax(x, y);

            double a = grid.getVelocityAt(x, y);
            double b = grid.getVelocityAt(x + 1, y);
            double c = grid.getVelocityAt(x + 1, y + 1);
            double d = grid.getVelocityAt(x, y + 1);


            double ax = grid.getWorldCoordinatesX(x);
            double ay = grid.getWorldCoordinatesY(y);

            double bx = grid.getWorldCoordinatesX(x + 1);
            double by = grid.getWorldCoordinatesY(y);

            double cx = grid.getWorldCoordinatesX(x + 1);
            double cy = grid.getWorldCoordinatesY(y + 1);

            double dx = grid.getWorldCoordinatesX(x);
            double dy = grid.getWorldCoordinatesY(y + 1);

            //erstes Dreieck in dieser Zelle ACD
            double wmin = Math.min(Math.min(a, c), d);
            double wmax = Math.max(Math.max(a, c), d);

            //Schritt 1
            if (wmin != wmax) {
                int nmin = 0, nmax = 0;
                //Schritt 2
                if (wmin > h0) {
                    nmin = (int) ((wmin - h0) / deltaH + 1);
                }
                if (wmin <= h0) {
                    nmin = (int) ((-wmin + h0) / deltaH);
                }
                if (wmax >= h0) {
                    nmax = (int) Math.ceil((wmax - h0) / deltaH);
                }
                if (wmax < h0) {
                    nmax = (int) (Math.ceil((wmax - h0) / deltaH - 1));
                }
                //---
                if (nmin < nmax) {
                    //Schritt 3
                    for (int n = nmin; n < nmax; n++) {
                        //Schritt 4
                        double hn = h0 + n * deltaH;//iso

                        //Schritt 5
                        double t1 = (hn - c) / (d - c);
                        double t2 = (hn - d) / (a - d);
                        double t3 = (hn - a) / (c - a);

                        //Schritt 6
                        Point p1 = null;
                        Point p2 = null;

                        if (t2 > 0 && t2 < 1) {
                            double sx = dx + t2 * (ax - dx);
                            double sy = dy + t2 * (ay - dy);
                            p1 = new Point(sx, sy);
                        }
                        if (t1 > 0 && t1 < 1) {
                            double sx = cx + t1 * (dx - cx);
                            double sy = cy + t1 * (dy - cy);
                            if (p1 == null) {
                                p1 = new Point(sx, sy);
                            } else {
                                p2 = new Point(sx, sy);
                            }
                        }
                        if (t3 > 0 && t3 < 1) {
                            double sx = ax + t3 * (cx - ax);
                            double sy = ay + t3 * (cy - ay);
                            p2 = new Point(sx, sy);
                        }

                        if (p1 != null && p2 != null) {
                            painter.setColor(colorFactory.makeColorForValue(currentMin, currentMax, hn));

                            painter.paintLine(p1, p2);
                        }
                    }
                }
            }
            //zweites Dreieck in dieser Zelle ABC
            wmin = Math.min(Math.min(a, b), c);
            wmax = Math.max(Math.max(a, b), c);

            //Schritt 1
            if (wmin != wmax) {
                int nmin = 0, nmax = 0;
                //Schritt 2
                if (wmin > h0) {
                    nmin = (int) ((wmin - h0) / deltaH + 1);
                }
                if (wmin <= h0) {
                    nmin = (int) ((-wmin + h0) / deltaH);
                }
                if (wmax >= h0) {
                    nmax = (int) Math.ceil((wmax - h0) / deltaH);
                }
                if (wmax < h0) {
                    nmax = (int) (Math.ceil((wmax - h0) / deltaH - 1));
                }
                //---
                if (nmin < nmax) {
                    //Schritt 3
                    for (int n = nmin; n < nmax; n++) {
                        //Schritt 4
                        double hn = h0 + n * deltaH;//iso

                        //Schritt 5
                        double t1 = (hn - b) / (c - b);
                        double t2 = (hn - c) / (a - c);
                        double t3 = (hn - a) / (b - a);

                        //Schritt 6
                        Point p1 = null;
                        Point p2 = null;

                        if (t3 > 0 && t3 < 1) {
                            double sx = ax + t3 * (bx - ax);
                            double sy = ay + t3 * (by - ay);
                            p1 = new Point(sx, sy);
                        }
                        if (t1 > 0 && t1 < 1) {
                            double sx = bx + t1 * (cx - bx);
                            double sy = by + t1 * (cy - by);
                            if (p1 == null) {
                                p1 = new Point(sx, sy);
                            } else {
                                p2 = new Point(sx, sy);
                            }
                        }
                        if (t2 > 0 && t2 < 1) {
                            double sx = cx + t2 * (ax - cx);
                            double sy = cy + t2 * (ay - cy);
                            p2 = new Point(sx, sy);
                        }

                        if (p1 != null && p2 != null) {
                            painter.setColor(colorFactory.makeColorForValue(currentMin, currentMax, hn));

                            painter.paintLine(p1, p2);
                        }
                    }
                }
            }
        }
    }

    private void getInitialMinMax() {
        if (firstRun) {
            min = grid.getVelocityAt(0, 0);
            max = grid.getVelocityAt(0, 0);
            for (int y = 0; y < grid.getVerticalNodes(); y++)
                for (int x = 0; x < grid.getHorizontalNodes(); x++)
                    adjustMinMax(x, y);
            firstRun = false;
        }
    }

    private void adjustMinMax(int x, int y) {
        double v = grid.getVelocityAt(x, y);
        if (v < min)
            min = v;
        if (v > max)
            max = v;
    }

}