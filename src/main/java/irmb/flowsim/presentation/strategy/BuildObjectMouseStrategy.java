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
        // TODO
    }

    @Override
    public void onRightClick(double x, double y) {
        // TODO
    }

    @Override
    public void onMouseMove(double x, double y) {
        // TODO
    }
}
