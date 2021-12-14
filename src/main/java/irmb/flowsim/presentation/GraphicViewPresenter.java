package irmb.flowsim.presentation;

import irmb.flowsim.view.graphics.Paintable;
import irmb.flowsim.view.graphics.PaintableShape;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Sven on 13.12.2016.
 */
public class GraphicViewPresenter {

    protected GraphicView graphicView;

    protected List<PaintableShape> shapeList;
    protected CommandStack commandStack;

    public GraphicViewPresenter(CommandStack commandStack, List<PaintableShape> shapeList) {
        this.shapeList = shapeList;
        this.commandStack = commandStack;
        attachObserverToCommandStack();
    }

    protected void attachObserverToCommandStack() {
        this.commandStack.addObserver((args) -> graphicView.update());
    }

    public void setGraphicView(GraphicView graphicView) {
        this.graphicView = graphicView;
    }

    public Iterator<? extends Paintable> getPaintableList() {
        return shapeList.iterator();
    }

}
