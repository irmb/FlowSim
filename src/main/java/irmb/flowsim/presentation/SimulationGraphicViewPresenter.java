package irmb.flowsim.presentation;

import irmb.flowsim.view.graphics.Paintable;
import irmb.flowsim.view.graphics.PaintableShape;

import java.lang.ref.WeakReference;
import java.util.*;

/** Created by sven on 03.03.17. */
public class SimulationGraphicViewPresenter extends GraphicViewPresenter {

    private ArrayList<Paintable> paintables = new ArrayList<>();
    private boolean needsUpdate = true;
    private boolean running;

    public SimulationGraphicViewPresenter(
            CommandStack commandStack,
            List<PaintableShape> shapeList) {
        super(commandStack, shapeList);
    }


    protected void attachObserverToCommandStack() {
        commandStack.addObserver(
                args -> {
                    updateGraphicViewAndSimulation();
                });
    }

    private void updateGraphicViewAndSimulation() {
        needsUpdate = true;
        graphicView.update();
    }


    @Override
    public Iterator<? extends Paintable> getPaintableList() {
        if (!needsUpdate) return paintables.iterator();
        paintables = new ArrayList<>(shapeList);

        return paintables.iterator();
    }

}
