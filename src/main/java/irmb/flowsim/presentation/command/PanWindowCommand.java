package irmb.flowsim.presentation.command;

import irmb.flowsim.model.util.CoordinateTransformer;

/**
 * Created by sven on 24.01.17.
 */
public class PanWindowCommand implements Command {

    private CoordinateTransformer transformer;
    private double dx;
    private double dy;
    private double totalDx;
    private double totalDy;
    private boolean calledExecute;

    public PanWindowCommand(CoordinateTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void execute() {
        // TODO
    }

    @Override
    public void undo() {
        // TODO
    }

    @Override
    public void redo() {
        // TODO
    }

    public void setDelta(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
