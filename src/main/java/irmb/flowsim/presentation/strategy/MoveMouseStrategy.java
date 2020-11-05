package irmb.flowsim.presentation.strategy;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.Shape;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.command.MoveShapeCommand;
import irmb.flowsim.presentation.command.RemovePaintableShapeCommand;
import irmb.flowsim.view.graphics.PaintableShape;

import java.util.List;

import static irmb.flowsim.presentation.strategy.StrategyState.UPDATE;

/**
 * Created by Sven on 05.01.2017.
 */
public class MoveMouseStrategy extends MouseStrategy {


    private int radius = 3;
    private MoveShapeCommand moveShapeCommand;
    private Point clickedPoint;
    private List<PaintableShape> shapeList;
    private CoordinateTransformer transformer;


    public MoveMouseStrategy(List<PaintableShape> shapeList, CoordinateTransformer transformer) {
        super(shapeList, transformer);
        this.shapeList = shapeList;
        this.transformer = transformer;
    }

    @Override
    public void onLeftClick(double x, double y) {
        //TODO
    }

    private void prepareShapeMove(PaintableShape paintableShape) {
        //TODO
    }

    private void makeMoveShapeCommand(PaintableShape paintableShape, Point definedPoint) {
        //TODO
    }

    @Override
    public void onRightClick(double x, double y) {
        //TODO
    }

    private void deleteShape(PaintableShape shape) {
        //TODO
    }

    private void notifyWithRemoveCommand(RemovePaintableShapeCommand command) {
        StrategyEventArgs args = new StrategyEventArgs(UPDATE);
        args.setCommand(command);
        notifyObservers(args);
    }

    private RemovePaintableShapeCommand makeRemoveCommand(PaintableShape shape) {
        return new RemovePaintableShapeCommand(shapeList, shape);
    }

    @Override
    public void onMouseMove(double x, double y) {
    }

    @Override
    public void onMouseDrag(double x, double y) {
        super.onMouseDrag(x, y);
        //TODO
    }

    private void moveShape(double x, double y) {
        //TODO
    }

    @Override
    public void onMouseRelease() {
        super.onMouseRelease();
        //TODO
    }

    private PaintableShape getPaintableShapeAt(double x, double y) {
        Point worldPoint = getWorldPoint(new Point(x, y));
        double tolerance = transformer.scaleToWorldLength(radius);
        for (PaintableShape p : shapeList)
            if (p.isPointOnBoundary(worldPoint, tolerance))
                return p;
        return null;
    }

    private double getWorldLength(double radius) {
        return transformer.scaleToWorldLength(radius);
    }

    private Point getWorldPoint(Point clickedPoint) {
        return transformer.transformToWorldPoint(clickedPoint);
    }

    public void setToleranceRadius(int radius) {
        this.radius = radius;
    }
}
