package irmb.flowsim.simulation;

import irmb.flowsim.model.*;
import irmb.flowsim.view.graphics.PaintableShape;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sven on 16.03.17.
 */
public class GridMapper implements ShapeVisitor {

    private UniformGrid grid;

    public GridMapper(UniformGrid grid) {
        this.grid = grid;
    }

    public void mapShapes(List<PaintableShape> shapes) {
        grid.resetSolidNodes();
        shapes.forEach(paintableShape -> {
            Shape shape = paintableShape.getShape();
            shape.accept(this);
        });
    }

    @Override
    public void visit(Line line) {
        //TODO
    }

    @Override
    public void visit(Circle circle) {
        //TODO
    }

    @Override
    public void visit(Triangle triangle) {
        //TODO
    }

    private void mapLineSegment(Point first, Point second) {
        //TODO
    }

    @Override
    public void visit(Rectangle rectangle) {
        //TODO
    }

    @Override
    public void visit(PolyLine polyLine) {
        //TODO
    }

    private void mapPolyLineToGrid(List<Point> pointList) {
        //TODO
    }

    @Override
    public void visit(Point point) {
    }

    @Override
    public void visit(BezierCurve bezierCurve) {
        //TODO
    }

    @Override
    public void visit(Spline spline) {

    }

    private void bresenham(int xStart, int yStart, int xEnd, int yEnd) {
        //TODO
    }
}
