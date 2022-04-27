package irmb.flowsim.simulation;

import irmb.flowsim.presentation.Painter;
import irmb.flowsim.simulation.visualization.GridNodeStyle;
import irmb.flowsim.util.Observer;
import irmb.flowsim.view.graphics.PaintableShape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Created by sven on 04.03.17.
 */
public class LBMChannelFlowSimulation extends Simulation implements Observer<String> {
    private final UniformGrid grid;
    private final LBMSolver solver;
    private GridMapper gridMapper;

    private final List<GridNodeStyle> styleList = new ArrayList<>();

    public LBMChannelFlowSimulation(UniformGrid grid, LBMSolver solver) {
        this.grid = grid;
        this.solver = solver;
        solver.addObserver(this);
    }

    @Override
    public void paint(Painter painter) {
        paintSurroundingRectangle(painter);
        for (GridNodeStyle style : styleList)
            style.paintGridNode(painter);
    }

    private void paintSurroundingRectangle(Painter painter) {
        painter.paintRectangle(grid.getTopLeft(), grid.getWidth(), grid.getHeight());

    }

    @Override
    public void run() {
        solver.solve();
    }

    @Override
    public void pause() {
        solver.pause();
    }

    @Override
    public void setShapes(List<PaintableShape> shapeList) {
        if (gridMapper == null)
            gridMapper = new GridMapper(grid);
        gridMapper.mapShapes(shapeList);
    }

    @Override
    public void update(String args) {
        notifyObservers(null);
    }

    public void addPlotStyle(GridNodeStyle gridNodeStyle) {
        styleList.add(gridNodeStyle);
        styleList.sort(Comparator.naturalOrder());
        gridNodeStyle.setGrid(grid);
    }

    @Override
    public void removePlotStyle(GridNodeStyle gridNodeStyle) {
        styleList.remove(gridNodeStyle);
    }
}
