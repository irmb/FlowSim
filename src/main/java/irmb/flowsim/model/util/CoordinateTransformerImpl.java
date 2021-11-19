package irmb.flowsim.model.util;

import Jama.Matrix;
import irmb.flowsim.model.Point;

/** Created by Sven on 10.01.2017. */
public class CoordinateTransformerImpl implements CoordinateTransformer {

    protected Point worldTopLeft;
    protected Point worldBottomRight;
    protected Point viewTopLeft;
    protected Point viewBottomRight;
    private double worldMidX;
    private double worldMidY;
    private double viewMidX;
    private double viewMidY;
    private boolean needsUpdate;
    private Matrix transformationMatrix;
    private Matrix inverse;

    public CoordinateTransformerImpl() {
        needsUpdate = true;
    }

    @Override
    public Point transformToPointOnScreen(Point point) {
        //TODO
        return new Point(0,0);
    }

    private Matrix makePointMatrix(Point point) {
        Matrix pointMatrix = new Matrix(1, 3);
        pointMatrix.set(0, 0, point.getX());
        pointMatrix.set(0, 1, point.getY());
        pointMatrix.set(0, 2, 1);
        return pointMatrix;
    }

    private double getScaleFactor() {
        //TODO
        return 1.0;
    }

    private void makeTransformationMatrix() {
        //TODO
    }

    private Matrix makeScaling(double s) {
        //TODO
        return new Matrix(
                new double[][] {
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1},
                });
    }

    private Matrix makeTranslation(double tx, double ty) {
        //TODO
        return new Matrix(
                new double[][] {
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                });
    }

    private double getDelta(double min, double max) {
        return Math.abs(max - min);
    }

    private double getMiddle(double min, double max) {
        return (max + min) / 2;
    }

    @Override
    public Point transformToWorldPoint(Point point) {
        //TODO
        return new Point(0,0);
    }

    private void calculateMiddleValues() {
        worldMidX = getMiddle(worldTopLeft.getX(), worldBottomRight.getX());
        worldMidY = getMiddle(worldTopLeft.getY(), worldBottomRight.getY());

        viewMidX = getMiddle(viewTopLeft.getX(), viewBottomRight.getX());
        viewMidY = getMiddle(viewTopLeft.getY(), viewBottomRight.getY());
    }

    @Override
    public void setWorldBounds(Point topLeft, Point bottomRight) {
        this.worldTopLeft = topLeft;
        this.worldBottomRight = bottomRight;
        this.needsUpdate = true;
    }

    @Override
    public void setViewBounds(Point topLeft, Point bottomRight) {
        this.viewTopLeft = topLeft;
        this.viewBottomRight = bottomRight;
        this.needsUpdate = true;
    }

    @Override
    public void moveViewWindow(double dx, double dy) {
        viewTopLeft.setX(viewTopLeft.getX() + dx);
        viewTopLeft.setY(viewTopLeft.getY() + dy);
        viewBottomRight.setX(viewBottomRight.getX() + dx);
        viewBottomRight.setY(viewBottomRight.getY() + dy);
        this.needsUpdate = true;
    }

    @Override
    public double scaleToScreenLength(double length) {
        //TODO
        return 1.0;
    }

    @Override
    public double scaleToWorldLength(double length) {
        //TODO
        return 1.0;
    }

    @Override
    public void zoomWindow(double zoomFactor, double worldX, double worldY) {
        double zoom = 1 - zoomFactor;
        calculateMiddleValues();
        double deltaX = worldX * zoomFactor;
        double deltaY = worldY * zoomFactor;

        worldTopLeft.setX(worldTopLeft.getX() * zoom + deltaX);
        worldTopLeft.setY(worldTopLeft.getY() * zoom + deltaY);
        worldBottomRight.setX(worldBottomRight.getX() * zoom + deltaX);
        worldBottomRight.setY(worldBottomRight.getY() * zoom + deltaY);
        this.needsUpdate = true;
    }
}
