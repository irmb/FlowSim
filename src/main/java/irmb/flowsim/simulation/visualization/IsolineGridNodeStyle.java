package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.Point;
import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.Color;
import irmb.flowsim.presentation.Painter;
import irmb.flowsim.presentation.factory.ColorFactory;

/**
 * Created by sven on 23.03.17.
 */
public class IsolineGridNodeStyle extends GridNodeStyle {


    public IsolineGridNodeStyle(CoordinateTransformer transformer) {
        super(-1, transformer);
        
    }

    @Override
    public void paintGridNode(Painter painter) {


    }

}