package irmb.flowsim.simulation.visualization;

import irmb.flowsim.model.util.CoordinateTransformer;
import irmb.flowsim.presentation.factory.ColorFactoryImpl;

/**
 * Created by sven on 24.03.17.
 */
public class GridNodeStyleFactory {
    private final CoordinateTransformer transformer;

    public GridNodeStyleFactory(CoordinateTransformer transformer) {
        this.transformer = transformer;
    }

    public GridNodeStyle makeGridNodeStyle(PlotStyle style) {
        switch (style) {
            case Color:
                return new ColorGridNodeStyle(new ColorFactoryImpl(), transformer);
            case Arrow:
                return new ArrowGridNodeStyle(5, transformer);
            case Info:
                return new InfoDisplayGridNodeStyle(transformer);
            case GridType:
                return new GridTypeStyle(transformer);
            default:
                return null;
        }

    }

    public String[] getGridNodeStyleChoices() {
        String[] choices = new String[PlotStyle.values().length];
        for (int i = 0; i < PlotStyle.values().length; i++) {
            choices[i] = PlotStyle.values()[i].toString();
        }

        return choices;
    }

}
