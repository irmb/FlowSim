package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;

/**
 * Created by sven on 23.03.17.
 */
public class ArrowGridNodeStyle extends GridNodeStyle {

    private int offset;
    private boolean firstRun = true;
    private double max;
    private double min;
    private double currentMin;
    private double currentMax;

    public ArrowGridNodeStyle(int offset, CoordinateTransformer transformer) {
        super(1, transformer);
        this.offset = offset;
    }

    @Override
    public void paintGridNode(Painter painter) {
        //TODO
    }

    private void getInitialMinMax() {
        if (firstRun) {
            min = grid.getVelocityAt(0, 0);
            max = grid.getVelocityAt(0, 0);
            for (int y = 0; y < grid.getVerticalNodes(); y++)
                for (int x = 0; x < grid.getHorizontalNodes(); x++)
                    adjustMinMax(x, y);
            firstRun = false;
        }
    }

    private void adjustMinMax(int x, int y) {
        double v = grid.getVelocityAt(x, y);
        if (v < min)
            min = v;
        if (v > max)
            max = v;
    }

    public boolean equals(Object o) {
        try {
            ArrowGridNodeStyle other = (ArrowGridNodeStyle) o;
            return other.offset == offset;
        } catch (Exception e) {
            return false;
        }
    }

}
