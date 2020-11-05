package irmb.flowsim.presentation.command;

import irmb.flowsim.view.graphics.PaintableShape;

import java.util.List;

/**
 * Created by sven on 16.02.17.
 */
public class RemovePaintableShapeCommand implements Command {

    private final List<PaintableShape> shapeList;
    private final PaintableShape paintableShape;

    public RemovePaintableShapeCommand(List<PaintableShape> shapeList, PaintableShape paintableShape) {
        this.shapeList = shapeList;
        this.paintableShape = paintableShape;
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
}
