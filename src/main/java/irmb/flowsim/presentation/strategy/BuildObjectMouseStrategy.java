package irmb.flowsim.presentation.strategy;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.builder.PaintableShapeBuilder;
import irmb.flowsim.presentation.command.AddPaintableShapeCommand;
import irmb.flowsim.view.graphics.PaintableShape;

import java.util.List;

import static irmb.flowsim.presentation.strategy.StrategyState.FINISHED;
import static irmb.flowsim.presentation.strategy.StrategyState.UPDATE;

/** Created by Sven on 05.01.2017. */
public class BuildObjectMouseStrategy extends MouseStrategy {

    private final PaintableShapeBuilder shapeBuilder;
    private AddPaintableShapeCommand addPaintableShapeCommand;
    private boolean needsNextPointOnMove;
    private boolean shapeAdded;
    private int pointsAdded = 0;

    public BuildObjectMouseStrategy(
            List<PaintableShape> shapeList,
            CoordinateTransformer transformer,
            PaintableShapeBuilder builder) {
        super(shapeList, transformer);
        this.shapeBuilder = builder;
    }

    @Override
    public void onLeftClick(double x, double y) {
        Point point = getWorldPoint(x, y);
        if (firstPoint()) addPointToShape(point);
        needsNextPointOnMove = true;
        notifyObserverWithMatchingArgs();
    }

    private boolean firstPoint() {
        return pointsAdded == 0;
    }

    private void addPointToShape(Point point) {
        shapeBuilder.addPoint(point);
        pointsAdded++;
    }

    private void notifyObserverWithMatchingArgs() {
        StrategyState state = shapeBuilder.isObjectFinished() ? FINISHED : UPDATE;
        StrategyEventArgs args = makeStrategyEventArgs(state);
        if (state == FINISHED) args.setCommand(addPaintableShapeCommand);
        notifyObservers(args);
    }

    @Override
    public void onRightClick(double x, double y) {
        StrategyEventArgs args = makeStrategyEventArgs(FINISHED);
        if (shapeBuilder.isObjectPaintable())
            if (shapeBuilder.isInfinite()) finishObject(args);
            else undoAddShape();
        notifyObservers(args);
    }

    private void undoAddShape() {
        addPaintableShapeCommand.undo();
    }

    private void finishObject(StrategyEventArgs args) {
        shapeBuilder.removeLastPoint();
        args.setCommand(addPaintableShapeCommand);
    }

    @Override
    public void onMouseMove(double x, double y) {
        adjustShapeOnMouseMove(x, y);
        if (shapeBuilder.isObjectPaintable()) notifyObservers(makeStrategyEventArgs(UPDATE));
    }

    private void adjustShapeOnMouseMove(double x, double y) {
        Point point = getWorldPoint(x, y);
        if (needsNextPointOnMove) {
            addPointToShape(point);
            needsNextPointOnMove = false;
        }

        if (shapeReadyForPainting()) addShapeToList();
        shapeBuilder.setLastPoint(point);
    }

    private boolean shapeReadyForPainting() {
        return !shapeAdded && shapeBuilder.isObjectPaintable();
    }

    private void addShapeToList() {
        shapeAdded = true;
        addPaintableShapeCommand = new AddPaintableShapeCommand(shapeBuilder.getShape(), shapeList);
        addPaintableShapeCommand.execute();
    }

    private Point getWorldPoint(double x, double y) {
        return transformer.transformToWorldPoint(new Point(x, y));
    }

    private StrategyEventArgs makeStrategyEventArgs(StrategyState state) {
        return new StrategyEventArgs(state);
    }
}
