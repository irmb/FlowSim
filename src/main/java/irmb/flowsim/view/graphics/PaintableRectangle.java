package irmb.flowsim.view.graphics;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Rectangle;
import irmb.flowsim.model.Shape;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

/**
 * Created by Sven on 14.12.2016.
 */
public class PaintableRectangle extends PaintableShape {

    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    private final Rectangle rectangle;


    public PaintableRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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
        return rectangle;
    }

    @Override
    public Point getDefinedPoint(Point point, double radius) {
        //TODO
        return new Point(0,0);
    }

    private boolean isInside(Point point, double radius) {
        //TODO
        return false;
    }

    private boolean isInBoundingBox(Point point, double radius) {
        //TODO
        return false;
    }

    private boolean isInYBounds(Point point, double radius) {
        //TODO
        return false;
    }

    private boolean isInXBounds(Point point, double radius) {
        //TODO
        return false;
    }
}
