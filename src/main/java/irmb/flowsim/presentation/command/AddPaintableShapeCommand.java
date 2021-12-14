package irmb.flowsim.presentation.command;

import irmb.flowsim.view.graphics.PaintableShape;

import java.util.List;

/**
 * Created by Sven on 03.01.2017.
 */
public class AddPaintableShapeCommand implements Command {

    private PaintableShape shape;
    private List<PaintableShape> paintableShapeList;

    public AddPaintableShapeCommand(PaintableShape shape, List<PaintableShape> paintableShapeList) {
        this.shape = shape;
        this.paintableShapeList = paintableShapeList;
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
}
