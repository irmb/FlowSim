package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.simulation.UniformGrid;

/**
 * Created by sven on 23.03.17.
 */
public abstract class GridNodeStyle implements Comparable<GridNodeStyle> {
    protected final int priority;
    protected CoordinateTransformer transformer;
    protected UniformGrid grid;

    protected GridNodeStyle(int priority, CoordinateTransformer transformer) {
        this.priority = priority;
        this.transformer = transformer;
    }

    public void setGrid(UniformGrid grid) {
        this.grid = grid;
    }

    public abstract void paintGridNode(Painter painter);

    public int compareTo(GridNodeStyle style) {
        return Integer.compare(priority, style.priority);
    }

}
