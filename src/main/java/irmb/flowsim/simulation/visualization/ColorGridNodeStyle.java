package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;

/**
 * Created by sven on 23.03.17.
 */
public class ColorGridNodeStyle extends GridNodeStyle {

    private final ColorFactory colorFactory;
    private boolean firstRun = true;
    private double min;
    private double max;


    public ColorGridNodeStyle(ColorFactory colorFactory, CoordinateTransformer transformer) {
        super(0, transformer);
        this.colorFactory = colorFactory;
    }

    @Override
    public void paintGridNode(Painter painter) {
        double viewDelta, viewX, viewY;
        viewDelta = transformer.scaleToScreenLength(grid.getDelta());
        Point topLeft = transformer.transformToPointOnScreen(grid.getTopLeft());
        viewX = topLeft.getX();
        viewY = topLeft.getY();

        getInitialMinMax();
        double currentMin = min;
        double currentMax = max;

        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;

        int size = grid.getHorizontalNodes() * grid.getVerticalNodes();
        int x, y;
        for (int i = 0; i < size; i++) {
            x = i % grid.getHorizontalNodes();
            y = i / grid.getHorizontalNodes();

            if(grid.isSolid(x, y))
                continue;

            adjustMinMax(x, y);
            double velocity = grid.getVelocityAt(x, y);
            painter.setColor(colorFactory.makeColorForValue(currentMin, currentMax, velocity));
            painter.fillRectangle(viewX + x * viewDelta, viewY - grid.getHeight() + y * viewDelta, Math.ceil(viewDelta), Math.ceil(viewDelta));
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
