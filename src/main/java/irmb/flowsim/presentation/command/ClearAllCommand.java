package irmb.flowsim.presentation.command;

import java.util.ArrayList;
import java.util.List;

import irmb.flowsim.view.graphics.PaintableShape;

/**
 * Created by sven on 20.03.17.
 */
public class ClearAllCommand implements Command {

    private List<PaintableShape> shapeList;
    private List<PaintableShape> backupList = new ArrayList<>();

    public ClearAllCommand(List<PaintableShape> shapeList) {
        this.shapeList = shapeList;
    }

    @Override
    public void execute() {
        backupList.clear();
        shapeList.forEach(paintableShape -> backupList.add(paintableShape));
        shapeList.clear();
    }

    @Override
    public void undo() {
        backupList.forEach(paintableShape -> shapeList.add(paintableShape));
    }

    @Override
    public void redo() {
        execute();
    }
}
