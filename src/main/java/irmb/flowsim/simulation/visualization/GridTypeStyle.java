package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;

/**
 * Created by sven on 23.03.17.
 */
public class GridTypeStyle extends GridNodeStyle {


    public GridTypeStyle(CoordinateTransformer transformer) {
        super(-1, transformer);
        
    }

    @Override
    public void paintGridNode(Painter painter) {

        double viewDelta, viewX, viewY;
        viewDelta = transformer.scaleToScreenLength(grid.getDelta());
        Point topLeft = transformer.transformToPointOnScreen(grid.getTopLeft());
        viewX = topLeft.getX();
        viewY = topLeft.getY();

        int size = grid.getHorizontalNodes() * grid.getVerticalNodes();
        int x, y;

        for (int i = 0; i < size; i++) {
            x = i % grid.getHorizontalNodes();
            y = i / grid.getHorizontalNodes();

            boolean noteIsSolid = grid.isSolid(x, y);

            Color c = noteIsSolid ? new Color(255, 0, 0) :  new Color(0, 0, 255);

            painter.setColor(c);
            painter.fillRectangle(viewX + x * viewDelta, viewY - grid.getHeight() + y * viewDelta, (int) Math.round(viewDelta * 0.5), (int) Math.round(viewDelta * 0.5));
        }
    }

}
