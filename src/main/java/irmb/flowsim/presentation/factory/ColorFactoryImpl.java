package irmb.flowsim.presentation.factory;

import irmb.flowsim.presentation.Color;

/**
 * Created by sven on 09.03.17.
 */
public class ColorFactoryImpl implements ColorFactory {
    @Override
    public Color makeColorForValue(double min, double max, double value) {
        int r, g, b;
        r = b = g = 0;

        //TODO: Calculate the appropriate color for <value> between <min> and <max>

        return new Color(r, g, b);
    }
}

