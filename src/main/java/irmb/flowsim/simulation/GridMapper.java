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
        Point first = line.getFirst();
        Point second = line.getSecond();
        if (!grid.isPointInside(first) || !grid.isPointInside(second))
            return;
        mapLineSegment(first, second);
    }

    @Override
    public void visit(Circle circle) {
        Point center = circle.getCenter();
        double radius = circle.getRadius();
        int numPoints = 100 ;
        int stepSize = 360 / numPoints;
        LinkedList<Point> points = new LinkedList<>();
        for (int angle = 0; angle < 360; angle += stepSize) {
            double x = radius * Math.cos(angle * Math.PI / 180);
            double y = radius * Math.sin(angle * Math.PI / 180);
            points.add(new Point(center.getX() + x, center.getY() + y));
        }
        mapPolyLineToGrid(points);

        int gridX = grid.getGridCoordinatesX(center.getX());
        int gridY = grid.getGridCoordinatesY(center.getY());

        floodfill(gridX, gridY);
    }

    @Override
    public void visit(Triangle triangle) {
        mapLineSegment(triangle.getFirstPoint(), triangle.getSecondPoint());
        mapLineSegment(triangle.getSecondPoint(), triangle.getThirdPoint());
        mapLineSegment(triangle.getThirdPoint(), triangle.getFirstPoint());
    }

    private void mapLineSegment(Point first, Point second) {
        double startX = (first.getX() - grid.getTopLeft().getX()) / grid.getDelta();
        double startY = (grid.getTopLeft().getY() - first.getY()) / grid.getDelta();
        double endX = (second.getX() - grid.getTopLeft().getX()) / grid.getDelta();
        double endY = (grid.getTopLeft().getY() - second.getY()) / grid.getDelta();
        int x0 = (int) Math.floor(startX);
        int y0 = (int) Math.floor(startY);
        int x1 = (int) Math.floor(endX);
        int y1 = (int) Math.floor(endY);
        bresenham(x0, y0, x1, y1);
    }

    @Override
    public void visit(Rectangle rectangle) {
        Point first = rectangle.getFirst();
        Point second = rectangle.getSecond();
        if (!grid.isPointInside(first) || !grid.isPointInside(second))
            return;
        double minX, maxX, minY, maxY;
        int minGridX, maxGridX, minGridY, maxGridY;
        minX = Math.min(first.getX(), second.getX());
        maxX = Math.max(first.getX(), second.getX());
        minY = Math.min(first.getY(), second.getY());
        maxY = Math.max(first.getY(), second.getY());

        minGridX = (int) Math.round((minX - grid.getTopLeft().getX()) / grid.getDelta());
        maxGridX = (int) Math.round((maxX - grid.getTopLeft().getX()) / grid.getDelta());
        maxGridY = (int) Math.round((grid.getTopLeft().getY() - minY) / grid.getDelta());
        minGridY = (int) Math.round((grid.getTopLeft().getY() - maxY) / grid.getDelta());

        for (int x = minGridX; x <= maxGridX; x++) {
            grid.setSolid(x, minGridY);
            grid.setSolid(x, maxGridY);
        }
        for (int y = minGridY; y <= maxGridY; y++) {
            grid.setSolid(minGridX, y);
            grid.setSolid(maxGridX, y);
        }
    }

    @Override
    public void visit(PolyLine polyLine) {
        List<Point> pointList = polyLine.getPointList();
        for (Point p : pointList)
            if (!grid.isPointInside(p))
                return;
        mapPolyLineToGrid(pointList);
    }

    private void mapPolyLineToGrid(List<Point> pointList) {
        for (int i = 0; i < pointList.size() - 1; i++) {
            Point first = pointList.get(i);
            Point second = pointList.get(i + 1);
            mapLineSegment(first, second);
        }
    }

    @Override
    public void visit(Point point) {
    }

    @Override
    public void visit(BezierCurve bezierCurve) {
        for (Point p : bezierCurve.getPointList()) {
            if (!grid.isPointInside(p))
                return;
        }
        for (int i = 0; i < 100 - 1; i++) {
            double t1 = i / 100.;
            double t2 = (i + 1) / 100.;

            Point first = bezierCurve.calculatePointWithBernstein(t1);
            Point second = bezierCurve.calculatePointWithBernstein(t2);
            mapLineSegment(first, second);
        }
    }

    @Override
    public void visit(Spline spline) {

    }

    private void bresenham(int xStart, int yStart, int xEnd, int yEnd) {
        int x, y, t, dx, dy, incx, incy, pdx, pdy, ddx, ddy, es, el, err;

        dx = xEnd - xStart;
        dy = yEnd - yStart;

        incx = dx < 0 ? -1 : 1;
        incy = dy < 0 ? -1 : 1;
        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        if (dx > dy) {
            pdx = incx;
            pdy = 0;
            ddx = incx;
            ddy = incy;
            es = dy;
            el = dx;
        } else {
            pdx = 0;
            pdy = incy;
            ddx = incx;
            ddy = incy;
            es = dx;
            el = dy;
        }

        x = xStart;
        y = yStart;
        err = el / 2;
        grid.setSolid(x, y);
        for (t = 0; t < el; ++t) {
            err -= es;
            if (err < 0) {
                err += el;
                x += ddx;
                y += ddy;
            } else {
                x += pdx;
                y += pdy;
            }
            grid.setSolid(x, y);
        }
    }


    private void floodfill(int x, int y) {
        if (!grid.isSolid(x, y)) {

            grid.setSolid(x, y);
            
            floodfill(x, y + 1); // unten
            floodfill(x - 1, y); // links
            floodfill(x, y - 1); // oben
            floodfill(x + 1, y); // rechts
        }
    }

    @Override
    public void visit(Delaunay delaunay) {
       
    }
}
