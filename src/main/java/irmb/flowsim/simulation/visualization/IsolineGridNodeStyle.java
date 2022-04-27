package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;

/**
 * Created by sven on 23.03.17.
 */
public class IsolineGridNodeStyle extends GridNodeStyle {

    private final ColorFactory colorFactory;
    private boolean firstRun = true;
    private double min;
    private double max;

    public IsolineGridNodeStyle(ColorFactory colorFactory, CoordinateTransformer transformer) {
        super(-1, transformer);
        this.colorFactory = colorFactory;
    }

    @Override
    public void paintGridNode(Painter painter) {

        getInitialMinMax();
        double currentMin = min;
        double currentMax = max;

        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;

        double deltaH = Math.abs(currentMin - currentMax) / 20.0;
        double h0 = currentMin;

        int size = grid.getHorizontalNodes() * grid.getVerticalNodes();
        int x, y;
        for (int i = 0; i < size; i++) {
            x = i % grid.getHorizontalNodes();
            y = i / grid.getHorizontalNodes();

            // TODO
        }
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

}