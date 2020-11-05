package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.PolyLine;
import irmb.flowsim.model.Shape;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

import java.util.List;

/** Created by Sven on 15.12.2016. */
public class PaintablePolyLine extends PaintableShape {

    private final PolyLine polyLine;

    public PaintablePolyLine(PolyLine polyLine) {
        this.polyLine = polyLine;
    }

    @Override
    public void paint(Painter painter) {
        //TODO
    }

    @Override
    public boolean isPointOnBoundary(Point point, double radius) {
        //TODO
        return false;
    }

    @Override
    public Shape getShape() {
        return polyLine;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        List<Point> pointList = polyLine.getPointList();
        for (Point p : pointList) {
            if (getDistance(p, point) <= radius) return p;
        }
        return null;
    }
}
