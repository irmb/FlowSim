package irmb.flowsim.presentation.command;

import irmb.flowsim.model.Shape;

/**
 * Created by Sven on 02.01.2017.
 */
public class MoveShapeCommand implements Command {

    private final Shape shape;
    private double dx;
    private double dy;
    private boolean calledExecute;
    private double totalDx;
    private double totalDy;

    public MoveShapeCommand(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void execute() {
        //TODO
    }

    @Override
    public void undo() {
        //TODO
    }

    @Override
    public void redo() {
        //TODO
    }

    public void setDelta(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
